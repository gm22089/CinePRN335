package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf;

import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonObject;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ContadorBean;

import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class FrmWebSocket implements Serializable {
    @Inject
    @Push(channel = "chepe")
    PushContext pushContext;
    @Inject
    ContadorBean cbean;
    int cuenta=0;
    UUID UID;


    ///proboof reemplasara  a rest
    public void enviarMensaje(){
        UID=UUID.randomUUID();
        cbean.contarDespacio(cuenta,UID,mensaje->recibirMensaje(mensaje));
        pushContext.send("mensaje enviado"+System.currentTimeMillis());

    }
    public void recibirMensaje(JsonObject respuesta){
        if (respuesta!=null){
            if (respuesta.getString("tipo_respuesta").equals(ContadorBean.TIPO_RESPUESTA.EXISTO.toString())
                    && respuesta.getString("UUID").equals(this.UID.toString())
            ){

                cuenta=respuesta.getInt("cuenta");
                pushContext.send("cuenta: "+cuenta);
            }
        }
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }
}
