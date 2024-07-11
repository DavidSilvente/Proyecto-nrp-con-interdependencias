package model;

public class Proyecto implements Comparable<Proyecto>{
    private int id;
    private String nombre;
    
    public Proyecto (int id, String nombre) {
    	this.id = id;
    	this.nombre = nombre;
    }
    
    public Proyecto (String nombre) {
    	this.nombre = nombre;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Proyecto)) return false;
        Proyecto proyecto = (Proyecto)obj;

        if (!(this.id==proyecto.getId()) || !this.nombre.equals(proyecto.getNombre())) {
            return false;
        }
        return true;
    }
	
	@Override
	public int compareTo(Proyecto o) {
	    if(this.getId() > o.getId())
	        return 1;
	    else if(this.getId() < o.getId())
	        return -1;
	    return this.getNombre().compareTo(o.getNombre());
	}
}
