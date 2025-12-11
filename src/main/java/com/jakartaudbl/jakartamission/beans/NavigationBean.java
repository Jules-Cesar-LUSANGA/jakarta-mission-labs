import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import java.io.IOException;

@Named(value = "navigationController")
@RequestScoped
public class NavigationBean {

    public void voirAjouter() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("pages/lieu.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void voirVisiter() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("pages/lieu_ajoutes.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void voirApropos() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("pages/a_propos.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
