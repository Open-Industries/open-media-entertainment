package org.openindustries.media.content;

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

@Path("/api/v1/content")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Content Assets", description = "Content asset registry; the registry code is masked at registration.")
public class ContentAssetResource {

    @Inject
    ContentAssetService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a contentasset")
    public Response create(@Valid CreateContentAssetRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<ContentAsset> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public ContentAsset get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/publish")
    @Operation(summary = "Publish this contentasset (lifecycle transition, no body)")
    public ContentAsset publish(@PathParam("id") UUID id) {
        return service.publish(id);
    }

    @POST
    @Path("/{id}/archive")
    @Operation(summary = "Archive this contentasset (lifecycle transition, no body)")
    public ContentAsset archive(@PathParam("id") UUID id) {
        return service.archive(id);
    }
}
