package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Conexion;
import model.Usuario;

public class UsuarioDAO {

	public static boolean inicioSesion(String login, String pass) throws SQLException {

		String query = "SELECT * FROM `usuario` WHERE `login` = ? AND `password` = ?";

		Conexion.conectarBD();

		PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
		sentencia.setString(1, login);
		sentencia.setString(2, pass);
		boolean filaExiste = sentencia.executeQuery().next() != false;
		sentencia.close();

		Conexion.desconectarBD();

		return filaExiste;
	}

	public static boolean insertar(Usuario usuario) throws SQLException {

		if (obtenerPorLogin(usuario.getLogin()) != null) {
            return false;
		}

		String query = "INSERT INTO `usuario` (`id`, `login`, `password`, `admin`) VALUES (?, ?, ?, ?)";

		Conexion.conectarBD();

		PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
		sentencia.setString(1, null);
		sentencia.setString(2, usuario.getLogin());
		sentencia.setString(3, usuario.getPassword());
		sentencia.setBoolean(4, usuario.getAdmin());
		boolean filaAnadida = sentencia.executeUpdate() > 0;
		sentencia.close();

		Conexion.desconectarBD();

		return filaAnadida;
	}

	public static boolean borrar(Usuario usuario) throws SQLException {
		String query = "DELETE FROM usuario WHERE `id` = ? AND `login` = ? AND `password` = ?";

		Conexion.conectarBD();

		PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
		sentencia.setInt(1, obtenerPorLogin(usuario.getLogin()).getId());
		sentencia.setString(2, usuario.getLogin());
		sentencia.setString(3, usuario.getPassword());
		boolean filaBorrada = sentencia.executeUpdate() > 0;
		sentencia.close();

		Conexion.desconectarBD();

		return filaBorrada;
	}

	public static boolean borrar(int id) throws SQLException {
		String query = "DELETE FROM usuario WHERE `usuario`.`id` = ?";

		Conexion.conectarBD();

		PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
		sentencia.setInt(1, id);
		boolean filaBorrada = sentencia.executeUpdate() > 0;
		sentencia.close();

		Conexion.desconectarBD();

		return filaBorrada;
	}

	public static boolean actualizar(Usuario usuario) throws SQLException {
		
		if (obtenerPorLogin(usuario.getLogin()) != null) {
            if (usuario.getId() != obtenerPorLogin(usuario.getLogin()).getId()) {
            	return false;
            }
		}
		
		String query = "UPDATE `usuario` SET `login` = ?, `password` = ?, `admin` = ? WHERE `usuario`.`id` = ?";

		Conexion.conectarBD();

		PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
		sentencia.setString(1, usuario.getLogin());
		sentencia.setString(2, usuario.getPassword());
		sentencia.setBoolean(3, usuario.getAdmin());
		sentencia.setInt(4, usuario.getId());
		boolean filaActualizada = sentencia.executeUpdate() > 0;
		sentencia.close();

		Conexion.desconectarBD();

		return filaActualizada;
	}

	public static Usuario obtenerPorID(int id) throws SQLException {
		Usuario usuario = null;
		String query = "SELECT * FROM `usuario` WHERE `id` = ?";

		Conexion.conectarBD();

		PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
		sentencia.setInt(1, id);

		ResultSet resultado = sentencia.executeQuery();

		if (resultado.next()) {
			usuario = new Usuario(resultado.getInt("id"), resultado.getString("login"), resultado.getString("password"),
					resultado.getBoolean("admin"));
		} else {
			return null;
		}

		sentencia.close();

		Conexion.desconectarBD();

		return usuario;
	}

	public static Usuario obtenerPorLogin(String login) throws SQLException {
		Usuario usuario = null;
		String query = "SELECT * FROM `usuario` WHERE `login` = ?";

		Conexion.conectarBD();

		PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
		sentencia.setString(1, login);

		ResultSet resultado = sentencia.executeQuery();

		if (resultado.next()) {
			usuario = new Usuario(resultado.getInt("id"), resultado.getString("login"), resultado.getString("password"),
					resultado.getBoolean("admin"));
		} else {
			return null;
		}

		sentencia.close();

		Conexion.desconectarBD();

		return usuario;
	}

	public static List<Usuario> listar() throws SQLException {
		List<Usuario> listaUsuario = new ArrayList<Usuario>();
		String query = "SELECT * FROM `usuario`";

		Conexion.conectarBD();

		PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);

		ResultSet resultado = sentencia.executeQuery();

		while (resultado.next()) {
			int id = resultado.getInt("id");
			String login = resultado.getString("login");
			String password = resultado.getString("password");
			Boolean admin = resultado.getBoolean("admin");

			Usuario usuario = new Usuario(id, login, password, admin);
			listaUsuario.add(usuario);
		}

		sentencia.close();

		Conexion.desconectarBD();

		return listaUsuario;
	}

	public static Boolean verificarAdminPorLogin(String login) throws SQLException {
		String query = "SELECT * FROM `usuario` WHERE `login` = ?";

		Conexion.conectarBD();

		PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);
		sentencia.setString(1, login);

		ResultSet resultado = sentencia.executeQuery();

		if (resultado.next() && resultado.getBoolean("admin")) {
			sentencia.close();
			Conexion.desconectarBD();
			return true;
		} else {
			sentencia.close();
			Conexion.desconectarBD();
			return false;
		}
	}
	
	public static List<Usuario> listarAdmins() throws SQLException {
		List<Usuario> listaUsuario = new ArrayList<Usuario>();
		String query = "SELECT * FROM `usuario` WHERE `admin` = 1";

		Conexion.conectarBD();

		PreparedStatement sentencia = Conexion.getConexion().prepareStatement(query);

		ResultSet resultado = sentencia.executeQuery();

		while (resultado.next()) {
			int id = resultado.getInt("id");
			String login = resultado.getString("login");
			String password = resultado.getString("password");
			Boolean admin = resultado.getBoolean("admin");

			Usuario usuario = new Usuario(id, login, password, admin);
			listaUsuario.add(usuario);
		}

		sentencia.close();

		Conexion.desconectarBD();

		return listaUsuario;
	}
}
