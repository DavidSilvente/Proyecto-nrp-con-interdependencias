package model;

import controladores.ServletProyecto;

public class Cliente implements Comparable<Cliente>{
    private int id;
    private int prioridad;
    private String nombre;
    private int proyecto_id = ServletProyecto.proyecto;

    public Cliente(int id, int prioridad, String nombre) {
        this.id = id;
        this.prioridad = prioridad;
        this.nombre = nombre;
    }

    public Cliente(int prioridad, String nombre) {
        this.prioridad = prioridad;
        this.nombre = nombre;
    }
    
    public Cliente(String nombre, int prioridad) {
        this.prioridad = prioridad;
        this.nombre = nombre;
    }

    public int getProyecto_id() {
        return proyecto_id;
    }

    public int getId() {
        return id;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Cliente)) return false;
        Cliente cliente = (Cliente) obj;

        if (!this.nombre.equals(cliente.getNombre()) || this.prioridad != cliente.getPrioridad() || this.proyecto_id != cliente.getProyecto_id()) {
            return false;
        }
        return true;
    }

	@Override
	public int compareTo(Cliente o) {
		Cliente cli = (Cliente)o;
		
		return (this.nombre.compareTo(cli.nombre));
	}
    
//	@Override
//	public int compareTo(Cliente o) {
//	    if(this.getId() > o.getId())
//	        return 1;
//	    else if(this.getId() < o.getId())
//	        return -1;
//	    return this.getNombre().compareTo(o.getNombre());
//	}
}
