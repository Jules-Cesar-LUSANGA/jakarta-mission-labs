/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaudbl.jakartamission.exceptions;

import jakarta.ejb.ApplicationException;

/**
 *
 * @author cesar-dev
 */
@ApplicationException(rollback = false)
public class UtilisateurExistantException extends RuntimeException {
    public UtilisateurExistantException() {
        super("Nom d'utilisateur ou email déjà utilisé");
    }
}