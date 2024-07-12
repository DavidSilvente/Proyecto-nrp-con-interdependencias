package mochila;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;
import model.Cliente;

public class RequisitoCombinado extends Requisito {
	public ArrayList<Requisito> combinados = new ArrayList<Requisito>();

	public RequisitoCombinado(String nombre, int esfuerzo, TreeMap<Cliente, Integer> clientesValoracion) {
		super(nombre, esfuerzo, clientesValoracion);
	}

	public RequisitoCombinado(String nombre) {
		this.isCombinado=true;
		this.nombre = nombre;
		this.esfuerzo = 0;
		this.satisfaccion = 0;
		this.requisitoRelacion= new TreeMap<Requisito, String>();
	}

	public void aniadirCombinados(Requisito req) {
		req.padre = this;
		if(!this.combinados.contains(req)) {
			this.combinados.add(req);
			
			this.esfuerzo += req.esfuerzo;
			this.satisfaccion += req.satisfaccion;
			this.nombre += " " + req.getNombre();

			for (Entry<Requisito, String> reqRelacion : req.requisitoRelacion.entrySet()) {
				if (reqRelacion.getValue() == "Combinacion" && !this.combinados.contains(reqRelacion.getKey()))
					this.aniadirCombinados(reqRelacion.getKey());
			}
			this.requisitoRelacion.putAll(req.requisitoRelacion); // OJO ANIADE LA RELACION DE COMBINACION
		}
	}

	public ArrayList<Requisito> getCombinados() {
		return combinados;
	}

	public void setCombinados(ArrayList<Requisito> combinados) {
		this.combinados = combinados;
	}

}
