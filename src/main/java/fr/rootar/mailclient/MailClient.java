package fr.rootar.mailclient;


import fr.rootar.dto.MailDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RegisterRestClient
@Path("/mailer")
public interface MailClient {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendMail(MailDto mail, @HeaderParam("ApiKey") String apiKey);

}
