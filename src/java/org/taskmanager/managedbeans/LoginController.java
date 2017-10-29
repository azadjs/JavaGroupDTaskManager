package org.taskmanager.managedbeans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.taskmanager.entities.User;
import org.taskmanager.managedbeans.util.MessageController;
import org.taskmanager.providers.UserFacade;
import org.taskmanager.providers.exceptions.UserAuthenticationException;
import org.taskmanager.providers.exceptions.UserException;
import org.taskmanager.services.UserServices;

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
    private UserServices userService;
    private User currentUser;

    @PostConstruct
    public void init() {
        messageController = new MessageController();
        try {
            userService = new UserFacade();
        } catch (UserException e) {
            e.printStackTrace(System.err);
        }
    }

    public String login() {
        try {
            currentUser = userService.login(username, password);
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(true);
            session.setAttribute("user", currentUser);

            return "/secured/home.xhtml?faces-redirect=true";
        } catch (UserAuthenticationException e) {
            e.printStackTrace(System.err);
            messageController.showMessage("Username or Password is incorrect", FacesMessage.SEVERITY_ERROR);
            return "login.xhtml";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            messageController.showMessage("Technical problem occured , please contact Administrator",
                    FacesMessage.SEVERITY_ERROR);
            return "login.xhtml";
        }
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        return "login.xhtml";
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

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
