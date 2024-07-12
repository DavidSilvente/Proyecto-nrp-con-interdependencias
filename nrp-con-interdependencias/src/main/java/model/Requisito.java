package model;

import java.sql.SQLException;

import controladores.ServletProyecto;
import dao.ClienteDAO;
import dao.ClienteHasRequisitoDAO;

public class Requisito implements Comparable<Requisito>{
    private int id;
    private int esfuerzo;
    private String nombre;
    private int satisfaccion;
    private int proyecto_id = ServletProyecto.proyecto;

    public Requisito(int id, int esfuerzo, String nombre) {
        this.id = id;
        this.esfuerzo = esfuerzo;
        this.nombre = nombre;
        calcularSatisfaccion();
    }

    public Requisito(int esfuerzo, String nombre) {
        this.esfuerzo = esfuerzo;
        this.nombre = nombre;
        calcularSatisfaccion();
    }

    public int getProyecto_id() {
        return proyecto_id;
    }

    public int getId() {
        return id;
    }

    public int getSatisfaccion() {
		return satisfaccion;
	}

	public void setSatisfaccion(int satisfaccion) {
		this.satisfaccion = satisfaccion;
	}

	public int getEsfuerzo() {
        return esfuerzo;
    }

    public void setEsfuerzo(int esfuerzo) {
        this.esfuerzo = esfuerzo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    private void calcularSatisfaccion() {
    	int satisfaccion = 0;
    	try {
			for (ClienteHasRequisito chr : ClienteHasRequisitoDAO.obtenerRelacionesRequisito(id)) {
				satisfaccion += (chr.getValor() * ClienteDAO.obtenerPorID(chr.getCliente_id()).getPrioridad());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.satisfaccion = satisfaccion;
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Requisito)) return false;
        Requisito requisito = (Requisito) obj;

        if (!this.nombre.equals(requisito.getNombre()) || this.esfuerzo != requisito.getEsfuerzo() || this.proyecto_id != requisito.getProyecto_id()) {
            return false;
        }
        return true;
    }
    
	@Override
	public int compareTo(Requisito o) {
	    if(this.getId() > o.getId())
	        return 1;
	    else if(this.getId() < o.getId())
	        return -1;
	    return this.getNombre().compareTo(o.getNombre());
	}
}
