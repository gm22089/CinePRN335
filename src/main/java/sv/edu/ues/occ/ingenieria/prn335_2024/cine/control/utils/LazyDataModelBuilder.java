package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.utils;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.AbstractFrm;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersist;

import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LazyDataModelBuilder<T> {
    private AbstractDataPersist<T> bean;
    private LazyDataModel<T> modelo;

    private final Function<Void,Integer> count;
    private final Function<T, String> getIdByObjectFunction;
    private final Function<String, T> getObjectByIdFunction;
//    private final ImultipleFunctionFather<Integer,Integer,String,String,String,List<T> lista>;

    public LazyDataModelBuilder(
            Function<Void,Integer> count,
            Function<T, String> getIdByObjectFunction,
            Function<String, T> getObjectByIdFunction,
            ImultipleFunction<T> find

    )

    {
        this.count = count;
        this.getIdByObjectFunction=getIdByObjectFunction;
        this.getObjectByIdFunction=getObjectByIdFunction;
        this.modelo=new LazyDataModel<T>(){

            @Override
            public int count(Map<String, FilterMeta> map) {
                try {
                    return count.apply(null);
                }catch (Exception e){
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE,"no se ha podido encontrar la cantidad de datos para la clase",e);
                }
                return 0;
            }

            @Override
            public List<T> load(int init, int max, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {

                if (init >= 0 && max > 0){

                    try {

                        return find.findRange(init,max);
                    }catch (Exception e) {
                        Logger.getLogger(AbstractFrm.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                return List.of();
            }

            @Override
            public String getRowKey(T object) {
                // Aquí llamamos a la función que obtiene el ID único del objeto
                return getIdByObjectFunction.apply(object);
            }

            @Override
            public T getRowData(String rowKey) {
                // Aquí llamamos a la función que obtiene el objeto a partir del ID
                return getObjectByIdFunction.apply(rowKey);
            }

        };
    }

    public LazyDataModel<T> getModelo() {
        return this.modelo;
    }



}
