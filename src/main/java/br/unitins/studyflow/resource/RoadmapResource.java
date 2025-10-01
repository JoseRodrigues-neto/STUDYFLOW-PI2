package br.unitins.studyflow.resource;

import br.unitins.studyflow.dto.RoadmapDTO;
import br.unitins.studyflow.dto.RoadmapRequestDTO;
import br.unitins.studyflow.service.RoadmapService;
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

@Path("/roadmaps")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoadmapResource {
    @Inject
    RoadmapService roadmapService;

    @GET
    public Response findAll() {
        return Response.ok(roadmapService.findAll()
                .stream()
                .map(RoadmapDTO::valueOf)
                .toList())
                .build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(RoadmapDTO.valueOf(roadmapService.findById(id))).build();
    }

    @POST
    public Response create(@Valid RoadmapRequestDTO dto) {
        return Response.status(Status.CREATED)
                .entity(RoadmapDTO.valueOf(roadmapService.create(dto)))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid RoadmapRequestDTO dto) {
        return Response.ok(
                RoadmapDTO.valueOf(roadmapService.update(id, dto))).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        roadmapService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{titulo}")
    public Response findById(@PathParam("titulo") String titulo) {
        return Response.ok(RoadmapDTO.valueOf(roadmapService.findByTitulo(titulo))).build();
    }
}
