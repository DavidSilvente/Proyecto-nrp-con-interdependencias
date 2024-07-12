package model;

public class UsuarioHasProyecto {
    private int usuario_id;
    private int proyecto_id;
    
    public UsuarioHasProyecto(int usuario_id, int proyecto_id) {
    	this.proyecto_id = proyecto_id;
    	this.usuario_id = usuario_id;
    }
    
	public int getUsuario_id() {
		return usuario_id;
	}
	
	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}
	
	public int getProyecto_id() {
		return proyecto_id;
	}
	
	public void setProyecto_id(int proyecto_id) {
		this.proyecto_id = proyecto_id;
	}
    
	@Override
    public boolean equals(Object obj) {

        if(!(obj instanceof UsuarioHasProyecto)) return false;
        UsuarioHasProyecto relacion = (UsuarioHasProyecto) obj;

        if (this.usuario_id != relacion.getUsuario_id() || this.proyecto_id != relacion.getProyecto_id()) {
            return false;
        }
        return true;
    }
    
}
