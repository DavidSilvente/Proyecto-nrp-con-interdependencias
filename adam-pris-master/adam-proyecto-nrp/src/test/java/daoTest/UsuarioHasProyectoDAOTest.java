package daoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;

import dao.ProyectoDAO;
import dao.UsuarioDAO;
import dao.UsuarioHasProyectoDAO;
import model.Usuario;
import model.UsuarioHasProyecto;
import model.Proyecto;

public class UsuarioHasProyectoDAOTest {

    @Test
    void testInsertar() {
        try {
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
            Usuario usuario = new Usuario(1, "userLogin", "userPswd", true);
            UsuarioDAO.insertar(usuario);

            UsuarioHasProyecto relacion = new UsuarioHasProyecto(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId());
            UsuarioHasProyectoDAO.insertar(relacion);

            UsuarioHasProyecto relacion2 = UsuarioHasProyectoDAO.obtenerPorID(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId());

            UsuarioHasProyectoDAO.borrar(relacion);
            UsuarioDAO.borrar(usuario);
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
        	
            Usuario usuario = new Usuario(1, "userLogin", "userPswd", true);
            UsuarioDAO.insertar(usuario);

            UsuarioHasProyecto relacion = new UsuarioHasProyecto(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId());
            UsuarioHasProyectoDAO.insertar(relacion);

            UsuarioHasProyecto relacion2 = UsuarioHasProyectoDAO.obtenerPorID(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId());

            if (UsuarioHasProyectoDAO.obtenerPorID(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId())==null){
                fail("No se ha creado la relación");
            }

            UsuarioHasProyectoDAO.borrar(relacion);

            int idUsuario = UsuarioDAO.obtenerPorLogin("userLogin").getId();
            int idProyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();

            UsuarioDAO.borrar(usuario);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertTrue(UsuarioHasProyectoDAO.obtenerPorID(idUsuario, idProyecto) == null);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBorrarRelacionesUsuario() {
        try {
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
            Usuario usuario = new Usuario(1, "userLogin", "userPswd", true);
            UsuarioDAO.insertar(usuario);

            UsuarioHasProyecto relacion = new UsuarioHasProyecto(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId());
            UsuarioHasProyectoDAO.insertar(relacion);

            UsuarioHasProyecto relacion2 = UsuarioHasProyectoDAO.obtenerPorID(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId());

            if (UsuarioHasProyectoDAO.obtenerPorID(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId())==null){
                fail("No se ha creado la relación");
            }

            UsuarioHasProyectoDAO.borrarRelacionesUsuario(UsuarioDAO.obtenerPorLogin("userLogin").getId());

            int idUsuario = UsuarioDAO.obtenerPorLogin("userLogin").getId();
            int idProyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();

            UsuarioDAO.borrar(usuario);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertTrue(UsuarioHasProyectoDAO.obtenerPorID(idUsuario, idProyecto) == null);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBorrarRelacionesProyecto() {
        try {
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
            Usuario usuario = new Usuario(1, "userLogin", "userPswd", true);
            UsuarioDAO.insertar(usuario);

            UsuarioHasProyecto relacion = new UsuarioHasProyecto(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId());
            UsuarioHasProyectoDAO.insertar(relacion);

            UsuarioHasProyecto relacion2 = UsuarioHasProyectoDAO.obtenerPorID(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId());

            if (UsuarioHasProyectoDAO.obtenerPorID(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId())==null){
                fail("No se ha creado la relación");
            }
            
            UsuarioHasProyectoDAO.borrarRelacionesProyecto(ProyectoDAO.obtenerPorNombre("Pr1").getId());

            int idUsuario = UsuarioDAO.obtenerPorLogin("userLogin").getId();
            int idProyecto = ProyectoDAO.obtenerPorNombre("Pr1").getId();

            UsuarioDAO.borrar(usuario);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertTrue(UsuarioHasProyectoDAO.obtenerPorID(idUsuario, idProyecto) == null);

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
        	
            Usuario usuario = new Usuario(1, "userLogin", "userPswd", true);
            UsuarioDAO.insertar(usuario);

            UsuarioHasProyecto relacion = new UsuarioHasProyecto(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId());
            UsuarioHasProyectoDAO.insertar(relacion);

            List<UsuarioHasProyecto> lista = UsuarioHasProyectoDAO.obtenerRelacionesUsuario(UsuarioDAO.obtenerPorLogin("userLogin").getId());

            if (lista.size() != 1) {
                fail("Número de relaciones diferente de 1");
            }

            UsuarioHasProyecto relacion2 = lista.get(0);

            UsuarioHasProyectoDAO.borrarRelacionesUsuario(UsuarioDAO.obtenerPorLogin("userLogin").getId());

            UsuarioDAO.borrar(usuario);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertEquals(relacion, relacion2);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testObtenerRelacionesProyecto() {
        try {
        	Proyecto proyecto = new Proyecto("Pr1");
        	ProyectoDAO.insertar(proyecto);
        	
            Usuario usuario = new Usuario(1, "userLogin", "userPswd", true);
            UsuarioDAO.insertar(usuario);

            UsuarioHasProyecto relacion = new UsuarioHasProyecto(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId());
            UsuarioHasProyectoDAO.insertar(relacion);

            List<UsuarioHasProyecto> lista = UsuarioHasProyectoDAO.obtenerRelacionesProyecto(ProyectoDAO.obtenerPorNombre("Pr1").getId());
            
            if (lista.size() != 1) {
                fail("Número de relaciones diferente de 1");
            }

            UsuarioHasProyecto relacion2 = lista.get(0);

            UsuarioHasProyectoDAO.borrarRelacionesProyecto(ProyectoDAO.obtenerPorNombre("Pr1").getId());

            UsuarioDAO.borrar(usuario);
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
        	
            Usuario usuario = new Usuario(1, "userLogin", "userPswd", true);
            UsuarioDAO.insertar(usuario);

            UsuarioHasProyecto relacion = new UsuarioHasProyecto(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId());
            UsuarioHasProyectoDAO.insertar(relacion);

            UsuarioHasProyecto relacion2 = UsuarioHasProyectoDAO.obtenerPorID(UsuarioDAO.obtenerPorLogin("userLogin").getId(), ProyectoDAO.obtenerPorNombre("Pr1").getId());

            if (relacion2 == null) {
                fail("No se ha creado ninguna relación");
            }

            UsuarioHasProyectoDAO.borrar(relacion);

            UsuarioDAO.borrar(usuario);
            ProyectoDAO.borrar(ProyectoDAO.obtenerPorNombre("Pr1"));

            assertEquals(relacion, relacion2);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }
}
