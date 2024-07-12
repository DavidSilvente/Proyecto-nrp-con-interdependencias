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
import dao.ClienteHasRequisitoDAO;
import dao.RequisitoDAO;
import model.Cliente;
import model.ClienteHasRequisito;
import model.Requisito;

@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public ServletCliente() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
            case "crear_cliente":
                crear_cliente(request, response);
                break;
            case "mostrar_editar_cliente":
                mostrar_editar_cliente(request, response);
                break;
            case "editar_cliente":
                editar_cliente(request, response);
                break;
            case "eliminar_cliente":
                eliminar_cliente(request, response);
                break;
            default:
                break;
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }


    }

    private void eliminar_cliente(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException, IOException, ServletException {
        String id = request.getParameter("id");
        boolean borrar = ClienteDAO.borrar(Integer.parseInt(id));

        if (borrar) {
            getServletContext().getRequestDispatcher("/ServletInicio?action=mostrar_inicio").forward(request, response);
        }else {
            getServletContext().getRequestDispatcher("/ServletInicio?action=mostrar_inicio").forward(request, response);
        }
    }

    private void editar_cliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String nombre = request.getParameter("nombre");
        String peso = request.getParameter("peso");
        String id = request.getParameter("id");

        if (nombre == null || nombre.isEmpty()) {
            if (peso == null || peso.isEmpty()) {
                request.setAttribute("error", "No se ha introducido el nombre ni el peso del cliente");
                mostrar_editar_cliente(request, response);
            }
            request.setAttribute("error", "No se ha introducido el nombre del cliente");
            mostrar_editar_cliente(request, response);
        }else if (peso == null || peso.isEmpty()) {
            request.setAttribute("error", "No se ha introducido el peso del cliente");
            mostrar_editar_cliente(request, response);
        }else {
            if (!peso.matches("-?(0|[1-9]\\d*)")) {
                request.setAttribute("error", "Solo se pueden introducir números enteros en el peso");
                mostrar_editar_cliente(request, response);
            } else {
                Cliente cliente = ClienteDAO.obtenerPorID(Integer.parseInt(id));

                String nombreAnterior = cliente.getNombre();
                int prioridadAnterior = cliente.getPrioridad();

                cliente.setNombre(nombre.trim());
                cliente.setPrioridad(Integer.parseInt(peso));

                boolean actualizar = ClienteDAO.actualizar(cliente);

                if (actualizar) {
                    response.setContentType("text/html");
                    PrintWriter pw=response.getWriter();
                    pw.println("<script type=\"text/javascript\">");
                    pw.println("alert('El cliente se ha actualizado correctamente');");
                    pw.println("</script>");
                    RequestDispatcher rd=request.getRequestDispatcher("/ServletInicio?action=mostrar_inicio");
                    rd.include(request, response);
                }else {
                    request.setAttribute("error", "El nombre del cliente ya existe");
                    request.setAttribute("id", cliente.getId());
                    request.setAttribute("nombre", nombreAnterior);
                    request.setAttribute("prioridad", prioridadAnterior);
                    mostrar_editar_cliente(request, response);
                }
            }
        }
    }

    private void mostrar_editar_cliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("nombre", request.getParameter("nombre"));
        request.setAttribute("prioridad", request.getParameter("prioridad"));
        RequestDispatcher dispatcher= request.getRequestDispatcher("/editarCliente.jsp");
        dispatcher.forward(request, response);
    }

    private void crear_cliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String nombre = request.getParameter("nombre");
        String peso = request.getParameter("peso");

        if (nombre == null || nombre.isEmpty()) {
            if (peso == null || peso.isEmpty()) {
                request.setAttribute("error", "No se ha introducido el nombre ni el peso del cliente");
                getServletContext().getRequestDispatcher("/crearCliente.jsp").forward(request, response);
            }
            request.setAttribute("error", "No se ha introducido el nombre del cliente");
            getServletContext().getRequestDispatcher("/crearCliente.jsp").forward(request, response);
        }else if (peso == null || peso.isEmpty()) {
            request.setAttribute("error", "No se ha introducido el peso del cliente");
            getServletContext().getRequestDispatcher("/crearCliente.jsp").forward(request, response);
        }else {
            if (!peso.matches("-?(0|[1-9]\\d*)")) {
                request.setAttribute("error", "Solo se pueden introducir números enteros en el peso");
                getServletContext().getRequestDispatcher("/crearCliente.jsp").forward(request, response);
            } else {
                Cliente cliente = new Cliente(Integer.parseInt(peso), nombre.trim());
                boolean insertar = ClienteDAO.insertar(cliente);

                if (insertar) {
                	int id = ClienteDAO.obtenerPorNombre(nombre).getId();
                    response.setContentType("text/html");
                    PrintWriter pw=response.getWriter();
                    pw.println("<script type=\"text/javascript\">");
                    pw.println("alert('El cliente se ha creado correctamente');");
                    pw.println("</script>");
                    for (Requisito req : RequisitoDAO.listar()) {
                    	ClienteHasRequisito chr = new ClienteHasRequisito(0, id, req.getId());
                    	ClienteHasRequisitoDAO.insertar(chr);
                    }
                    RequestDispatcher rd=request.getRequestDispatcher("/ServletInicio?action=mostrar_inicio");
                    rd.include(request, response);
                }else {
                    request.setAttribute("error", "El nombre del cliente ya existe");
                    getServletContext().getRequestDispatcher("/crearCliente.jsp").forward(request, response);
                }
            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public String path() {
        return getServletContext().getRealPath("/");
    }
}
