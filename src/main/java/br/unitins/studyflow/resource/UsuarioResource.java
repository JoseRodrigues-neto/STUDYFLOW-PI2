package br.unitins.studyflow.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken; 
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

import br.unitins.studyflow.dto.UsuarioRequestDTO;
import br.unitins.studyflow.dto.UsuarioResponseDTO;
import br.unitins.studyflow.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/usuarios")
@SecuritySchemes(value = {
    @SecurityScheme(
        securitySchemeName = "BearerAuth",
        description = "Autenticação JWT com Firebase",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
    )
})
@SecurityRequirement(name = "BearerAuth")
public class UsuarioResource {

    @Inject
    UsuarioService service;

    @Inject
    JsonWebToken jwt;  
    @POST
 
    public Response cadastrar(UsuarioRequestDTO usuarioDTO) {
     
        String uid = jwt.getSubject();
        UsuarioResponseDTO novoUsuario = service.cadastrar(uid, usuarioDTO);
        return Response.status(Status.CREATED).entity(novoUsuario).build();
    }

    @GET
    @RolesAllowed("PROFESSOR")  
    public List<UsuarioResponseDTO> buscarTodos() {
        return service.buscarTodos();
    }
    @GET
    @Path("/me") 
    @RolesAllowed({"ALUNO", "PROFESSOR"}) 
    public Response buscarMeuPerfil() {
        String uidToken = jwt.getSubject();
        UsuarioResponseDTO usuario = service.buscarPorId(uidToken); 
        return usuario != null ? Response.ok(usuario).build() : Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{uid}")
    @RolesAllowed({"ALUNO", "PROFESSOR"})  
    public Response buscarPorId(@PathParam("uid") String uid) {
        String uidToken = jwt.getSubject();

       
        if (jwt.getGroups().contains("ALUNO") && !uid.equals(uidToken)) {
            return Response.status(Status.FORBIDDEN)
                    .entity("Acesso negado. Alunos só podem acessar o próprio perfil.").build();
        }

        UsuarioResponseDTO usuario = service.buscarPorId(uid);
        return usuario != null ? Response.ok(usuario).build() : Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{uid}")
    @RolesAllowed({"ALUNO", "PROFESSOR"})
    public Response atualizar(@PathParam("uid") String uid, UsuarioRequestDTO usuarioDTO) {
        String uidToken = jwt.getSubject();

        
        if (jwt.getGroups().contains("ALUNO") && !uid.equals(uidToken)) {
            return Response.status(Status.FORBIDDEN)
                    .entity("Acesso negado. Alunos só podem atualizar o próprio perfil.").build();
        }

        UsuarioResponseDTO usuarioAtualizado = service.atualizar(uid, usuarioDTO);
        return usuarioAtualizado != null ? Response.ok(usuarioAtualizado).build()
                                         : Response.status(Status.NOT_FOUND).build();
    }

@DELETE
    @Path("/me")
    @RolesAllowed({"ALUNO", "PROFESSOR"}) 
    public Response deleteSelf() {
        
        String uid = jwt.getSubject();
        try {
       
            service.excluir(uid);
            return Response.status(Status.NO_CONTENT).build();

        } catch (Exception e) {
     
            e.printStackTrace(); 
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                           .entity("Erro ao processar a exclusão da conta.")
                           .build();
        }
    }
@PUT 
    @Path("/avatar") // Rota: /usuarios/avatar
    @Consumes(MediaType.TEXT_PLAIN) // Espera apenas um texto (a URL)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ALUNO", "PROFESSOR"})
    public Response salvarAvatarUrl(String avatarUrl) { // Recebe a URL como String
        
        String uid = jwt.getSubject();

        // Manda o service salvar esta URL no banco
        UsuarioResponseDTO usuarioAtualizado = service.atualizarAvatar(uid, avatarUrl);

        if (usuarioAtualizado == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        // Retorna o usuário com a nova foto
        return Response.ok(usuarioAtualizado).build();
    }
}