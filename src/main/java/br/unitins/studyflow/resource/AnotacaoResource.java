package br.unitins.studyflow.resource;

import br.unitins.studyflow.dto.AnotacaoDTO;
import br.unitins.studyflow.dto.AnotacaoRequestDTO;
import br.unitins.studyflow.model.Anotacao;
import br.unitins.studyflow.service.AnotacaoService;
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
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/anotacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnotacaoResource {
    @Inject
    AnotacaoService anotacaoService;

    @GET
    public Response findAll() {
        return Response.ok(anotacaoService.findAll()
                .stream()
                .map(AnotacaoDTO::valueOf)
                .toList())
                .build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(AnotacaoDTO.valueOf(anotacaoService.findById(id))).build();
    }

    @GET
    @Path("/search/atividade/{idAtividade}")
    public Response findByAtividade(@PathParam("idAtividade") Long idAtividade) {
        return Response.ok(anotacaoService.findByAtividade(idAtividade)
                .stream()
                .map(AnotacaoDTO::valueOf)
                .toList()).build();
    }

    // exporta anotação em arq txt
    @GET
    @Path("/{id}/export/txt")
    @Produces(MediaType.TEXT_PLAIN)
    public Response exportAnotacaoAsTxt(@PathParam("id") Long id) {
        Anotacao anotacao = anotacaoService.findById(id);

        if (anotacao == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        String conteudo = anotacao.getConteudo();
        String nomeArquivo = "anotacao-" + anotacao.getId() + ".txt";

        ResponseBuilder response = Response.ok(conteudo);
        response.header("Content-Disposition", "attachment; filename=\"" + nomeArquivo + "\"");

        return response.build();
    }

    @POST
    public Response create(@Valid AnotacaoRequestDTO dto) {
        return Response.status(Status.CREATED)
                .entity(AnotacaoDTO.valueOf(anotacaoService.create(dto)))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid AnotacaoRequestDTO dto) {
        return Response.ok(
                AnotacaoDTO.valueOf(anotacaoService.update(id, dto))).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        anotacaoService.delete(id);
        return Response.noContent().build();
    }
}
