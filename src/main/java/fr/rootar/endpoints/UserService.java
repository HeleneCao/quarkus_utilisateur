package fr.rootar.endpoints;




import fr.rootar.dto.MailDto;
import fr.rootar.entities.UtilisateurEntity;
import fr.rootar.mailclient.MailClient;
import fr.rootar.repositories.UserRepository;
import fr.rootar.security.SecurityTools;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Path("/userservice")
@Tag(name="UserService")

public class UserService {

    @Inject
    UserRepository userRepository;
    @Context
    UriInfo uriInfo;
    @Inject
    @RestClient
    MailClient mailClient;

    @POST
    @Path("/authentification")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getToken(@HeaderParam("login") String login, @HeaderParam("password") String password){
        UtilisateurEntity utilisateur=userRepository.findById(login);
        if (utilisateur == null){
            return Response.ok("login inconnu !").status(404).build();
        }

        if(BcryptUtil.matches(password, utilisateur.getPassword())){
            String token = SecurityTools.getToken(utilisateur);
            return Response.ok(token).build();

        }
        return Response.ok().status(Response.Status.FORBIDDEN).build();
    }

    @Transactional
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(UtilisateurEntity utilisateur) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
    UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
    if(userRepository.findById(utilisateur.getLogin()) != null)
        return Response.status(417, "Ce login existe deja, veuillez choisir un autre!").build();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        Date expiration = calendar.getTime();
        String url = String.format("%s|%s|%s|%s|%s",
        utilisateur.getLogin(),
        utilisateur.getMail(),
        BcryptUtil.bcryptHash(utilisateur.getPassword(), 31, "MyPersonalSalt0".getBytes()),
                "User",
                new SimpleDateFormat("dd-MM-yy-HH:mm:ss").format(expiration));
        String urlEncode = SecurityTools.encrypt(url);
        uriBuilder.path(URI.create(urlEncode).toString());

        StringBuilder body = new StringBuilder("Veuiller cliquer sur le lien suivant pour confirmer la création de votre compte.");
        body.append(uriBuilder.build());
        MailDto mailDto = new MailDto(utilisateur.getMail(), "Confirmation de compte", body.toString());

        mailClient.sendMail(mailDto, "ItsOKForYou");
        return Response.ok().status(200).build();


    }



}
