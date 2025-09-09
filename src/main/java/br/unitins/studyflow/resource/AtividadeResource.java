package br.unitins.studyflow.resource;

import br.unitins.studyflow.dto.AtividadeDTO;
import br.unitins.studyflow.dto.AtividadeRequestDTO;
import br.unitins.studyflow.service.AtividadeService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
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

@Path("/atividades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AtividadeResource {

    @Inject
    AtividadeService atividadeService;

    @GET
    public Response findAll() {
        return Response.ok(atividadeService.findAll()
                .stream()
                .map(AtividadeDTO::valueOf)
                .toList())
                .build();
    }

    @GET
    @Path("/search/{titulo}")
    public Response findByTitulo(@PathParam("titulo") String titulo) {
        return Response.ok(atividadeService.findByTitulo(titulo)
                .stream()
                .map(AtividadeDTO::valueOf).toList())
                .build();
    }

    @POST
    public Response create(@Valid AtividadeRequestDTO dto) {
        return Response.status(Status.CREATED)
                .entity(AtividadeDTO.valueOf(atividadeService.create(dto)))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid AtividadeRequestDTO dto) {
        return Response.ok(
                AtividadeDTO.valueOf(atividadeService.update(id, dto))).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        atividadeService.delete(id);
        return Response.noContent().build();
    }

}
