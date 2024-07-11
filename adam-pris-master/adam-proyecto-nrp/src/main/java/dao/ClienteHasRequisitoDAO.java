package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ClienteHasRequisito;
import model.Conexion;

public class ClienteHasRequisitoDAO {

	public static boolean insertar(ClienteHasRequisito relacion) throws SQLException {
        String query = "INSERT INTO `cliente_has_requisito` (`cliente_id`, `requisito_id`, `valor`) VALUES (?, ?, ?)";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, relacion.getCliente_id());
        sentencia.setInt(2, relacion.getRequisito_id());
        sentencia.setInt(3, relacion.getValor());
        boolean filaAnadida = sentencia.executeUpdate() > 0;
        sentencia.close();

        Conexion.desconectarBD();

        return filaAnadida;
    }

    public static boolean borrar(ClienteHasRequisito relacion) throws SQLException {
        String query = "DELETE FROM cliente_has_requisito WHERE `cliente_id` = ? AND `requisito_id` = ? AND `valor` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, relacion.getCliente_id());
        sentencia.setInt(2, relacion.getRequisito_id());
        sentencia.setInt(3, relacion.getValor());
        boolean filaBorrada = sentencia.executeUpdate() > 0;
        sentencia.close();

        Conexion.desconectarBD();

        return filaBorrada;
    }

    public static boolean borrar(int cliente_id, int requisito_id) throws SQLException {
        String query = "DELETE FROM cliente_has_requisito WHERE `cliente_id` = ? AND `requisito_id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, cliente_id);
        sentencia.setInt(2, requisito_id);
        boolean filaBorrada = sentencia.executeUpdate() > 0;
        sentencia.close();

        Conexion.desconectarBD();

        return filaBorrada;
    }

    public static boolean borrarRelacionesCliente(int cliente_id) {

        List<ClienteHasRequisito> relaciones;

        try {
            relaciones = obtenerRelacionesCliente(cliente_id);

            for (ClienteHasRequisito relacion : relaciones) {
                borrar(relacion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean borrarRelacionesRequisito(int requisito_id) {

        List<ClienteHasRequisito> relaciones;

        try {
            relaciones = obtenerRelacionesRequisito(requisito_id);

            for (ClienteHasRequisito relacion : relaciones) {
                borrar(relacion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public static boolean actualizar(ClienteHasRequisito relacion) throws SQLException {
        String query = "UPDATE `cliente_has_requisito` SET `valor` = ? WHERE `cliente_id` = ? AND `requisito_id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, relacion.getValor());
        sentencia.setInt(2, relacion.getCliente_id());
        sentencia.setInt(3, relacion.getRequisito_id());
        boolean filaActualizada = sentencia.executeUpdate() > 0;
        sentencia.close();

        Conexion.desconectarBD();

        return filaActualizada;
    }

    public static ClienteHasRequisito obtenerPorID(int cliente_id, int requisito_id) throws SQLException {
    	ClienteHasRequisito relacion = null;
        String query = "SELECT * FROM `cliente_has_requisito` WHERE `cliente_id` = ? AND `requisito_id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, cliente_id);
        sentencia.setInt(2, requisito_id);

        ResultSet resultado = sentencia.executeQuery();

        if (resultado.next()) {
            relacion = new ClienteHasRequisito(resultado.getInt("valor"), resultado.getInt("cliente_id"), resultado.getInt("requisito_id"));
        } else {
            return null;
        }

        sentencia.close();

        Conexion.desconectarBD();

        return relacion;
    }

    public static List<ClienteHasRequisito> obtenerRelacionesCliente(int cliente_id) throws SQLException {
        List<ClienteHasRequisito> relaciones = new ArrayList<ClienteHasRequisito>();
        String query = "SELECT * FROM `cliente_has_requisito` WHERE `cliente_id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, cliente_id);

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            relaciones.add(new ClienteHasRequisito(resultado.getInt("valor"), resultado.getInt("cliente_id"), resultado.getInt("requisito_id")));
        }

        sentencia.close();

        Conexion.desconectarBD();

        return relaciones;
    }

    public static List<ClienteHasRequisito> obtenerRelacionesRequisito(int requisito_id) throws SQLException {
        List<ClienteHasRequisito> relaciones = new ArrayList<ClienteHasRequisito>();
        String query = "SELECT * FROM `cliente_has_requisito` WHERE `requisito_id` = ?";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
        sentencia.setInt(1, requisito_id);

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            relaciones.add(new ClienteHasRequisito(resultado.getInt("valor"), resultado.getInt("cliente_id"), resultado.getInt("requisito_id")));
        }

        sentencia.close();

        Conexion.desconectarBD();

        return relaciones;
    }

    public static List<ClienteHasRequisito> listar() throws SQLException {
        List<ClienteHasRequisito> listaClienteHasRequisito = new ArrayList<ClienteHasRequisito>();
        String query = "SELECT * FROM `cliente_has_requisito`";

        Conexion.conectarBD();

        PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            int valor = resultado.getInt("valor");
            int cliente_id = resultado.getInt("cliente_id");
            int requisito_id = resultado.getInt("requisito_id");

            ClienteHasRequisito relacion = new ClienteHasRequisito(valor, cliente_id, requisito_id);
            listaClienteHasRequisito.add(relacion);
        }

        sentencia.close();

        Conexion.desconectarBD();

        return listaClienteHasRequisito;
    }
}
