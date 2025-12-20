package com.jakartaudbl.jakartamission.beans;

import com.jakartaudbl.jakartamission.business.SessionManager;
import com.jakartaudbl.jakartamission.business.UtilisateurEntrepriseBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import com.jakartaudbl.jakartamission.entities.Utilisateur;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.io.IOException;

@RequestScoped
@Named
public class WelcomeBean {

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;
    @Inject
    private SessionManager sessionManager;
    
    private String nom;
    private String message;
    
    private String email;
    private String password;

    private String messageUsd;
    private String messageIdr;

    private double amountUsd;
    private double amountIdr;

    
    private static final double RATE = 16682.0;

    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMessage() {
        return message;
    }

   
    public void display() {
        this.message = "Welcome to Indonesia, dear " + this.nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public double getAmountUsd() {
        return amountUsd;
    }

    public void setAmountUsd(double amountUsd) {
        this.amountUsd = amountUsd;
    }

    public double getAmountIdr() {
        return amountIdr;
    }

    public void setAmountIdr(double amountIdr) {
        this.amountIdr = amountIdr;
    }

    public String getMessageUsd() {
        return messageUsd;
    }

    public String getMessageIdr() {
        return messageIdr;
    }

   
    public double convertToUsd() {
        return amountIdr / RATE;
    }

    
    public double convertToIdr() {
        return amountUsd * RATE;
    }

    
    public void processConversion() {
        double usd = convertToUsd();
        double cdf = convertToIdr();

        this.messageUsd = amountIdr + " CDF = " + usd + " USD";
        this.messageIdr = amountUsd + " USD = " + cdf + " CDF";
    }
    
    public String connexion(){
        return sAuthentifier(email, password);
    }
    
    public String sAuthentifier(String email, String password) {
        Utilisateur utilisateur = utilisateurEntrepriseBean.authentifier(email, password);
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(utilisateur != null) {
            sessionManager.createSession("user", email);
            return "pages/home?faces-redirect=true";
        } else {
            this.message = "Email ou mot de passe incorrect.";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return null;    
        }
    }
}
