package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.utils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@FunctionalInterface
public interface ImultipleFunction<T> {
    List<T> find(Integer init, Integer max, String campo, String orden);
    default List<T> findRange(Integer init ,Integer max){
        try {

            return find(init, max,null,null);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"problemas al obtener lista d eobjecto",e);
        }
        return List.of();
    }
    default List<T> findRangeOrdered(Integer init ,Integer max,String campo, String orden){

        try {

            return find(init, max,campo,orden);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"problemas al obtener lista d eobjecto",e);
        }

        return List.of();
    }


}
