package daoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import controladores.ServletProyecto;
import dao.ClienteDAO;
import dao.ClienteHasRequisitoDAO;
import dao.ProyectoDAO;
import dao.RequisitoDAO;
import model.Cliente;
import model.ClienteHasRequisito;
import model.Proyecto;
import model.Requisito;

class ClienteHasRequisitoDAOTest {

    @Test
    void testInsertar() {
        try {
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();
        	
            Cliente cliente = new Cliente(1, "ClienteTestBBDD");
            ClienteDAO.insertar(cliente);

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            ClienteHasRequisito relacion = new ClienteHasRequisito(2, ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());
            ClienteHasRequisitoDAO.insertar(relacion);

            ClienteHasRequisito relacion2 = ClienteHasRequisitoDAO.obtenerPorID(ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());

            ClienteHasRequisitoDAO.borrar(relacion);
            ClienteDAO.borrar(cliente);
            RequisitoDAO.borrar(requisito);
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
        	
            Cliente cliente = new Cliente(1, "ClienteTestBBDD");
            ClienteDAO.insertar(cliente);

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            ClienteHasRequisito relacion = new ClienteHasRequisito(2, ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());
            ClienteHasRequisitoDAO.insertar(relacion);

            if (ClienteHasRequisitoDAO.obtenerPorID(ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId()) == null) {
                fail("No se ha creado la relación");
            }

            ClienteHasRequisitoDAO.borrar(relacion);

            int idCliente = ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId();
            int idRequisito = RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId();

            ClienteDAO.borrar(cliente);
            RequisitoDAO.borrar(requisito);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertTrue(ClienteHasRequisitoDAO.obtenerPorID(idCliente, idRequisito) == null);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBorrarRelacionesCliente() {
        try {
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();
        	
            Cliente cliente = new Cliente(1, "ClienteTestBBDD");
            ClienteDAO.insertar(cliente);

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            ClienteHasRequisito relacion = new ClienteHasRequisito(2, ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());
            ClienteHasRequisitoDAO.insertar(relacion);

            if (ClienteHasRequisitoDAO.obtenerPorID(ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId()) == null) {
                fail("No se ha creado la relación");
            }

            ClienteHasRequisitoDAO.borrarRelacionesCliente(ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId());

            int idCliente = ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId();
            int idRequisito = RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId();

            ClienteDAO.borrar(cliente);
            RequisitoDAO.borrar(requisito);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertTrue(ClienteHasRequisitoDAO.obtenerPorID(idCliente, idRequisito) == null);

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
        	
            Cliente cliente = new Cliente(1, "ClienteTestBBDD");
            ClienteDAO.insertar(cliente);

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            ClienteHasRequisito relacion = new ClienteHasRequisito(2, ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());
            ClienteHasRequisitoDAO.insertar(relacion);

            if (ClienteHasRequisitoDAO.obtenerPorID(ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId()) == null) {
                fail("No se ha creado la relación");
            }

            ClienteHasRequisitoDAO.borrarRelacionesRequisito(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());

            int idCliente = ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId();
            int idRequisito = RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId();

            ClienteDAO.borrar(cliente);
            RequisitoDAO.borrar(requisito);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertTrue(ClienteHasRequisitoDAO.obtenerPorID(idCliente, idRequisito) == null);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testObtenerRelacionesCliente() {
        try {
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();
        	
            Cliente cliente = new Cliente(1, "ClienteTestBBDD");
            ClienteDAO.insertar(cliente);

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            ClienteHasRequisito relacion = new ClienteHasRequisito(2, ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());
            ClienteHasRequisitoDAO.insertar(relacion);

            List<ClienteHasRequisito> lista = ClienteHasRequisitoDAO.obtenerRelacionesCliente(ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId());

            if (lista.size() != 1) {
                fail("Número de relaciones diferente de 1");
            }

            ClienteHasRequisito relacion2 = lista.get(0);

            ClienteHasRequisitoDAO.borrarRelacionesCliente(ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId());

            ClienteDAO.borrar(cliente);
            RequisitoDAO.borrar(requisito);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertEquals(relacion, relacion2);

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
        	
            Cliente cliente = new Cliente(1, "ClienteTestBBDD");
            ClienteDAO.insertar(cliente);

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            ClienteHasRequisito relacion = new ClienteHasRequisito(2, ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());
            ClienteHasRequisitoDAO.insertar(relacion);

            List<ClienteHasRequisito> lista = ClienteHasRequisitoDAO.obtenerRelacionesRequisito(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());

            if (lista.size() != 1) {
                fail("Número de relaciones diferente de 1");
            }

            ClienteHasRequisito relacion2 = lista.get(0);

            ClienteHasRequisitoDAO.borrarRelacionesRequisito(RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());

            ClienteDAO.borrar(cliente);
            RequisitoDAO.borrar(requisito);
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
        	
            Cliente cliente = new Cliente(1, "ClienteTestBBDD");
            ClienteDAO.insertar(cliente);

            Requisito requisito = new Requisito(1, "RequisitoTestBBDD");
            RequisitoDAO.insertar(requisito);

            ClienteHasRequisito relacion = new ClienteHasRequisito(2, ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());
            ClienteHasRequisitoDAO.insertar(relacion);

            ClienteHasRequisito relacion2 = ClienteHasRequisitoDAO.obtenerPorID(ClienteDAO.obtenerPorNombre("ClienteTestBBDD").getId(), RequisitoDAO.obtenerPorNombre("RequisitoTestBBDD").getId());

            if (relacion2 == null) {
                fail("No se ha creado ninguna relación");
            }

            ClienteHasRequisitoDAO.borrar(relacion);

            ClienteDAO.borrar(cliente);
            RequisitoDAO.borrar(requisito);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertEquals(relacion, relacion2);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }
}
