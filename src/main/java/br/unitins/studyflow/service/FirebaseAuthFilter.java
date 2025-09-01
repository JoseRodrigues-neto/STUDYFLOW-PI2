package br.unitins.studyflow.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class FirebaseAuthFilter implements ContainerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Bearer";

  
    static {
        try {
           //arrumar esse rota futuramemte 
            FileInputStream serviceAccount = new FileInputStream("C:\\key\\fire-flow-6aa0c-firebase-adminsdk-fbsvc-c12238b247.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
   
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase Admin SDK inicializado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao inicializar o Firebase Admin SDK: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void filter(ContainerRequestContext requestContext) {
        // Lógica para lidar com requisições OPTIONS e rotas públicas.
        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod()) ||
            requestContext.getUriInfo().getPath().contains("/login") ||
            requestContext.getUriInfo().getPath().contains("/public")) {
            return;
        }

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(AUTHENTICATION_SCHEME + " ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token de autenticacao ausente ou invalido.").build());
            return;
        }

        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();
            
            // Criação do Principal e do SecurityContext
            Principal principal = () -> uid;

            SecurityContext securityContext = new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return principal;
                }
                @Override
                public boolean isUserInRole(String role) { return false; }
                @Override
                public boolean isSecure() { return true; }
                @Override
                public String getAuthenticationScheme() { return "BearerAuth"; }
            };

            requestContext.setSecurityContext(securityContext);

 
        } catch (FirebaseAuthException e) {
            System.err.println("Erro na validacao do token: " + e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token de autenticacao invalido: " + e.getMessage()).build());
        }
    }
}
