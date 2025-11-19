package br.unitins.studyflow.resource;

import br.unitins.studyflow.service.UsuarioService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UsuarioService usuarioService;

    @POST
    @Path("/google-login")
    @PermitAll // Este endpoint não exige autenticação prévia
    @Consumes(MediaType.TEXT_PLAIN) // Recebe o token como texto puro
    public Response googleLogin(String idToken) {
        try {
            // CORRETO: O serviço já devolve um 'Response', apenas retorna-o diretamente
            return usuarioService.loginComGoogle(idToken);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro: " + e.getMessage())
                    .build();
        }
    }
}
