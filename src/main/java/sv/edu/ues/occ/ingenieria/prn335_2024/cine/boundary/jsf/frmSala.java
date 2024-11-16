package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.util.List;

@Named
@RequestScoped
public class frmSala {

    private Sala registro;
    private List<Sala> registros;

    @Inject
    private SalaBean salaBean;

    // Getter y setter de registro
    public Sala getRegistro() {
        return registro;
    }

    public void setRegistro(Sala registro) {
        this.registro = registro;
    }

    // Getter y setter de registros
    public List<Sala> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Sala> registros) {
        this.registros = registros;
    }

    // Buscar salas por nombre
    public void buscarPorNombre(String nombre) {
        if (isValidString(nombre)) {
            try {
                registros = salaBean.findByName(nombre);
            } catch (Exception e) {
                handleError("Error al buscar salas", e);
            }
        }
    }

    // Guardar o actualizar sala
    public void guardarSala() {
        if (registro == null) {
            showError("No hay sala seleccionada para guardar.");
            return;
        }

        try {
            if (registro.getIdSala() == null) {
                salaBean.create(registro);  // Crear nueva sala
            } else {
                salaBean.edit(registro);    // Actualizar sala existente
            }
        } catch (Exception e) {
            handleError("Error al guardar sala", e);
        }
    }

    // Eliminar sala
    public void eliminarSala() {
        if (registro == null || registro.getIdSala() == null) {
            showError("No se ha seleccionado una sala para eliminar.");
            return;
        }

        try {
            salaBean.remove(registro);  // Eliminar sala seleccionada
            registro = null;  // Resetear el registro después de eliminar
        } catch (Exception e) {
            handleError("Error al eliminar sala", e);
        }
    }

    // Seleccionar una sala para editar
    public void seleccionarSala(Sala sala) {
        if (sala != null) {
            this.registro = sala;
        } else {
            showError("No se seleccionó una sala válida.");
        }
    }

    // Cancelar operación y restaurar el estado inicial
    public void cancelar() {
        this.registro = null;  // Resetear registro
    }

    // Crear nueva sala
    public void nuevaSala() {
        this.registro = new Sala();  // Inicializa un nuevo objeto Sala
    }

    // Verificar si la cadena es válida (no nula ni vacía)
    private boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    // Mostrar mensaje de error
    private void showError(String message) {
        System.out.println(message);  // Aquí podrías agregar un mensaje en la interfaz
    }

    // Manejo de errores y log
    private void handleError(String message, Exception e) {
        System.out.println(message + ": " + e.getMessage());  // Log del error
    }
}
