package br.unitins.studyflow.resource;

 
import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.studyflow.dto.DashboardDTO;
import br.unitins.studyflow.service.DashboardService;
import br.unitins.studyflow.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dashboard")
@Produces(MediaType.APPLICATION_JSON)
public class DashboardResource {

    @Inject
    DashboardService dashboardService; 

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

@GET
@Path("/me")
@RolesAllowed({"ALUNO", "PROFESSOR"})
public Response getDashboardData(
    @QueryParam("roadmapId") Long roadmapId,
    @QueryParam("from") String from,
    @QueryParam("to") String to,
    @QueryParam("order") String order
) {
    String uid = jwt.getSubject();
    Long usuarioId = usuarioService.buscarPorId(uid).id();

    DashboardDTO dto = dashboardService.getDashboardData(usuarioId);

    return Response.ok(dto).build();
}


}
