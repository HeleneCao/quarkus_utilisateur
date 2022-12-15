package fr.rootar.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "UTILISATEUR", schema = "dbo", catalog = "ROOTAR")
public class UtilisateurEntity {


    @Basic
    @Column(name = "PRENOM")
    private String prenom;
    @Basic
    @Column(name = "NOM")
    private String nom;
 @Id
 @Column(name = "LOGIN")
    private String login;
    @Basic
    @Column(name = "MAIL")
    private String mail;
    @Basic
    @Column(name = "PASSWORD")
    private String password;
    @Basic
    @Column(name = "ROLE")
    private String role;




}
