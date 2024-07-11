package daoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import dao.ProyectoDAO;
import model.Proyecto;

public class ProyectoDAOTest {

	@Test
	void testInsertar() {
		try {
			Proyecto proyecto = new Proyecto("Pr1");
			ProyectoDAO.insertar(proyecto);

			Proyecto proyecto2 = ProyectoDAO.obtenerPorNombre("Pr1");
			Proyecto proyecto3 = new Proyecto(proyecto2.getId(),proyecto2.getNombre());

			ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

			assertEquals(proyecto2, proyecto3);

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

			if (ProyectoDAO.obtenerPorNombre("Pr1") == null) {
				fail("No se ha insertado el proyecto");
			}

			ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

			assertTrue(ProyectoDAO.obtenerPorNombre("Pr1") == null);

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

			Proyecto proyecto2 = ProyectoDAO.obtenerPorNombre("Pr1");

			int id = proyecto2.getId();

			proyecto2.setNombre("Pr1Actualizado");

			ProyectoDAO.actualizar(proyecto2);

			Proyecto proyecto3 = ProyectoDAO.obtenerPorID(id);

			ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1Actualizado"));

			assertEquals(proyecto2, proyecto3);

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

			Proyecto proyecto2 = ProyectoDAO.obtenerPorNombre("Pr1");
			Proyecto proyecto3 = new Proyecto(proyecto2.getId(),proyecto2.getNombre());
			
			ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

			assertEquals(proyecto2, proyecto3);

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

			Proyecto proyecto2 = ProyectoDAO.obtenerPorNombre("Pr1");
			int id = proyecto2.getId();

			Proyecto proyecto3 = ProyectoDAO.obtenerPorID(id);

			ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

			assertEquals(proyecto2, proyecto3);

		} catch (SQLException e) {
			e.printStackTrace();
			fail("Excepción SQL");
		}
	}

}
