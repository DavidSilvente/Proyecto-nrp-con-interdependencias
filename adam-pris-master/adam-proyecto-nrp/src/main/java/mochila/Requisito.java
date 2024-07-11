package mochila;

import java.util.Map.Entry;
import java.util.TreeMap;
import model.Cliente;

public class Requisito implements Comparable<Object> {
	protected String nombre;
	public int esfuerzo;
	protected double satisfaccion;
	protected Boolean isCombinado = false;
	protected Requisito padre = null;
	protected TreeMap<Cliente, Integer> clientesValoracion = null;
	protected TreeMap<Requisito, String> requisitoRelacion =  null;
	
	
	public Requisito() {
		
	}
	
	public Requisito(String nombre, int esfuerzo, TreeMap<Cliente, Integer> clientesValoracion) {
		this.nombre=nombre;
		this.esfuerzo=esfuerzo;
		this.clientesValoracion=clientesValoracion;
		
		this.satisfaccion = 0;
		for(Entry<Cliente, Integer> cliValor : clientesValoracion.entrySet()) {
			satisfaccion+= cliValor.getKey().getPrioridad() *cliValor.getValue();
		}
	}

	
	public double calcProduct() {
		return satisfaccion/esfuerzo;
	}
	
	public double calcContrib(Cliente cli) {
		if(this.clientesValoracion == null)
			return 0;
		return cli.getPrioridad() * this.clientesValoracion.get(cli)/satisfaccion;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getEsfuerzo() {
		return esfuerzo;
	}


	public void setEsfuerzo(int esfuerzo) {
		this.esfuerzo = esfuerzo;
	}


	public double getSatisfaccion() {
		return satisfaccion;
	}


	public void setSatisfaccion(double satisfaccion) {
		this.satisfaccion = satisfaccion;
	}


	public TreeMap<Cliente, Integer> getClientesValoracion() {
		return clientesValoracion;
	}


	public void setClientesValoracion(TreeMap<Cliente, Integer> clientesValoracion) {
		this.clientesValoracion = clientesValoracion;
	}

	public TreeMap<Requisito, String> getRequisitoRelacion() {
		return requisitoRelacion;
	}

	public void setRequisitoRelacion(TreeMap<Requisito, String> requisitoRelacion) {
		this.requisitoRelacion = requisitoRelacion;
	}
	
	public void aniadirRelacion(Requisito requisito, String relacion) {
		if(this.requisitoRelacion==null)
			this.requisitoRelacion = new TreeMap<Requisito, String>();
		if(relacion == "Combinacion")
			this.setIsCombinado(true);

		this.requisitoRelacion.put(requisito, relacion);
	}

	public Boolean getIsCombinado() {
		return isCombinado;
	}

	public void setIsCombinado(Boolean isCombinado) {
		this.isCombinado = isCombinado;
	}

	@Override
	public int compareTo(Object o) {
		Requisito req = (Requisito)o;
		
		if(req.satisfaccion>this.satisfaccion)
			return -1;
		else if(req.satisfaccion<this.satisfaccion)
			return 1;
		else
			return this.getNombre().compareTo(req.getNombre());
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;

		Requisito req = (Requisito)o;
		return req.nombre == this.nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
}
