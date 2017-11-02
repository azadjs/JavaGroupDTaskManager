package org.taskmanager.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.taskmanager.entities.util.UserRoles;
import org.taskmanager.managedbeans.util.Menus;

/**
 *
 * @author ElmarMa
 */
@ManagedBean(name = "security")
public class UISecurityController {

    @ManagedProperty(value = "#{login}")
    private LoginController loginController;

    public boolean hasUIPermission(String menu) {
        boolean result;
        UserRoles currentRole = loginController.getCurrentUser().getUserRole();
        switch (Menus.fromVal(menu)) {
            case ASSIGNED_TASKS:
                result = currentRole != UserRoles.NONE;
                break;
            case MY_EMPLOYEES:
                result = currentRole == UserRoles.MANAGER;
                break;
            case MY_TASKS:
                result = currentRole == UserRoles.MANAGER;
                break;
            case NOTIFICATIONS:
                result = currentRole != UserRoles.NONE;
                break;
            default:
                result = false;
                break;
        }
        return result;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

}
