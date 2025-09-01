package br.unitins.studyflow.resource;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement; // Importação correta para segurança web
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

import br.unitins.studyflow.dto.UsuarioRequestDTO;
import br.unitins.studyflow.dto.UsuarioResponseDTO;
import br.unitins.studyflow.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;


@Path("/usuarios")
@SecuritySchemes(value = {
    @SecurityScheme(
        securitySchemeName = "BearerAuth",
        description = "Autenticacao JWT usando Bearer Token",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
    )
})
@SecurityRequirement(name = "BearerAuth")
public class UsuarioResource {

    @Inject
    UsuarioService service;

    @Context
    SecurityContext securityContext;

    @POST
    // @RolesAllowed("ALUNO") // Pode ser ativado depois de configurar roles no Firebase
    public Response cadastrar(UsuarioRequestDTO usuarioDTO) {
        try {
            // Obtém o UID do Principal (usuário autenticado), que é preenchido pelo filtro.
            String uid = securityContext.getUserPrincipal().getName();

            if (uid == null || uid.isEmpty()) {
                return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("UID do Firebase ausente no contexto de seguranca.").build();
            }

            UsuarioResponseDTO novoUsuario = service.cadastrar(uid, usuarioDTO);
            return Response.status(Response.Status.CREATED).entity(novoUsuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Erro ao cadastrar usuario: " + e.getMessage()).build();
        }
    }
    
    @GET
    public List<UsuarioResponseDTO> buscarTodos() {
        return service.buscarTodos();
    }

    @GET
    @Path("/{uid}")
    public Response buscarPorId(@PathParam("uid") String uid) {
         String uidDoToken = securityContext.getUserPrincipal().getName();
        if (!uid.equals(uidDoToken)) {
            return Response.status(Response.Status.FORBIDDEN)
                .entity("Acesso negado. Voce nao tem permissao para acessar este recurso.").build();
        }

        UsuarioResponseDTO usuario = service.buscarPorId(uid);
        if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT 
    @Path("/{uid}")
    public Response atualizar(@PathParam("uid") String uid, UsuarioRequestDTO usuarioDTO) {
        try {
           
            String uidDoToken = securityContext.getUserPrincipal().getName();
            if (!uid.equals(uidDoToken)) {
                return Response.status(Response.Status.FORBIDDEN)
                    .entity("Acesso negado. Voce nao tem permissao para atualizar este recurso.").build();
            }

            UsuarioResponseDTO usuarioAtualizado = service.atualizar(uid, usuarioDTO);
            if (usuarioAtualizado != null) {
                return Response.ok(usuarioAtualizado).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Erro ao atualizar usuario: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{uid}")
    public Response excluir(@PathParam("uid") String uid) {
       
        String uidDoToken = securityContext.getUserPrincipal().getName();
        if (!uid.equals(uidDoToken)) {
            return Response.status(Response.Status.FORBIDDEN)
                .entity("Acesso negado. Voce nao tem permissao para excluir este recurso.").build();
        }
        
        boolean sucesso = service.excluir(uid);
        if (sucesso) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
