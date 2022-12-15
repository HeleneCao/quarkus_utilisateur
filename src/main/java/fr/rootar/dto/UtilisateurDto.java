package fr.rootar.dto;

import lombok.Data;

import javax.enterprise.context.ApplicationScoped;

@Data

public class UtilisateurDto {

    private int idUtilisateur;
    private String prenom;
    private String nom;
    private String login;
    private String password;
    private String role;

}
