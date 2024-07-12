package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Conexion;
import model.Proyecto;
import model.Usuario;
import model.UsuarioHasProyecto;

public class UsuarioHasProyectoDAO {
	public static boolean insertar(UsuarioHasProyecto relacion) throws SQLException {
        String query = "INSERT INTO `usuario_has_proyecto` (`usuario_id`, `proyecto_id`) VALUES (?, ?)";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, relacion.getUsuario_id());
        sentencia.setInt(2, relacion.getProyecto_id());
        boolean filaAnadida = sentencia.executeUpdate() > 0;
        sentencia.close();

        Conexion.desconectarBD();

        return filaAnadida;
    }

    public static boolean borrar(UsuarioHasProyecto relacion) throws SQLException {
        String query = "DELETE FROM usuario_has_proyecto WHERE `usuario_id` = ? AND `proyecto_id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, relacion.getUsuario_id());
        sentencia.setInt(2, relacion.getProyecto_id());
        boolean filaBorrada = sentencia.executeUpdate() > 0;
        sentencia.close();

        Conexion.desconectarBD();

        return filaBorrada;
    }

    public static boolean borrar(int usuario_id, int proyecto_id) throws SQLException {
        String query = "DELETE FROM usuario_has_proyecto WHERE `usuario_id` = ? AND `proyecto_id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, usuario_id);
        sentencia.setInt(2, proyecto_id);
        boolean filaBorrada = sentencia.executeUpdate() > 0;
        sentencia.close();

        Conexion.desconectarBD();

        return filaBorrada;
    }

    public static boolean borrarRelacionesUsuario(int usuario_id) {

        List<UsuarioHasProyecto> relaciones;

        try {
            relaciones = obtenerRelacionesUsuario(usuario_id);

            for (UsuarioHasProyecto relacion : relaciones) {
                borrar(relacion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean borrarRelacionesProyecto(int proyecto_id) {

        List<UsuarioHasProyecto> relaciones;

        try {
            relaciones = obtenerRelacionesProyecto(proyecto_id);

            for (UsuarioHasProyecto relacion : relaciones) {
                borrar(relacion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static UsuarioHasProyecto obtenerPorID(int usuario_id, int proyecto_id) throws SQLException {
    	UsuarioHasProyecto relacion = null;
        String query = "SELECT * FROM `usuario_has_proyecto` WHERE `usuario_id` = ? AND `proyecto_id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, usuario_id);
        sentencia.setInt(2, proyecto_id);

        ResultSet resultado = sentencia.executeQuery();

        if (resultado.next()) {
            relacion = new UsuarioHasProyecto(resultado.getInt("usuario_id"), resultado.getInt("proyecto_id"));
        } else {
            return null;
        }

        sentencia.close();

        Conexion.desconectarBD();

        return relacion;
    }

    public static List<Proyecto> obtenerProyectosUsuario(int usuario_id) throws SQLException {
        List<Proyecto> relaciones = new ArrayList<Proyecto>();
        String query = "SELECT * FROM `usuario_has_proyecto` WHERE `usuario_id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, usuario_id);

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            relaciones.add(ProyectoDAO.obtenerPorID(resultado.getInt("proyecto_id")));
        }

        sentencia.close();

        Conexion.desconectarBD();

        return relaciones;
    }

    public static List<Usuario> obtenerUsuariosProyecto(int proyecto_id) throws SQLException {
        List<Usuario> relaciones = new ArrayList<Usuario>();
        String query = "SELECT * FROM `usuario_has_proyecto` WHERE `proyecto_id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, proyecto_id);

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            relaciones.add(UsuarioDAO.obtenerPorID(resultado.getInt("usuario_id")));
        }

        sentencia.close();

        Conexion.desconectarBD();

        return relaciones;
    }
    
    public static List<UsuarioHasProyecto> obtenerRelacionesUsuario(int usuario_id) throws SQLException {
        List<UsuarioHasProyecto> relaciones = new ArrayList<UsuarioHasProyecto>();
        String query = "SELECT * FROM `usuario_has_proyecto` WHERE `usuario_id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, usuario_id);

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            relaciones.add(new UsuarioHasProyecto(resultado.getInt("usuario_id"), resultado.getInt("proyecto_id")));
        }

        sentencia.close();

        Conexion.desconectarBD();

        return relaciones;
    }

    public static List<UsuarioHasProyecto> obtenerRelacionesProyecto(int proyecto_id) throws SQLException {
        List<UsuarioHasProyecto> relaciones = new ArrayList<UsuarioHasProyecto>();
        String query = "SELECT * FROM `usuario_has_proyecto` WHERE `proyecto_id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, proyecto_id);

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            relaciones.add(new UsuarioHasProyecto(resultado.getInt("usuario_id"), resultado.getInt("proyecto_id")));
        }

        sentencia.close();

        Conexion.desconectarBD();

        return relaciones;
    }

    public static List<UsuarioHasProyecto> listar() throws SQLException {
        List<UsuarioHasProyecto> listaUsuarioHasProyecto = new ArrayList<UsuarioHasProyecto>();
        String query = "SELECT * FROM `usuario_has_proyecto`";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            int usuario_id = resultado.getInt("usuario_id");
            int proyecto_id = resultado.getInt("proyecto_id");

            UsuarioHasProyecto relacion = new UsuarioHasProyecto(usuario_id, proyecto_id);
            listaUsuarioHasProyecto.add(relacion);
        }

        sentencia.close();

        Conexion.desconectarBD();

        return listaUsuarioHasProyecto;
    }
}
