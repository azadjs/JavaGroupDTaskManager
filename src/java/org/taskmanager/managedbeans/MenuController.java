package org.taskmanager.managedbeans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.taskmanager.managedbeans.util.Menus;

/**
 *
 * @author elmarmammadov
 */
@ManagedBean(name = "mc")
@SessionScoped
public class MenuController {
    
    private Menus currentMenu;
    
    @PostConstruct
    public void initMenuElement(){
        this.currentMenu = Menus.MY_TASKS;
    }
    
    public void navigateMenuElement(Menus toMenu){
        this.currentMenu = toMenu;
    }

    public Menus getCurrentMenu() {
        return currentMenu;
    }
    
    public boolean isActiveMenuElement(Menus menu){
        return this.currentMenu.equals(menu);
    }
    
    
}
