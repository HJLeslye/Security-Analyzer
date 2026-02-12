package com.security.controller;

import com.security.dao.UserDAO;
import com.security.model.User;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {
    private String username;
    private String password;
    private User userAuthenticated;
    private UserDAO userDAO = new UserDAO();

    public String login() {
        userAuthenticated = userDAO.login(username, password);
        if (userAuthenticated != null) {
            if (userAuthenticated.getRole().equalsIgnoreCase("ADMIN")) {
                return "dashboard_admin?faces-redirect=true";
            } else {
                return "dashboard_mantenimiento?faces-redirect=true";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acceso Denegado", "Usuario o contraseña incorrectos"));
            return null;
        }
    }

    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public User getUserAuthenticated() { return userAuthenticated; }
}