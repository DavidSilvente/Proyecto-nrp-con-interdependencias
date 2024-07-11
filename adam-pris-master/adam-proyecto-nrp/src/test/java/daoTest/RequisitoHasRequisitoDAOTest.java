package daoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import controladores.ServletProyecto;
import dao.ProyectoDAO;
import dao.RequisitoDAO;
import dao.RequisitoHasRequisitoDAO;
import model.Proyecto;
import model.Requisito;
import model.RequisitoHasRequisito;
import model.RequisitoHasRequisito.tipo;

class RequisitoHasRequisitoDAOTest {

    @Test
    void testInsertar() {
        try {
        	
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            Requisito requisito2 = new Requisito(2, "Requisito2TestBBDD");
            RequisitoDAO.insertar(requisito2);

            RequisitoHasRequisito relacion = new RequisitoHasRequisito(tipo.combinacion, RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId());

            RequisitoHasRequisitoDAO.insertar(relacion);

            RequisitoHasRequisito relacion2 = RequisitoHasRequisitoDAO.obtenerPorID(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId());

            RequisitoHasRequisitoDAO.borrar(relacion);
            RequisitoDAO.borrar(requisito);
            RequisitoDAO.borrar(requisito2);
            
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertEquals(relacion, relacion2);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBorrar() {
        try {

        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            Requisito requisito2 = new Requisito(2, "Requisito2TestBBDD");
            RequisitoDAO.insertar(requisito2);

            RequisitoHasRequisito relacion = new RequisitoHasRequisito(tipo.combinacion, RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId());

            RequisitoHasRequisitoDAO.insertar(relacion);

            if (RequisitoHasRequisitoDAO.obtenerPorID(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId()) == null) {
                fail("No se ha creado ninguna relación");
            }

            RequisitoHasRequisitoDAO.borrar(relacion);

            int id = RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId();
            int id2 = RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId();

            RequisitoDAO.borrar(requisito);
            RequisitoDAO.borrar(requisito2);
            
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertTrue(RequisitoHasRequisitoDAO.obtenerPorID(id, id2) == null);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBorrarRelacionesIds() {
        try {

        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            Requisito requisito2 = new Requisito(2, "Requisito2TestBBDD");
            RequisitoDAO.insertar(requisito2);

            RequisitoHasRequisito relacion = new RequisitoHasRequisito(tipo.combinacion, RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId());

            RequisitoHasRequisitoDAO.insertar(relacion);

            if (RequisitoHasRequisitoDAO.obtenerPorID(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId()) == null) {
                fail("No se ha creado la relación");
            }

            RequisitoHasRequisitoDAO.borrar(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId());

            int id = RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId();
            int id2 = RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId();

            RequisitoDAO.borrar(requisito);
            RequisitoDAO.borrar(requisito2);
            
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertTrue(RequisitoHasRequisitoDAO.obtenerPorID(id, id2) == null);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBorrarRelacionesRequisito() {
        try {

        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            Requisito requisito2 = new Requisito(2, "Requisito2TestBBDD");
            RequisitoDAO.insertar(requisito2);

            RequisitoHasRequisito relacion = new RequisitoHasRequisito(tipo.combinacion, RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId());
            RequisitoHasRequisitoDAO.insertar(relacion);

            List<RequisitoHasRequisito> lista = RequisitoHasRequisitoDAO.obtenerRelacionesRequisito(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());

            RequisitoHasRequisitoDAO.borrarRelacionesRequisito(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());

            List<RequisitoHasRequisito> lista2 = RequisitoHasRequisitoDAO.obtenerRelacionesRequisito(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());

            RequisitoDAO.borrar(requisito);
            RequisitoDAO.borrar(requisito2);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertEquals(lista.size(), lista2.size() + 1);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testObtenerRelacionesRequisito() {
        try {

        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            Requisito requisito2 = new Requisito(2, "Requisito2TestBBDD");
            RequisitoDAO.insertar(requisito2);

            RequisitoHasRequisito relacion = new RequisitoHasRequisito(tipo.combinacion, RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId());
            RequisitoHasRequisitoDAO.insertar(relacion);

            List<RequisitoHasRequisito> lista = RequisitoHasRequisitoDAO.obtenerRelacionesRequisito(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());

            if (lista.size() != 1) {
                fail("Número de relaciones diferente de 1");
            }

            RequisitoHasRequisito relacion2 = lista.get(0);

            RequisitoHasRequisitoDAO.borrarRelacionesRequisito(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());

            RequisitoDAO.borrar(requisito);
            RequisitoDAO.borrar(requisito2);
            
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertEquals(relacion, relacion2);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void obtenerPorId() {
        try {

        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            Requisito requisito2 = new Requisito(2, "Requisito2TestBBDD");
            RequisitoDAO.insertar(requisito2);

            RequisitoHasRequisito relacion = new RequisitoHasRequisito(tipo.combinacion, RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId());
            RequisitoHasRequisitoDAO.insertar(relacion);

            RequisitoHasRequisito relacion2 = RequisitoHasRequisitoDAO.obtenerPorID(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("Requisito2TestBBDD").getId());

            RequisitoHasRequisitoDAO.borrarRelacionesRequisito(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());

            RequisitoDAO.borrar(requisito);
            RequisitoDAO.borrar(requisito2);
            
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertEquals(relacion, relacion2);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }
}
