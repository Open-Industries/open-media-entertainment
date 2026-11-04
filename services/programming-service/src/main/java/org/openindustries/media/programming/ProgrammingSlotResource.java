package org.openindustries.media.programming;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/programming-slots")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Programming Slots", description = "Programming slot catalogue for schedules.")
public class ProgrammingSlotResource {

    @Inject
    ProgrammingSlotService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a programmingslot")
    public Response create(@Valid CreateProgrammingSlotRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<ProgrammingSlot> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public ProgrammingSlot get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/air")
    @Operation(summary = "Air (lifecycle transition, no body)")
    public ProgrammingSlot air(@PathParam("id") UUID id) {
        return service.air(id);
    }
}
