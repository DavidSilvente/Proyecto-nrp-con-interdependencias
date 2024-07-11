package daoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import controladores.ServletProyecto;
import dao.ClienteDAO;
import dao.ProyectoDAO;
import model.Cliente;
import model.Proyecto;

class ClienteDAOTest {

    @Test
    void testInsertar() {
        try {
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();
        	
            Cliente cliente = new Cliente(1, "PruebaTestBBDD");

            ClienteDAO.insertar(cliente);

            Cliente cliente2 = ClienteDAO.obtenerPorNombre("PruebaTestBBDD");

            ClienteDAO.borrar(cliente);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertEquals(cliente, cliente2);

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
        	
            Cliente cliente = new Cliente(1, "PruebaTestBBDD");

            ClienteDAO.insertar(cliente);

            if (ClienteDAO.obtenerPorNombre("PruebaTestBBDD") == null) {
                fail("No se ha insertado el cliente");
            }

            ClienteDAO.borrar(cliente);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertTrue(ClienteDAO.obtenerPorNombre("PruebaTestBBDD") == null);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBorrarId() {
        try {
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();
        	
            Cliente cliente = new Cliente(1, "PruebaTestBBDD");

            ClienteDAO.insertar(cliente);

            if (ClienteDAO.obtenerPorNombre("PruebaTestBBDD") == null) {
                fail("No se ha insertado el cliente");
            }

            ClienteDAO.borrar(ClienteDAO.obtenerPorNombre(cliente.getNombre()));
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertTrue(ClienteDAO.obtenerPorNombre("PruebaTestBBDD") == null);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testActualizar() {
        try {
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();
        	
            Cliente cliente = new Cliente(1, "PruebaTestBBDD");

            ClienteDAO.insertar(cliente);
            
            Cliente cliente2 = ClienteDAO.obtenerPorNombre("PruebaTestBBDD");

            int id = cliente2.getId();

            cliente2.setNombre("PruebaActualizadaTestBBDD");

            cliente2.setPrioridad(2);

            ClienteDAO.actualizar(cliente2);

            Cliente cliente3 = ClienteDAO.obtenerPorID(id);

            ClienteDAO.borrar(cliente2);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertEquals(cliente2, cliente3);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBuscarNombre() {
        try {
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();
        	
            Cliente cliente = new Cliente(1, "PruebaTestBBDD");

            ClienteDAO.insertar(cliente);

            Cliente cliente2 = ClienteDAO.obtenerPorNombre("PruebaTestBBDD");

            ClienteDAO.borrar(cliente2);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertEquals(cliente2, cliente);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBuscarId() {
        try {
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
        	ServletProyecto.proyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();
        	
            Cliente cliente = new Cliente(1, "PruebaTestBBDD");

            ClienteDAO.insertar(cliente);

            Cliente cliente2 = ClienteDAO.obtenerPorNombre("PruebaTestBBDD");

            int id = cliente2.getId();

            Cliente cliente3 = ClienteDAO.obtenerPorID(id);

            ClienteDAO.borrar(cliente2);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertEquals(cliente, cliente3);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }
}
