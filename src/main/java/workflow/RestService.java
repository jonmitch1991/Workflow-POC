package main.java.workflow;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/workflow")
@Produces(MediaType.TEXT_PLAIN)
public class RestService {

    @Inject
    private WorkflowEngine workflowEngine;

    @GET
    public Response ping() {
        return Response.ok("Welcome").build();
    }

    @GET
    @Path("/test")
    public Response start() {
        return Response.ok("Test success!").build();
    }

    @GET
    @Path("/createIPR")
    public Response createIPR() {
        Map<String, Object> params = new HashMap<>();
        params.put("task", Task.CREATE_IPR);
        return Response.ok(workflowEngine.process(params)).build();
    }

    @GET
    @Path("/submitIPR")
    public Response submitIPR() {
        Map<String, Object> params = new HashMap<>();
        params.put("task", Task.SUBMIT_IPR);
        return Response.ok(workflowEngine.process(params)).build();
    }

    @GET
    @Path("/approveIPR")
    public Response approveIPR() {
        Map<String, Object> params = new HashMap<>();
        params.put("task", Task.APPROVE_IPR);
        return Response.ok(workflowEngine.process(params)).build();
    }

    @GET
    @Path("/rejectIPR")
    public Response rejectIPR() {
        Map<String, Object> params = new HashMap<>();
        params.put("task", Task.REJECT_IPR);
        return Response.ok(workflowEngine.process(params)).build();
    }

}
