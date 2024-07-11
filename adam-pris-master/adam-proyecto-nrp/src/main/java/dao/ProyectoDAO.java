package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Conexion;
import model.Proyecto;

public class ProyectoDAO {
    public static boolean insertar(Proyecto proyecto) throws SQLException {

    	 if (obtenerPorNombre(proyecto.getNombre().trim()) != null) {
             return false;
         }
    	
        String query = "INSERT INTO `proyecto` (`nombre`) VALUES (?)";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setString(1, proyecto.getNombre());
        boolean filaAnadida = sentencia.executeUpdate() > 0;
        sentencia.close();

        Conexion.desconectarBD();

        return filaAnadida;
    }

    public static boolean borrar(Proyecto proyecto) throws SQLException {
        String query = "DELETE FROM proyecto WHERE `id` = ? AND `nombre` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, proyecto.getId());
        sentencia.setString(2, proyecto.getNombre());
        boolean filaBorrada = sentencia.executeUpdate() > 0;
        sentencia.close();

        Conexion.desconectarBD();

        return filaBorrada;
    }
    
    public static boolean borrar(int proyecto_id) throws SQLException {
        String query = "DELETE FROM proyecto WHERE `id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, proyecto_id);
        boolean filaBorrada = sentencia.executeUpdate() > 0;
        sentencia.close();

        Conexion.desconectarBD();

        return filaBorrada;
    }

    public static boolean borrar(String nombre) throws SQLException {
    	 String query = "DELETE FROM proyecto WHERE `nombre` = ?";

         Conexion.conectarBD();

         PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
         sentencia.setString(1, nombre);
         boolean filaBorrada = sentencia.executeUpdate() > 0;
         sentencia.close();

         Conexion.desconectarBD();

         return filaBorrada;
    }

    public static boolean actualizar(Proyecto proyecto) throws SQLException {

        if (obtenerPorNombre(proyecto.getNombre()) != null) {
            if (proyecto.getId() != obtenerPorNombre(proyecto.getNombre()).getId()) {
                return false;
            }
        }


        String query = "UPDATE `proyecto` SET  `nombre` = ? WHERE `id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setString(1, proyecto.getNombre());
        sentencia.setInt(2, proyecto.getId());
        boolean filaActualizada = sentencia.executeUpdate() > 0;
        sentencia.close();

        Conexion.desconectarBD();

        return filaActualizada;
    }

    public static Proyecto obtenerPorID(int id) throws SQLException {
    	Proyecto proyecto = null;
        String query = "SELECT * FROM `proyecto` WHERE `id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, id);

        ResultSet resultado = sentencia.executeQuery();

        if (resultado.next()) {
        	proyecto = new Proyecto(resultado.getInt("id"), resultado.getString("nombre"));
        } else {
            return null;
        }

        sentencia.close();

        Conexion.desconectarBD();

        return proyecto;
    }

    public static Proyecto obtenerPorNombre(String nombre) throws SQLException {
    	Proyecto proyecto = null;
        String query = "SELECT * FROM `proyecto` WHERE `nombre` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setString(1, nombre);

        ResultSet resultado = sentencia.executeQuery();

        if (resultado.next()) {
        	proyecto = new Proyecto(resultado.getInt("id"), resultado.getString("nombre"));
        } else {
            return null;
        }

        sentencia.close();

        Conexion.desconectarBD();

        return proyecto;
    }

    public static List<Proyecto> listar() throws SQLException {
        List<Proyecto> listaProyectos = new ArrayList<Proyecto>();
        String query = "SELECT * FROM `proyecto`";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            int id = resultado.getInt("id");
            String nombre = resultado.getString("nombre");
            
            Proyecto proyecto = new Proyecto(id, nombre);
            listaProyectos.add(proyecto);
        }

        sentencia.close();

        Conexion.desconectarBD();

        return listaProyectos;
    }

}
