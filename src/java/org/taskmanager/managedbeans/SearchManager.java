package org.taskmanager.managedbeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author elmarmammadov
 */
@ManagedBean
@ViewScoped
public class SearchManager implements Serializable {

    private String testData;

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

}
