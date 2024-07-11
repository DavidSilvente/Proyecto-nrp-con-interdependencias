package controladores;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.RequisitoDAO;

@WebServlet("/ServletInicio")
public class ServletInicio extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public ServletInicio() {
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
            default:
                break;
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }


    }

	private void mostrar_inicio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        request.setAttribute("listaClientes", ClienteDAO.listar());
        request.setAttribute("listaRequisitos", RequisitoDAO.listar());
        getServletContext().getRequestDispatcher("/inicio.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
