package fr.rootar.dto;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.enterprise.context.ApplicationScoped;

@Data

public class UtilisateurDto {

    @Schema(required = true,example = "tata")
    private String login;
    @Schema(required = true,example = "helenecao59@gmail.com")
    private String mail;
    @Schema(required = true,minLength = 8,maxLength = 20 ,example = "toto%1234")
    private String password;
    /*@Schema(required = true,example = "user")
    private String role;*/

}
