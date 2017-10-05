package org.taskmanager.managedbeans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.taskmanager.managedbeans.util.MessageController;


/**
 *
 * @author elmarmammadov
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginController {

    private String username;
    private String password;
    private MessageController messageController;
    
    @PostConstruct
    public void init(){
        messageController = new MessageController();
    }
    
    public String login() {
        if ("Elmar".equals(username) && "test".equals(password)) {
            return "/secured/home.xhtml";
        } else {
            messageController.showMessage("Username or Password is incorrect", FacesMessage.SEVERITY_ERROR);
            return "login.xhtml";
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
   
}
