package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;


import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class FrmMenu implements Serializable {
    @Inject
    FacesContext facesContext;
    DefaultMenuModel model;

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();

        // Registro para comprobar si init se llama
        System.out.println("Inicializando el menú...");

        DefaultSubMenu tipos = DefaultSubMenu.builder()
                .label("Tipos")
                .expanded(true)
                .build();

        DefaultMenuItem item = DefaultMenuItem.builder()
                .value("Salas")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoSala.jsf')}")
                .build();

        tipos.getElements().add(item);
        model.getElements().add(tipos);

        // Registro para comprobar la estructura del modelo
        System.out.println("Modelo de menú inicializado: " + model.getElements());
    }

    public void navegar(String formulario) {
        try {
            facesContext.getExternalContext().redirect(formulario);
        } catch (IOException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo navegar.");
            facesContext.addMessage(null, message);
            e.printStackTrace(); // Para depuración
        }
    }


    public DefaultMenuModel getModel() {
        return model;
    }
}