package br.unitins.studyflow.resource;

import br.unitins.studyflow.service.DashboardService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dashboard")
@Produces(MediaType.APPLICATION_JSON)
public class DashboardResource {

    @Inject
    DashboardService dashboardService; 

    @GET
    @Path("/{usuarioId}")
    public Response getDashboardData(@PathParam("usuarioId") Long usuarioId) {
         
        return Response.ok(dashboardService.getDashboardData(usuarioId)).build();
    }
}
