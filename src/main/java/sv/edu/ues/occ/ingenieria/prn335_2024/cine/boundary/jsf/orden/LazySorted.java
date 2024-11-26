package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.jsf.orden;

import javax.swing.*;
import java.util.Comparator;

public class LazySorted<T> implements Comparator<T> {
    private String sortField;
    private SortOrder sortOrder;

    public LazySorted(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public LazySorted(String field, org.primefaces.model.SortOrder order) {
    }

    @Override
    public int compare(T primero, T segundo) {
        try {
            // Obtener los valores de las propiedades mediante reflexión
            Object value1 = ShowCaseUtil.getPropertyValueViaReflection(primero, sortField);
            Object value2 = ShowCaseUtil.getPropertyValueViaReflection(segundo, sortField);

            // Comparar los valores si no son nulos
            if (value1 == null && value2 == null) return 0;
            if (value1 == null) return 1; // null es menor que cualquier valor
            if (value2 == null) return -1; // cualquier valor es mayor que null

            int value = ((Comparable<Object>) value1).compareTo(value2);

            // Retornar el valor basado en el orden de clasificación
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -value;
        } catch (Exception e) {
            throw new RuntimeException("Error al comparar elementos: " + e.getMessage(), e);
        }
    }

}
