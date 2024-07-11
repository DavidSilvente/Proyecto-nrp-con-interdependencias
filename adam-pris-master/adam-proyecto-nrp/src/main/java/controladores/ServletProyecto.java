package controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.ClienteHasRequisitoDAO;
import dao.ProyectoDAO;
import dao.RequisitoDAO;
import dao.RequisitoHasRequisitoDAO;
import dao.UsuarioDAO;
import dao.UsuarioHasProyectoDAO;
import model.Cliente;
import model.ClienteHasRequisito;
import model.Proyecto;
import model.Requisito;
import model.RequisitoHasRequisito;
import model.Usuario;
import model.UsuarioHasProyecto;
import model.RequisitoHasRequisito.tipo;

@WebServlet("/ServletProyecto")
public class ServletProyecto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static int proyecto = 2;

	public ServletProyecto() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			switch (action) {
			case "mostrar_inicio":
				mostrar_inicio(request, response);
				break;
			case "elegir_proyecto":
				elegir_proyecto(request, response);
				break;
			case "mostrar_editar_proyecto":
				mostrar_editar_proyecto(request, response);
				break;
			case "editar_proyecto":
				editar_proyecto(request, response);
				break;
			case "eliminar_proyecto":
				eliminar_proyecto(request, response);
				break;
			case "mostrar_crear_proyecto":
				mostrar_crear_proyecto(request, response);
				break;
			case "crear_proyecto":
				crear_proyecto(request, response);
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.getStackTrace();
		}
	}

	private void crear_proyecto(HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, ServletException, IOException, SQLException {
		String nombre = request.getParameter("nombre");

		Enumeration<String> parametros = request.getParameterNames();

		TreeMap<Integer, Boolean> usuarioAsignado = new TreeMap<Integer, Boolean>();

		while (parametros.hasMoreElements()) {
			String param = parametros.nextElement();
			if (param.contains("asignado")) {
				usuarioAsignado.put(Integer.parseInt(param.replace("asignado", "")),
						Boolean.valueOf(request.getParameter(param)));
			}
		}

		if (nombre == null || nombre.isEmpty()) {
			request.setAttribute("error", "No se ha introducido el nombre del requisito");
			mostrar_crear_proyecto(request, response);
		}

		Proyecto proyecto = new Proyecto(nombre);

		boolean insertar = ProyectoDAO.insertar(proyecto);

		if (insertar) {
			int id = ProyectoDAO.obtenerPorNombre(nombre).getId();
			for (Entry<Integer, Boolean> entry : usuarioAsignado.entrySet()) {
				UsuarioHasProyecto uhp = new UsuarioHasProyecto(entry.getKey().intValue(), id);
				if (entry.getValue() == true) {
					UsuarioHasProyectoDAO.insertar(uhp);
				}
			}
			for (Usuario user : UsuarioDAO.listarAdmins()) {
				UsuarioHasProyectoDAO.insertar(new UsuarioHasProyecto(user.getId(), id));
			}
		} else {
			request.setAttribute("error", "El nombre del proyecto ya existe");
			mostrar_crear_proyecto(request, response);
		}
		elegir_proyecto(request, response);
	}

	private void mostrar_crear_proyecto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<Usuario> usuarios = UsuarioDAO.listar();
		usuarios.removeAll(UsuarioDAO.listarAdmins());
		request.setAttribute("usuarios", usuarios);
		getServletContext().getRequestDispatcher("/crearProyecto.jsp").forward(request, response);
	}

	private void editar_proyecto(HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, ServletException, IOException, SQLException {
		String nombre = request.getParameter("nombre");
		int id = Integer.parseInt(request.getParameter("id"));

		boolean error = false;

		Enumeration<String> parametros = request.getParameterNames();

		TreeMap<Integer, Boolean> usuarioAsignado = new TreeMap<Integer, Boolean>();

		while (parametros.hasMoreElements() && !error) {
			String param = parametros.nextElement();
			if (param.contains("asignado")) {
				usuarioAsignado.put(Integer.parseInt(param.replace("asignado", "")),
						Boolean.valueOf(request.getParameter(param)));
			}
		}

		if (!error) {
			if (nombre == null || nombre.isEmpty()) {
				request.setAttribute("error", "No se ha introducido el nombre del requisito");
				mostrar_editar_proyecto(request, response);
			}

			Proyecto proyecto = ProyectoDAO.obtenerPorID(id);
			proyecto.setNombre(nombre);

			boolean actualizar = ProyectoDAO.actualizar(proyecto);

			if (actualizar) {

				for (Entry<Integer, Boolean> entry : usuarioAsignado.entrySet()) {
					UsuarioHasProyecto uhp = new UsuarioHasProyecto(entry.getKey().intValue(), id);
					if (UsuarioHasProyectoDAO.obtenerPorID(entry.getKey().intValue(), id) == null) {
						if (entry.getValue() == true) {
							UsuarioHasProyectoDAO.insertar(uhp);
						}
					} else {
						if (entry.getValue() == false) {
							UsuarioHasProyectoDAO.borrar(uhp);
						}
					}
				}
			} else {
				request.setAttribute("error", "El nombre del proyecto ya existe");
				mostrar_editar_proyecto(request, response);
			}
		}
		elegir_proyecto(request, response);
	}

	private void eliminar_proyecto(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException, ServletException, IOException {
        String id = request.getParameter("id");
        boolean borrar = ProyectoDAO.borrar(Integer.parseInt(id));

        if (borrar) {
            getServletContext().getRequestDispatcher("/ServletProyecto?action=elegir_proyecto").forward(request, response);
        }else {
            getServletContext().getRequestDispatcher("/ServletProyecto?action=elegir_proyecto").forward(request, response);        
        }
	}

	private void mostrar_editar_proyecto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NumberFormatException, SQLException {
		int id_proyecto = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("id", id_proyecto);
		request.setAttribute("nombre", request.getParameter("nombre"));
		TreeMap<Usuario, Boolean> treeUsuarios = new TreeMap<Usuario, Boolean>();
		for (Usuario user : UsuarioDAO.listar()) {
			if (!user.getAdmin()) {
				boolean asignado = UsuarioHasProyectoDAO.obtenerPorID(user.getId(), id_proyecto) == null ? false : true;
				treeUsuarios.put(UsuarioDAO.obtenerPorID(user.getId()), asignado);
			}
		}
		request.setAttribute("treeUsuarios", treeUsuarios);
		getServletContext().getRequestDispatcher("/editarProyecto.jsp").forward(request, response);
	}

	private void elegir_proyecto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<Proyecto> listaProyectos = UsuarioHasProyectoDAO.obtenerProyectosUsuario(ServletUsuario.id);
		List<Usuario> listaUsuarios = UsuarioDAO.listar();
		listaUsuarios.removeAll(UsuarioDAO.listarAdmins());
		request.setAttribute("listaProyectos", listaProyectos);
		request.setAttribute("listaUsuarios", listaUsuarios);
		request.setAttribute("admin", ServletUsuario.admin);
		getServletContext().getRequestDispatcher("/elegirProyecto.jsp").forward(request, response);
	}

	private void mostrar_inicio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		ServletProyecto.proyecto = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("listaClientes", ClienteDAO.listar());
		request.setAttribute("listaRequisitos", RequisitoDAO.listar());
		getServletContext().getRequestDispatcher("/inicio.jsp").forward(request, response);
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
