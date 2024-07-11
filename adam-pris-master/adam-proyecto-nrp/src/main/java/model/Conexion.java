package model;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;

public class Conexion {

    static java.sql.Connection conexion;
    static private Statement sentencia;
    static String host = "localhost";
    static String bd = "nrp_db";
    static String user = "root";
    static String password = "";

    private static boolean registrarDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean conectarBD() {
        if (registrarDriver()) {
            try {
                final String urlBD = "jdbc:mysql://" + host + "/" + bd
                        + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                conexion = DriverManager.getConnection(urlBD, user, password);
                sentencia = conexion.createStatement(1005, 1008);
                return true;
            } catch (SQLException e) {
                System.out.println("Error SQL");
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static void desconectarBD() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }

    public static java.sql.Connection getConexion() {
        return conexion;
    }

    public static void setConexion(java.sql.Connection conexion) {
        Conexion.conexion = conexion;
    }

    public static void datos() throws SQLException, ServletException, IOException {
        //ServletRequest request = new HttpServletRequest();
        //ServletResponse respuesta = new ServletResponse("");
        Conexion.conectarBD();
        Statement sentencia = Conexion.conexion.createStatement();

        ResultSet resultado = sentencia.executeQuery("SELECT * FROM `cliente` WHERE `nombre`='Juan'");
        resultado.next();
        String nombre = resultado.getString("prioridad");

        System.out.println(nombre);

        /*request.setAttribute("nombre", nombre);

        RequestDispatcher dispatcher = request.getRequestDispatcher("NewFile.jsp");
        dispatcher.forward(request, respuesta);*/
    }

    public static void main (String[]args) throws SQLException, ServletException, IOException {
        datos();
    }
}
