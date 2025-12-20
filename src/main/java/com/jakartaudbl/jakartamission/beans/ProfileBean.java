package com.jakartaudbl.jakartamission.beans;

import com.jakartaudbl.jakartamission.business.SessionManager;
import com.jakartaudbl.jakartamission.business.UtilisateurEntrepriseBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import com.jakartaudbl.jakartamission.entities.Utilisateur;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.IOException;

@RequestScoped
@Named
public class ProfileBean {

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    @Inject
    private SessionManager sessionManager;
    
    private String username;
    private String email;
    
    private String password;
    private String confirmPassword;
    
    private String description;

    @PostConstruct
    public void init() {
        String userEmail = sessionManager.getValueFromSession("user");
        if (userEmail != null) {
            Utilisateur utilisateur = utilisateurEntrepriseBean.trouverUtilisateurParEmail(userEmail);
            if (utilisateur != null) {
                this.username = utilisateur.getUsername();
                this.email = utilisateur.getEmail();
                this.description = utilisateur.getDescription();
            }
        }
    }
    
    public void modifierProfil() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(!password.isEmpty()) {
            if (!password.equals(confirmPassword)) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les mots de passe ne correspondent pas", null));
                return;
            }
        }
        
        utilisateurEntrepriseBean.modifierMotDePasseEtDescription(password, description, email);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informations modifiées avec succès", null));
    }

    public void seDeconnecter() {
        sessionManager.invalidateSession();
        try {
            FacesContext.getCurrentInstance()
                .getExternalContext()
                .redirect(
                    FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .getRequestContextPath() + "/index.xhtml"
                );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
   
}
