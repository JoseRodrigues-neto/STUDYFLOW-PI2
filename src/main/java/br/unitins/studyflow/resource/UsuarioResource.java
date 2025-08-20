package br.unitins.studyflow.resource;


import java.util.List;

import br.unitins.studyflow.dto.UsuarioRequestDTO;
import br.unitins.studyflow.dto.UsuarioResponseDTO;
import br.unitins.studyflow.service.UsuarioService;
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

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService service;

    @POST
    public Response cadastrar(UsuarioRequestDTO usuarioDTO) {
        try {
            UsuarioResponseDTO novoUsuario = service.cadastrar(usuarioDTO);
            return Response.status(Response.Status.CREATED).entity(novoUsuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar usuário: " + e.getMessage()).build();
        }
    }

    @GET
    public List<UsuarioResponseDTO> buscarTodos() {
        return service.buscarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        UsuarioResponseDTO usuario = service.buscarPorId(id);
        if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, UsuarioRequestDTO usuarioDTO) {
        try {
            UsuarioResponseDTO usuarioAtualizado = service.atualizar(id, usuarioDTO);
            if (usuarioAtualizado != null) {
                return Response.ok(usuarioAtualizado).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar usuário: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        boolean sucesso = service.excluir(id);
        if (sucesso) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
