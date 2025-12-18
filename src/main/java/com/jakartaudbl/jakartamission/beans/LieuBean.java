/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaudbl.jakartamission.beans;

import com.jakartaudbl.jakartamission.business.LieuEntrepriseBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.jakartaudbl.jakartamission.entities.Lieu;
import jakarta.annotation.PostConstruct;

/**
 *
 * @author cesar-dev
 */
@Named(value = "lieuBean")
@RequestScoped
public class LieuBean implements Serializable{

    private int id;
    private String nom;
    private String description;
    private double longitude;
    private double latitude;
    private List<Lieu> lieux = new ArrayList<>();

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;
    
    @PostConstruct
    public void init() {
        if (id > 0) {
            Lieu l = lieuEntrepriseBean.trouverLieuParId(id);
            if (l != null) {
                nom = l.getNom();
                description = l.getDescription();
                latitude = l.getLatitude();
                longitude = l.getLongitude();
            }
        }
    }

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public List<Lieu> getLieux() { return lieuEntrepriseBean.listerTousLesLieux(); }

    public String ajouterLieu() {
        if (nom != null && !nom.isEmpty() && description != null && !description.isEmpty()) {
            lieuEntrepriseBean.ajouterLieuEntreprise(nom, description, latitude, longitude);
        }
        return "lieu_ajoutes.xhtml?faces-redirect=true";
    }
    
    public void supprimerLieu(int id) {
        lieuEntrepriseBean.supprimerLieu(id);
    }
    
    public void chargerLieu() {
        if (id > 0) {
            Lieu l = lieuEntrepriseBean.trouverLieuParId(id);
            if (l != null) {
                nom = l.getNom();
                description = l.getDescription();
                latitude = l.getLatitude();
                longitude = l.getLongitude();
            }
        }
    }
    
    public String modifierLieu() {
        lieuEntrepriseBean.modifierLieu(id, nom, description, latitude, longitude);
        return "lieu_ajoutes.xhtml?faces-redirect=true";
    }
}