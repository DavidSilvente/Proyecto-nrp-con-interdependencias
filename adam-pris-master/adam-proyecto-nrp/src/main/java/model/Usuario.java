package model;

public class Usuario implements Comparable<Usuario>{
    private int id;
    private String login;
    private String password;
    private Boolean admin;

    public Usuario(int id, String login, String password, Boolean admin) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.admin = admin;
    }

    public Usuario(String login, String password, Boolean admin) {
        this.login = login;
        this.password = password;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Usuario)) return false;
        Usuario usuario = (Usuario) obj;

        if (!this.login.equals(usuario.getLogin()) || !this.password.equals(usuario.getPassword())) {
            return false;
        }
        return true;
    }
    
	@Override
	public int compareTo(Usuario o) {
	    if(this.getId() > o.getId())
	        return 1;
	    else if(this.getId() < o.getId())
	        return -1;
	    return this.getLogin().compareTo(o.getLogin());
	}
}
