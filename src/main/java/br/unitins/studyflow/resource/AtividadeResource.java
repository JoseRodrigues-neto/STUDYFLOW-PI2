package br.unitins.studyflow.resource;

import br.unitins.studyflow.dto.AtividadeDTO;
import br.unitins.studyflow.dto.AtividadeRequestDTO;
import br.unitins.studyflow.service.AtividadeService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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

    @POST
    public Response create(@Valid AtividadeRequestDTO dto) {
        return Response.status(Status.CREATED)
                .entity(AtividadeDTO.valueOf(atividadeService.create(dto)))
                .build();
    }
}
