package br.unitins.studyflow.resource;

import br.unitins.studyflow.dto.AnotacaoDTO;
import br.unitins.studyflow.dto.AnotacaoRequestDTO;
import br.unitins.studyflow.service.AnotacaoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/anotacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnotacaoResource {
    @Inject
    AnotacaoService anotacaoService;

    @POST
    public Response create(@Valid AnotacaoRequestDTO dto) {
        return Response.status(Status.CREATED)
                .entity(AnotacaoDTO.valueOf(anotacaoService.create(dto)))
                .build();
    }
}
