package backingbeans;

import entities.PersonEntity;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.event.FlowEvent;

import java.io.Serializable;

@Named
@ViewScoped
public class UserWizard implements Serializable {

    private PersonEntity person = new PersonEntity();

    private boolean skip;

    public PersonEntity getPerson() {
        return person;
    }

    public void setUser(PersonEntity user) {
        this.person = user;
    }

    public void save() {
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + person.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false; //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }
}