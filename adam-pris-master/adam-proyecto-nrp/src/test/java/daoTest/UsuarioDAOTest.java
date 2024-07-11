package daoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import dao.UsuarioDAO;
import model.Usuario;

class UsuarioDAOTest {

    @Test
    void testInsertar() {
        try {

            Usuario usuario = new Usuario("PruebaLoginBBDD", "PruebaContBBDD", false);

            UsuarioDAO.insertar(usuario);

            Usuario usuario2 = UsuarioDAO.obtenerPorLogin("PruebaLoginBBDD");

            UsuarioDAO.borrar(usuario);

            assertEquals(usuario, usuario2);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBorrar() {
        try {

            Usuario usuario = new Usuario("PruebaLoginBBDD", "PruebaContBBDD", true);

            UsuarioDAO.insertar(usuario);

            if (UsuarioDAO.obtenerPorLogin("PruebaLoginBBDD") == null) {
                fail("No se ha insertado un usuario");
            }

            UsuarioDAO.borrar(usuario);

            assertTrue(UsuarioDAO.obtenerPorLogin("PruebaLoginBBDD") == null);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBorrarId() {
        try {

            Usuario usuario = new Usuario("PruebaLoginBBDD", "PruebaContBBDD", true);

            UsuarioDAO.insertar(usuario);

            if (UsuarioDAO.obtenerPorLogin("PruebaLoginBBDD") == null) {
                fail("No se ha insertado un usuario");
            }

            UsuarioDAO.borrar(UsuarioDAO.obtenerPorLogin(usuario.getLogin()));

            assertTrue(UsuarioDAO.obtenerPorLogin("PruebaLoginBBDD") == null);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testActualizar() {
        try {

            Usuario usuario = new Usuario("PruebaLoginBBDD", "PruebaContBBDD", true);

            UsuarioDAO.insertar(usuario);

            Usuario usuario2 = UsuarioDAO.obtenerPorLogin("PruebaLoginBBDD");

            int id = usuario2.getId();

            usuario2.setLogin("LoginActualizadoTestBBDD");

            usuario2.setPassword("PassActualizadoTestBBDD");
            
            usuario2.setAdmin(false);

            UsuarioDAO.actualizar(usuario2);

            Usuario usuario3 = UsuarioDAO.obtenerPorID(id);

            UsuarioDAO.borrar(usuario2);

            assertEquals(usuario2, usuario3);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBuscarNombre() {
        try {

            Usuario usuario = new Usuario("PruebaLoginBBDD", "PruebaContBBDD", true);

            UsuarioDAO.insertar(usuario);

            Usuario usuario2 = UsuarioDAO.obtenerPorLogin("PruebaLoginBBDD");

            UsuarioDAO.borrar(usuario2);

            assertEquals(usuario2, usuario);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }

    @Test
    void testBuscarId() {
        try {

            Usuario usuario = new Usuario("PruebaLoginBBDD", "PruebaContBBDD", true);

            UsuarioDAO.insertar(usuario);

            Usuario usuario2 = UsuarioDAO.obtenerPorLogin("PruebaLoginBBDD");

            int id = usuario2.getId();

            Usuario usuario3 = UsuarioDAO.obtenerPorID(id);

            UsuarioDAO.borrar(usuario2);

            assertEquals(usuario, usuario3);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }
    
    @Test
    void testVerificarAdmin() {
        try {

            Usuario usuario = new Usuario("PruebaLoginBBDD", "PruebaContBBDD", false);
            
            UsuarioDAO.insertar(usuario);
            
            Boolean admin = UsuarioDAO.verificarAdminPorLogin("PruebaLoginBBDD");

            UsuarioDAO.borrar(usuario);

            assertEquals(admin, false);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Excepción SQL");
        }
    }
}
