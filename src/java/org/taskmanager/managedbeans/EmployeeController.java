package org.taskmanager.managedbeans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.taskmanager.entities.User;
import org.taskmanager.providers.UserFacade;
import org.taskmanager.providers.exceptions.UserException;
import org.taskmanager.services.UserServices;

/**
 *
 * @author Elmar H.Mammadov <jrelmarmammadov@gmail.com>
 */
@ManagedBean(name = "empctrl")
@SessionScoped
public class EmployeeController {

    @ManagedProperty(value = "#{login}")
    private LoginController loginController;

    private UserServices userService;

    @PostConstruct
    public void init() {
        try {
            userService = new UserFacade();
        } catch (UserException e) {
            e.printStackTrace(System.err);
            userService = null;
        }
    }

    public List<User> getEmployees() {
        try {
            return userService.getUsersByManager(loginController.getCurrentUser());
        } catch (UserException e) {
            e.printStackTrace(System.err);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return new ArrayList<>();
    }

    public void removeEmployee(Long id) {
        try {
            userService.remove(id);
        } catch (UserException e) {
            e.printStackTrace(System.err);
        }
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

}
