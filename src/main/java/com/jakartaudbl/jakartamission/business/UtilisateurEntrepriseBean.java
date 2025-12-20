/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package com.jakartaudbl.jakartamission.business;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import com.jakartaudbl.jakartamission.entities.Utilisateur;
import com.jakartaudbl.jakartamission.exceptions.UtilisateurExistantException;

/**
 *
 * @author cesar-dev
 */
@Stateless
@LocalBean
public class UtilisateurEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void ajouterUtilisateur(String username, String email, String password, String description) {
           
        Utilisateur existsUtilisateur = verifierExistanceUtilisateur(username, email);
        
        if(existsUtilisateur != null) {
            throw new UtilisateurExistantException();
        }
        
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Utilisateur utilisateur = new Utilisateur(username, email, hashedPassword, description);
        
        em.persist(utilisateur);
    }
    
    @Transactional
    public void modifierMotDePasseEtDescription(String password, String description, String email) {
        if(!password.isEmpty()){
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());        
            em.createQuery(
                    "UPDATE Utilisateur SET description = :description, password = :password WHERE email = :email "
            )
            .setParameter("description", description)
            .setParameter("password", hashedPassword)        
            .setParameter("email", email)
            .executeUpdate();
        } else {
            em.createQuery(
                    "UPDATE Utilisateur SET description = :description WHERE email = :email "
            )
            .setParameter("description", description)    
            .setParameter("email", email)
            .executeUpdate();
        }
    }
    
    @Transactional
    private Utilisateur verifierExistanceUtilisateur(String username, String email) {
        return em.createQuery(
            "SELECT u FROM Utilisateur u WHERE u.username = :username OR u.email = :email",
            Utilisateur.class
        )
        .setParameter("username", username)
        .setParameter("email", email)
        .getResultList()
        .stream()
        .findFirst()
        .orElse(null);
    }
    //Méthode pour vérifier un mot de passe 
    public boolean verifierMotDePasse(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }  
    public List<Utilisateur> listerTousLesUtilisateurs() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    @Transactional
    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }

    public Utilisateur trouverUtilisateurParId(Long id) {
        return em.find(Utilisateur.class, id);
    }

    public Utilisateur trouverUtilisateurParEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Utilisateur authentifier(String email, String password) {
        Utilisateur utilisateur = trouverUtilisateurParEmail(email);
        
        if(utilisateur == null){
            return null;
        }
        
        boolean utilisateurExist = verifierMotDePasse(password, utilisateur.getPassword());
        
        if (utilisateurExist == true) {
            return utilisateur;
        }
        return null;
    }
}