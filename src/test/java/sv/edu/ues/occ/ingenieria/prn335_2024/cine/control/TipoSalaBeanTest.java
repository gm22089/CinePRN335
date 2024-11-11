package sv.edu.ues.occ.ingeneria.prn335_2024.cine.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingeneria.prn335_2024.cine.entity.TipoSala;

import static org.junit.jupiter.api.Assertions.*;

class TipoSalaBeanTest {

    @Test
    void create(){
        System.out.println("TipoSalaBeanTest create");
        TipoSala nuevo= new TipoSala();
        EntityManager mockEM= Mockito.mock(EntityManager.class);



        TipoSalaBean cut=new TipoSalaBean();
        assertThrows(IllegalArgumentException.class,()->{
            cut.create(null);
        });

        assertThrows(IllegalStateException.class,()->{
            cut.create(nuevo);
        });

        cut.en=mockEM;
        cut.create(nuevo);



        fail("Fallo exitosamente");
    }

    @Test
    void finById() {
        System.out.println("TipoSalaBeansTest. findByd");
        final Integer idEsperado = 1;
        TipoSala esperado = new TipoSala(idEsperado);
        TipoSalaBean cut=new TipoSalaBean();

        assertThrows(IllegalAccessException.class,()->{
           cut.findById(idEsperado);
        });

        EntityManager mock= Mockito.mock(EntityManager.class);
        Mockito.when(mock.find(TipoSala.class, idEsperado)).thenReturn(esperado);
        cut.en=mock;

        TipoSala resultado=cut.finById(idEsperado);

        assertNotNull(resultado);
        assertEquals(esperado, resultado);
        assertThrows(IllegalArgumentException.class, ()->{cut.findById(null);
        });

        //fail("Fallo exitosamente");
    }


}