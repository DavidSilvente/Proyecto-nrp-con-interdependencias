package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.ProyectoDAO;
import dao.UsuarioDAO;
import model.Cliente;
import model.Usuario;

@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UsuarioDAO usuarioDAO;
	public static boolean admin;
	public static int id;

	public ServletUsuario() {
		super();
	}

	@Override
	public void init() {

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			switch (action) {
			case "iniciar_sesion":
				iniciar_sesion(request, response);
			case "mostrar_editar_usuario":
				mostrar_editar_usuario(request, response);
				break;
			case "editar_usuario":
				editar_usuario(request, response);
				break;
			case "eliminar_usuario":
				eliminar_usuario(request, response);
				break;
			case "crear_usuario":
				crear_usuario(request, response);
				break;
			case "cerrar_sesion":
				cerrar_sesion(request, response);
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.getStackTrace();
		}

	}

	private void cerrar_sesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		id = -1;
		ServletProyecto.proyecto = -1;
        RequestDispatcher dispatcher= request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
	}

	private void mostrar_editar_usuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("login", request.getParameter("login"));
        RequestDispatcher dispatcher= request.getRequestDispatcher("/editarUsuario.jsp");
        dispatcher.forward(request, response);
	}

	private void editar_usuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException, SQLException {
		String id = request.getParameter("id");
		String login = request.getParameter("register-login");
		String pass = request.getParameter("register-passwd");
		String rpt_pass = request.getParameter("register-rpt-passwd");

		if (login == null || login.isBlank() || pass == null || pass.isBlank() || rpt_pass == null
				|| rpt_pass.isBlank()) {
			request.setAttribute("error", "Se ha dejado algún campo en blanco");
			request.setAttribute("login", login);
			request.setAttribute("id", id);
			getServletContext().getRequestDispatcher("/editarUsuario.jsp").forward(request, response);
		} else if (!pass.equals(rpt_pass)) {
			request.setAttribute("error", "Las contraseñas no son iguales");
			request.setAttribute("login", login);
			request.setAttribute("id", id);
			getServletContext().getRequestDispatcher("/editarUsuario.jsp").forward(request, response);
		} else {

            Usuario usuario = UsuarioDAO.obtenerPorID(Integer.parseInt(id));
            
            usuario.setLogin(login);
            usuario.setPassword(pass);

			boolean actualizar = UsuarioDAO.actualizar(usuario);

			if (actualizar) {
				response.setContentType("text/html");
				PrintWriter pw = response.getWriter();
				pw.println("<script type=\"text/javascript\">");
				pw.println("alert('El cliente se ha actualizado correctamente');");
				pw.println("</script>");
				getServletContext().getRequestDispatcher("/ServletProyecto?action=elegir_proyecto").forward(request,
						response);				
			} else {
				request.setAttribute("error", "El login del usuario ya existe");
				request.setAttribute("login", login);
				request.setAttribute("id", id);
				getServletContext().getRequestDispatcher("/editarUsuario.jsp").forward(request,
						response);
			}
		}
	}

	private void eliminar_usuario(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException, ServletException, IOException {
        String id = request.getParameter("id");
        boolean borrar = UsuarioDAO.borrar(Integer.parseInt(id));

        if (borrar) {
            getServletContext().getRequestDispatcher("/ServletProyecto?action=elegir_proyecto").forward(request, response);
        }else {
            getServletContext().getRequestDispatcher("/ServletProyecto?action=elegir_proyecto").forward(request, response);        
        }
	}

	private void crear_usuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String login = request.getParameter("register-login");
		String pass = request.getParameter("register-passwd");
		String rpt_pass = request.getParameter("register-rpt-passwd");

		if (login == null || login.isBlank() || pass == null || pass.isBlank() || rpt_pass == null
				|| rpt_pass.isBlank()) {
			request.setAttribute("error", "Se ha dejado algún campo en blanco");
			getServletContext().getRequestDispatcher("/crearUsuario.jsp").forward(request, response);
		} else if (!pass.equals(rpt_pass)) {
			request.setAttribute("error", "Las contraseñas no son iguales");
			getServletContext().getRequestDispatcher("/crearUsuario.jsp").forward(request, response);
		} else {

			Usuario usuario = new Usuario(login.trim(), pass, false);

			boolean insertar = UsuarioDAO.insertar(usuario);

			if (insertar) {
				response.setContentType("text/html");
				PrintWriter pw = response.getWriter();
				pw.println("<script type=\"text/javascript\">");
				pw.println("alert('El cliente se ha actualizado correctamente');");
				pw.println("</script>");
				getServletContext().getRequestDispatcher("/ServletProyecto?action=elegir_proyecto").forward(request,
						response);				
			} else {
				request.setAttribute("error", "El login del usuario ya existe");
				request.setAttribute("login", login);
				getServletContext().getRequestDispatcher("/crearUsuario.jsp").forward(request,
						response);
			}
		}
	}

	private void iniciar_sesion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String login = request.getParameter("username");
		String pass = request.getParameter("password");
		boolean iniciar = UsuarioDAO.inicioSesion(login, pass);

		if (iniciar) {
			Usuario usuario = UsuarioDAO.obtenerPorLogin(login);
			admin = usuario.getAdmin();
			id = usuario.getId();

			getServletContext().getRequestDispatcher("/ServletProyecto?action=elegir_proyecto").forward(request,
					response);
		} else {
			request.setAttribute("error", "El nombre o contraseña son incorrectos");
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
