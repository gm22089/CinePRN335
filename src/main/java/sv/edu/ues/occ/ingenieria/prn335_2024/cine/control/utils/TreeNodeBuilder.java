package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.utils;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.util.List;

public class TreeNodeBuilder <A,B>{
    private List<Object[]> listaDoble;
    private TreeNode root;
    public TreeNodeBuilder(List<Object[]> listaDoble) {
        this.listaDoble = listaDoble;
        root = new DefaultTreeNode("Root", null);
        A level1Actual = null;
        TreeNode level2nodo = null; // Declara la variable aquí

        for (Object[] result : listaDoble) {
            A level1 = (A) result[0];
            B level2 = (B) result[1];

            // Verificar si se necesita un nuevo nodo de level1
            if (level1Actual == null || !level1.equals(level1Actual)) {
                // Crear el nodo de la level1 y asignarlo a la variable
                level2nodo = new DefaultTreeNode(level1, root);
                level1Actual = level1; // Actualizar la level1 actual
            }

            // Crear el nodo de la level2 y añadirlo como hijo del nodo de la level1
            new DefaultTreeNode(level2, level2nodo);
        }

    }

    public TreeNode getRoot() {
        return root;
    }
}
