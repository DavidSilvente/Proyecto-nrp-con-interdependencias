package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.ClienteHasRequisitoDAO;
import dao.RequisitoDAO;
import dao.RequisitoHasRequisitoDAO;
import model.Cliente;
import model.ClienteHasRequisito;
import model.Requisito;
import model.RequisitoHasRequisito;
import model.RequisitoHasRequisito.tipo;

@WebServlet("/ServletRequisito")
public class ServletRequisito extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletRequisito() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			switch (action) {
			case "mostrar_crear_requisito":
				mostrar_crear_requisito(request, response);
				break;
			case "crear_requisito":
				crear_requisito(request, response);
				break;
			case "eliminar_requisito":
				eliminar_requisito(request, response);
				break;
			case "mostrar_editar_requisito":
				mostrar_editar_requisito(request, response);
				break;
			case "editar_requisito":
				editar_requisito(request, response);
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.getStackTrace();
		}

	}

	private void editar_requisito(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String nombre = request.getParameter("nombre");
		String esfuerzo = request.getParameter("esfuerzo");
		int id = Integer.parseInt(request.getParameter("id"));

		boolean error = false;

		Enumeration<String> parametros = request.getParameterNames();

		TreeMap<Integer, Integer> parametrosValor = new TreeMap<Integer, Integer>();

		TreeMap<Integer, tipo> parametrosRelacion = new TreeMap<Integer, tipo>();

		while (parametros.hasMoreElements() && !error) {
			String param = parametros.nextElement();
			if (param.contains("valor")) {
				if (!request.getParameter(param).matches("-?(0|[1-9]\\d*)")) {
					request.setAttribute("errorCliente", "Solo se pueden introducir números enteros en el valor");
					getServletContext().getRequestDispatcher("/ServletRequisito?action=mostrar_crear_requisito")
							.forward(request, response);
					error = true;
				} else {
					parametrosValor.put(Integer.parseInt(param.replace("valor", "")),
							Integer.parseInt(request.getParameter(param)));
				}
			} else if (param.contains("tipoRelacion")) {
				parametrosRelacion.put(Integer.parseInt(param.replace("tipoRelacion", "")),
						tipo.valueOf(request.getParameter(param)));
			}
		}

		if (!error) {
			if (nombre == null || nombre.isEmpty()) {
				if (esfuerzo == null || esfuerzo.isEmpty()) {
					request.setAttribute("error", "No se ha introducido el nombre ni el esfuerzo del requisito");
					mostrar_editar_requisito(request, response);
				}
				request.setAttribute("error", "No se ha introducido el nombre del requisito");
				mostrar_editar_requisito(request, response);
			} else if (esfuerzo == null || esfuerzo.isEmpty()) {
				request.setAttribute("error", "No se ha introducido el esfuerzo del requisito");
				mostrar_editar_requisito(request, response);
			} else {
				if (!esfuerzo.matches("-?(0|[1-9]\\d*)")) {
					request.setAttribute("error", "Solo se pueden introducir números enteros en el esfuerzo");
					mostrar_editar_requisito(request, response);
				} else {
					
					Requisito requisito = new Requisito(id, Integer.parseInt(esfuerzo), nombre.trim());
					boolean actualizar = RequisitoDAO.actualizar(requisito);

					if (actualizar) {
						int idRequisito = RequisitoDAO.obtenerPorNombre(requisito.getNombre()).getId();
						for (Entry<Integer, Integer> entry : parametrosValor.entrySet()) {
							ClienteHasRequisito chr = new ClienteHasRequisito(entry.getValue().intValue(),
									entry.getKey().intValue(), idRequisito);
							ClienteHasRequisitoDAO.actualizar(chr);
						}
						for (Entry<Integer, tipo> entry : parametrosRelacion.entrySet()) {
							RequisitoHasRequisito rhr = new RequisitoHasRequisito(entry.getValue(), idRequisito,
									entry.getKey().intValue());
							if (RequisitoHasRequisitoDAO.obtenerPorID(idRequisito, entry.getKey().intValue()) != null) {
								if (entry.getValue().toString() == "norelacion") {
									RequisitoHasRequisitoDAO.borrar(idRequisito, entry.getKey());
								} else {
									RequisitoHasRequisitoDAO.actualizar(rhr);
								}
							} else {
								if (!rhr.getTipo().equals(tipo.norelacion)) {
									RequisitoHasRequisitoDAO.insertar(rhr);
								}
							}
						}
						response.setContentType("text/html");
						PrintWriter pw = response.getWriter();
						pw.println("<script type=\"text/javascript\">");
						pw.println("alert('El requisito se ha creado correctamente');");
						pw.println("</script>");
						RequestDispatcher rd = request.getRequestDispatcher("/ServletInicio?action=mostrar_inicio");
						rd.include(request, response);
					} else {
						request.setAttribute("error", "El nombre del requisito ya existe");
						mostrar_editar_requisito(request, response);
					}
				}
			}
		}
	}

	private void mostrar_editar_requisito(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NumberFormatException, SQLException {
		request.setAttribute("id", request.getParameter("id"));
		request.setAttribute("nombre", request.getParameter("nombre"));
		request.setAttribute("esfuerzo", request.getParameter("esfuerzo"));
		TreeMap<Cliente, Integer> treeClientes = new TreeMap<Cliente, Integer>();
		TreeMap<Requisito, String> treeRequisitos = new TreeMap<Requisito, String>();
		for (ClienteHasRequisito chr : ClienteHasRequisitoDAO
				.obtenerRelacionesRequisito(Integer.parseInt(request.getParameter("id")))) {
			treeClientes.put(ClienteDAO.obtenerPorID(chr.getCliente_id()), chr.getValor());
		}
		for (Requisito req : RequisitoDAO.listar()) {
			if (req.getId() != Integer.parseInt(request.getParameter("id"))) {
				String tipo = "";
				RequisitoHasRequisito rhr = RequisitoHasRequisitoDAO.obtenerPorID(req.getId(),
						Integer.parseInt(request.getParameter("id")));
				if (rhr != null) {
					tipo = rhr.getTipo().toString();
				}
				treeRequisitos.put(req, tipo);
			}
		}
		request.setAttribute("treeClientes", treeClientes);
		request.setAttribute("treeRequisitos", treeRequisitos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/editarRequisito.jsp");
		dispatcher.forward(request, response);
	}

	private void eliminar_requisito(HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, SQLException, ServletException, IOException {
		String id = request.getParameter("id");
		boolean borrar = RequisitoDAO.borrar(Integer.parseInt(id));

		if (borrar) {
			getServletContext().getRequestDispatcher("/ServletInicio?action=mostrar_inicio").forward(request, response);
		} else {
			getServletContext().getRequestDispatcher("/ServletInicio?action=mostrar_inicio").forward(request, response);
		}
	}

	private void mostrar_crear_requisito(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		request.setAttribute("listaClientes", ClienteDAO.listar());
		request.setAttribute("listaRequisitos", RequisitoDAO.listar());
		getServletContext().getRequestDispatcher("/crearRequisito.jsp").forward(request, response);
	}

	private void crear_requisito(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String nombre = request.getParameter("nombre");
		String esfuerzo = request.getParameter("esfuerzo");

		boolean error = false;

		Enumeration<String> parametros = request.getParameterNames();

		TreeMap<Integer, Integer> parametrosValor = new TreeMap<Integer, Integer>();

		TreeMap<Integer, tipo> parametrosRelacion = new TreeMap<Integer, tipo>();

		while (parametros.hasMoreElements() && !error) {
			String param = parametros.nextElement();
			if (param.contains("valor")) {
				if (!request.getParameter(param).matches("-?(0|[1-9]\\d*)")) {
					request.setAttribute("errorCliente", "Solo se pueden introducir números enteros en el valor");
					getServletContext().getRequestDispatcher("/ServletRequisito?action=mostrar_crear_requisito")
							.forward(request, response);
					error = true;
				} else {
					parametrosValor.put(Integer.parseInt(param.replace("valor", "")),
							Integer.parseInt(request.getParameter(param)));
				}
			} else if (param.contains("tipoRelacion")) {
				if (!request.getParameter(param).equals("no-relacion")) {
					parametrosRelacion.put(Integer.parseInt(param.replace("tipoRelacion", "")),
							tipo.valueOf(request.getParameter(param)));
				}
			}
		}

		if (!error) {
			if (nombre == null || nombre.isEmpty()) {
				if (esfuerzo == null || esfuerzo.isEmpty()) {
					request.setAttribute("error", "No se ha introducido el nombre ni el esfuerzo del requisito");
					getServletContext().getRequestDispatcher("/ServletRequisito?action=mostrar_crear_requisito")
							.forward(request, response);
				}
				request.setAttribute("error", "No se ha introducido el nombre del requisito");
				getServletContext().getRequestDispatcher("/ServletRequisito?action=mostrar_crear_requisito")
						.forward(request, response);
			} else if (esfuerzo == null || esfuerzo.isEmpty()) {
				request.setAttribute("error", "No se ha introducido el esfuerzo del requisito");
				getServletContext().getRequestDispatcher("/ServletRequisito?action=mostrar_crear_requisito")
						.forward(request, response);
			} else {
				if (!esfuerzo.matches("-?(0|[1-9]\\d*)")) {
					request.setAttribute("error", "Solo se pueden introducir números enteros en el esfuerzo");
					getServletContext().getRequestDispatcher("/ServletRequisito?action=mostrar_crear_requisito")
							.forward(request, response);
				} else {
					Requisito requisito = new Requisito(Integer.parseInt(esfuerzo), nombre.trim());
					boolean insertar = RequisitoDAO.insertar(requisito);

					if (insertar) {
						int idRequisito = RequisitoDAO.obtenerPorNombre(requisito.getNombre()).getId();
						for (Entry<Integer, Integer> entry : parametrosValor.entrySet()) {
							ClienteHasRequisito chr = new ClienteHasRequisito(entry.getValue().intValue(),
									entry.getKey().intValue(), idRequisito);
							ClienteHasRequisitoDAO.insertar(chr);
						}
						for (Entry<Integer, tipo> entry : parametrosRelacion.entrySet()) {
							RequisitoHasRequisito rhr = new RequisitoHasRequisito(entry.getValue(), idRequisito,
									entry.getKey().intValue());
							RequisitoHasRequisitoDAO.insertar(rhr);
						}
						response.setContentType("text/html");
						PrintWriter pw = response.getWriter();
						pw.println("<script type=\"text/javascript\">");
						pw.println("alert('El requisito se ha creado correctamente');");
						pw.println("</script>");
						RequestDispatcher rd = request.getRequestDispatcher("/ServletInicio?action=mostrar_inicio");
						rd.include(request, response);
					} else {
						request.setAttribute("error", "El nombre del requisito ya existe");
						getServletContext().getRequestDispatcher("/ServletRequisito?action=mostrar_crear_requisito")
								.forward(request, response);
					}
				}
			}
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
