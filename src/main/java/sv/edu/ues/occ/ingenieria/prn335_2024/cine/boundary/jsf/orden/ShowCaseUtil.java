package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.orden;

import java.lang.reflect.Field;

public class ShowCaseUtil {
    public static Object getPropertyValueViaReflection(Object object, String propertyName) {
        try {
            // Obtener el campo del objeto usando su nombre
            Field field = object.getClass().getDeclaredField(propertyName);
            field.setAccessible(true); // Permitir acceso al campo privado
            return field.get(object); // Obtener el valor del campo
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("El campo '" + propertyName + "' no existe en la clase " + object.getClass().getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("No se puede acceder al campo '" + propertyName + "' en la clase " + object.getClass().getName(), e);
        }
    }
}
