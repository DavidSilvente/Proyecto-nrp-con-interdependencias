package mochila;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.TreeMap;

import controladores.ServletProyecto;
import dao.*;
import model.*;
import model.RequisitoHasRequisito.tipo;
import mochila.*;

public class MochilaNRP {
	private int esfuerzoMax;
	public ArrayList<Requisito> listadoResult;
	private ArrayList<Requisito> requisitos;
	private ArrayList<Requisito> requisitosInciales = new ArrayList<Requisito>(); // Lista inicial de requisitos
	private int[] resultadoMochila; // Lista indicaci�n de requisitos a escoger (RESULTADO METODO MOCHILA)

	public MochilaNRP(int esfuerzoMax) {
		listadoResult = new ArrayList<Requisito>();
		requisitos = new ArrayList<Requisito>();
		this.esfuerzoMax = esfuerzoMax;
	}

	/**
	 * Devuelve el listado resultado en forma de cadena una vez introducidos los
	 * requisitos obtenidos en el resultado de la mochila.
	 * 
	 * @return String correspondiente al listado resultados
	 */
	public String obtenerRequisitosAIntroducir() {
		this.introducirRequisitos();
		Collections.sort(listadoResult, (r1, r2) -> r1.compareTo(r2));
		return "Los requisitos escogidos para el sprint son : " + listadoResult.toString();
	}

	public String solucionManual(ArrayList<String> requisitosManual) throws SQLException {
		this.cargarListaRequisitos(crearArrayRequisitos()); // obtenemos todos los requisitos de la BD
		int solucionValida = this.introducirRequisitosManual(requisitosManual);
		if (solucionValida == -1) { // pasamos el array y se lo introducimos a un metodo mochila manual?
			return "No se cumplen las relaciones de combinacion entre requisitos de la solucion, introduzca una solucion valida por favor.";
		} else if (solucionValida == 0) {
			return "Se ha excedido el peso del equipo o se incumplen relaciones de implicacion/exclusion entre requisitos, introduzca una solucion valida por favor";
		} else {
			this.tratarRequisitosResultado();

			Collections.sort(listadoResult, (r1, r2) -> r2.compareTo(r1));

			if (listadoResult.isEmpty()) {
				return "No se ha introducido ningun requisito en la solucion";
			} else {
				String resultado = "";
				resultado += "\n<h3>Los requisitos introducidos son:" + listadoResult.toString() + "</h3><br/>"
						+ "<h4>Las métricas del software correspondientes a la solucion son: </h4><br/>"
						+ "<b>Productividad de la solución : " + calculoProductividad() + "</b><br/>"
						+ "<b>Contribución de la solución: " + calculoContribucion() + "</b><br/>"
						+ "<b>Cobertura de la solución: " + calculoCobertura() + "</b><br/>"
						+"<b>Esfuerzo del sprint: " + calculoEsfuerzo() + " / "+esfuerzoMax+"</b><br/>";
				for(Requisito r : listadoResult) {
					resultado += "<b>Satisfacción del requisito " + r.getNombre() + ": " + r.getSatisfaccion() + "</b><br/>";
				}
				return resultado;
			}
		}
	}

	public String solucionAutomaticaIndividual() throws SQLException {
		this.cargarListaRequisitos(crearArrayRequisitos());
		this.introducirRequisitos();

		// this.tratarRequisitosResultado();

		Collections.sort(listadoResult, (r1, r2) -> r1.compareTo(r2));

		if (listadoResult.isEmpty()) {
			return "No se puede llevar a cabo ningún requisito en este sprint";
		} else {
			return "Los requisitos escogidos para el sprint son : " + listadoResult.toString()
					+ "\n Las métricas del software correspondientes al sprint son : \n"
					+ "Productividad de la solución : \n" + calculoProductividad() + "Contribución de la solución : \n"
					+ calculoContribucion() + "Cobertura de la solución : \n" + calculoCobertura();
		}

	}

	public String solucionAutomatica() throws SQLException {
		ArrayList<Requisito> requisitosBorrar = new ArrayList<Requisito>();
		this.cargarListaRequisitos(crearArrayRequisitos());

		int sprint = 1;
		String resultado = "";

		if (requisitos == null) {
			return "\n Se han encontrado relaciones incompatibles (Combinacion y exlcusion simultaneas). Defina bien las relaciones e intentelo de nuevo.";
		}

		while (!requisitos.isEmpty()) {
			this.introducirRequisitos();
			requisitosBorrar.addAll(listadoResult);

			// Obtencion cadena resultado
			this.tratarRequisitosResultado();
			Collections.sort(listadoResult, (r1, r2) -> r2.compareTo(r1));
			if (listadoResult.isEmpty()) {
				resultado += "\n No se puede llevar a cabo ningún requisito en el sprint " + sprint
						+ ", los requisitos restantes son " + requisitos.toString();
				break;
			} else {
				resultado += "\n<h3><br/>Sprint " + sprint + ": " + listadoResult.toString() + "</h3><br/>"
						+ "<h4>Las métricas del software correspondientes al sprint son: </h4><br/>"
						+ "<b>Productividad de la solución : " + calculoProductividad() + "</b><br/>"
						+ "<b>Contribución de la solución: " + calculoContribucion() + "</b><br/>"
						+ "<b>Cobertura de la solución: " + calculoCobertura() + "</b><br/>"
						+ "<b>Esfuerzo del sprint: " + calculoEsfuerzo() + " / " + esfuerzoMax + "</b><br/>";
				for(Requisito r : listadoResult) {
					resultado += "<b>Satisfacción del requisito " + r.getNombre() + ": " + r.getSatisfaccion() + "</b><br/>";
				}
			}

			requisitos.removeAll(requisitosBorrar);
			listadoResult.clear();
			requisitosBorrar.clear();
			sprint++;
		}
		return resultado;
	}

	private int calculoEsfuerzo() {
		int esfuerzo = 0;
		for(Requisito r : listadoResult) {
			esfuerzo += r.esfuerzo;
		}
		return esfuerzo;
	}

	/**
	 * Método tratarRequisitosResultado, trata los requisitos para que tengan un
	 * formato correcto al obtener las métricas del software, divide los requisitos
	 * combinados en requisitos normales una vez obtenida la solucion
	 */
	public void tratarRequisitosResultado() {
		System.out.println("Listado result antes de tratar bb: " + listadoResult.toString());
		for (int i = 0; i < listadoResult.size(); i++) {
			if (listadoResult.get(i) instanceof RequisitoCombinado) {
				RequisitoCombinado rc = (RequisitoCombinado) listadoResult.get(i);
				System.out.println("Req del combinado: " + rc.combinados.toString());
				for (Requisito reqComb : rc.combinados) {
					listadoResult.add(reqComb);
				}
				listadoResult.remove(i);
			}
		}
		System.out.println("Listado result despues de tratar: " + listadoResult.toString());

	}

	private String calculoCobertura() {
		String cobClientes = "";
		double valSol = 0;
		double valTot = 0;
		for (Cliente c : listadoResult.get(0).clientesValoracion.navigableKeySet()) {
			for (Requisito r : requisitosInciales) {
				valTot += r.clientesValoracion.get(c);

				if (listadoResult.contains(r)) {
					valSol += r.clientesValoracion.get(c);
				}
			}
			cobClientes += c.getNombre() + ": " + String.format("%.2f", valSol / valTot) + "\n";
			valSol = 0;
			valTot = 0;
		}
		return cobClientes;
	}

	private String calculoContribucion() {
		String contrClientes = "";
		double contr = 0;
		for (Cliente c : listadoResult.get(0).clientesValoracion.navigableKeySet()) {
			for (Requisito r : listadoResult) {
				contr += r.calcContrib(c);
			}
			contrClientes += c.getNombre() + ": " + String.format("%.2f", contr) + "\n";
			contr = 0;
		}
		return contrClientes;
	}

	private String calculoProductividad() {
		double prod = 0;
		for (Requisito r : listadoResult) {
			prod += r.calcProduct();
		}
		return String.format("%.2f", prod) + "\n";
	}

	private int introducirRequisitosManual(ArrayList<String> requisitosManual) {
		System.out.println(requisitosManual.toString());
		int s[] = new int[requisitos.size() + 1];
		double esfuerzo = 0.0;

		for (int i = 0; i < this.requisitos.size(); i++) {
			Requisito req = requisitos.get(i);
			if (req instanceof RequisitoCombinado) {
				RequisitoCombinado reqComb = (RequisitoCombinado) req;
				int rcCompleto = reqComb.combinados.size();
				for (Requisito r : reqComb.combinados) {
					if (requisitosManual.contains(r.nombre)) {
						rcCompleto--;
						esfuerzo += r.esfuerzo;
					}
				}
				if (rcCompleto == 0) {
					s[i] = 1;
				}else if(rcCompleto < reqComb.combinados.size()) {
					listadoResult = null;
					return -1; // Faltan requisitos combinados
				}else {
					s[i] = 0;
				}
			} else if (requisitosManual.contains(req.nombre)) {
				s[i] = 1;
				esfuerzo += requisitos.get(i).esfuerzo;
			} else
				s[i] = 0;
		}
		s[requisitos.size()] = 0;

		if (!solucion(requisitos.size(), s, esfuerzo)) {
			return 0; // Se incumple algun criterio para considerarlo solucion
		}

		for (int i = 0; i < s.length; i++) { // recorremos la solucion manual
			if (s[i] == 1) { // anadimos los indicados al listado resultado
				this.listadoResult.add(this.requisitos.get(i));
			}
		}
		System.out.println(esfuerzo);
		System.out.println(listadoResult.toString());
		return 1;
	}

	/**
	 * Metodo introducirRequisitos, introduce los requisitos en el listado resultado
	 * teniendo en cuenta el array de enteros resultadoMochila.
	 */
	private void introducirRequisitos() {

		int[] requisitosEscogidos = this.mochilaBT();

		for (int i = 0; i < requisitosEscogidos.length; i++) { // recorremos la solucion optima indicada por el metodo
																// mochila
			if (requisitosEscogidos[i] == 1) { // anadimos los indicados al listado resultado
				this.listadoResult.add(this.requisitos.get(i));
			}
		}
	}

	/**
	 * Metodo mochilaBT, usa backtracking para obtener la solucion optima a nuestro
	 * problema mochila 0/1
	 * 
	 * @return array int[], teniendo un 1 si el objeto de la posicion
	 *         correspondiente es escogido y un 0 si no lo es
	 */
	public int[] mochilaBT() {
		int nivel = 0; // empezamos nivel en 0 para poder realizar accesos correctos a los arrays
		int s[] = new int[requisitos.size() + 1]; // espacio de soluciones
		int soa[] = Arrays.copyOfRange(s, 0, s.length); // espacio de soluciones optimas actuales
		double voa = -10000000000000000.0; // valor optimo actual
		double bact = 0; // beneficio actual
		double pact = 0; // peso actual

		// inicializamos el espacio de soluciones a -1
		for (int i = 0; i < s.length; i++) {
			s[i] = -1;
		}

		while (nivel > -1) { // mientras que no hayamos recorrido el arbol entero

			// **********************************************************************************************************
			// LLAMADA A GENERAR
			s[nivel] = s[nivel] + 1;
			if (nivel < this.requisitos.size() && s[nivel] == 1) { // actualizamos el estado del nodo (si se escoge o
																	// no,si esta explorado o no(-1)...)
				pact = pact + this.requisitos.get(nivel).getEsfuerzo(); // actualizamos el peso y el beneficio actuales
																		// segun
																		// si el nodo
				bact = bact + this.requisitos.get(nivel).getSatisfaccion(); // se escoge o no (con el valor de s[nivel])
			}
			// **********************************************************************************************************

			if (solucion(nivel, s, pact) && (bact > voa)) { // si encontramos una solucion mejor actualizamos las sol
															// optimas actuales
				voa = bact; // FUNCION A OPTIMIZAR
				soa = Arrays.copyOfRange(s, 0, s.length);
			} else if (criterio(nivel, pact, bact, voa, s)) // sigue siendo una solucion (cumple restricciones)
				nivel++;

			else {
				while ((!masHermanos(nivel, s)) && (nivel > -1)) {// mientras no queden nodos por explorar en la rama
					// LLAMADA A RETROCEDER
					if (nivel != this.requisitos.size()) {
						pact = pact - this.requisitos.get(nivel).getEsfuerzo() * s[nivel]; // reestablecemos el peso y
																							// el
																							// valor del nivel anterior
						bact = bact - this.requisitos.get(nivel).getSatisfaccion() * s[nivel];
					}
					s[nivel] = -1; // cambiamos el estado del nodo
					nivel -= 1; // retrocedemos un nivel
				}
			}
		}
		this.resultadoMochila = Arrays.copyOfRange(soa, 0, soa.length);
		return soa; // devolvemos la solucion optima
	}

	/**
	 * Metodo masHermanos, indica si el nodo actual es un puente a mas nodos a
	 * explorar
	 * 
	 * @param nivel Nivel actual del arbol debusqueda
	 * @param s     conjunto solucion actual
	 * @return boolean true si tiene mas hermanos
	 */
	private boolean masHermanos(int nivel, int[] s) {
		if (nivel == -1)
			return false;
		return s[nivel] < 1; // comprobamos que se tengan nodos por explorar en la misma rama
	}

	/**
	 * Metodo criterio, indicara si la solucion que esta siendo llevada a cabo
	 * cumple las restricciones impuestas o por el contrario no merece la pena
	 * seguir explorandola
	 * 
	 * @param nivel nivel actual del arbol de busqueda
	 * @param pact  peso actual de la mochila
	 * @param s
	 * @return bollean true si cumple las restricciones para ser una solucion
	 */
	private boolean criterio(int nivel, double pact, double bact, double voa, int[] s) {
		if ((nivel < this.requisitos.size()) /* Si aun quedan requisitos por explorar */
				&& (pact <= this.esfuerzoMax) /* Si no sobrepasamos el peso */
		/* && bact >= voa */) { /* Si la solucion es mejor que la anterior */
			return true; /* Evaluar que se cumplen requisitos de dependencias y exclusi�n */
		}
		return false;
	}

	/**
	 * 
	 * @param nivel
	 * @param s
	 * @return
	 */
	public boolean cumplimientoRelaciones(int nivel, int[] s) {
		Requisito req = null;
		int indiceRel = -1;
		for (int i = 0; i < nivel; i++) {
			if (s[i] == 1) { // Si el requisito se encuentra en la solucion exploramos sus dependencias
				req = this.requisitos.get(i);
				if (req.requisitoRelacion == null)
					continue;// Si no tiene relaciones continuamos iterando
				for (Entry<Requisito, String> reqRelacion : req.requisitoRelacion.entrySet()) {// Si tiene relaciones
																								// comprobamos que se
																								// cumplan
					// Comprobación si req ya fueron aniadidos*/
					if (!requisitos.contains(reqRelacion.getKey()) && !requisitos.contains(reqRelacion.getKey().padre))
						continue;
					indiceRel = reqRelacion.getKey().isCombinado == true
							? this.requisitos.indexOf(reqRelacion.getKey().padre)
							: this.requisitos.indexOf(reqRelacion.getKey());
					if (reqRelacion.getValue() == "Dependencia") { // RELACIONES DE DEPENDENCIA
						if (s[indiceRel] != 1) {// El requisito del que depende o su
							return false; // padre en caso de ser combinado deben de estar en la sol
						}
					} else if (reqRelacion.getValue() == "Exclusion") {// RELACIONES DE EXCLUSION
						if (s[indiceRel] == 1 && indiceRel <= nivel) {// El requisito que excluye o su
							return false; // padre en caso de ser combinado NO deben de estar en la sol
						}
					} else // RELACIONES DE COMBINACION YA ESTAN COMPROBADAS
						continue;
				}
			}
		}
		return true;
	}

	/**
	 * Metodo solucion, indica si un conjunto solucion actual puede ser una solucion
	 * valida
	 * 
	 * @param nivel nivel actual
	 * @param s     conjunto de soluciones actuales
	 * @param pact  peso actual
	 * @return boolean true si es una solucion valida
	 */
	private boolean solucion(int nivel, int[] s, double pact) {
		if ((nivel == requisitos.size()) && (pact <= this.esfuerzoMax))
			return cumplimientoRelaciones(nivel, s);// comprobamos si cumple los requisitos para
		// ser una solucion
		return false;

	}

	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public void cargarListaRequisitos(ArrayList<Requisito> listaAniadir) {
		this.requisitosInciales.addAll(listaAniadir);
		this.requisitos.clear();
		for (int i = 0; i < listaAniadir.size(); i++) {
			if (listaAniadir.get(i).requisitoRelacion == null
					|| !listaAniadir.get(i).requisitoRelacion.containsValue("Combinacion")) {// Si no es combinado se
																								// aniade directamente
				this.requisitos.add(listaAniadir.get(i));
			}

			else { // Si es combinado creamos un RC, aniadimos todos los adyacentes
				RequisitoCombinado rc = new RequisitoCombinado("RC"); // y borramos de la lista de requisitos los ya
																		// aniadidos al RC
				rc.aniadirCombinados(listaAniadir.get(i));
				for (Requisito reqCombinados : rc.combinados) {
					if (rc.requisitoRelacion.get(reqCombinados) == "Exclusion") {
						requisitos = null;
						return;
					}
					listaAniadir.remove(reqCombinados);
				}
				this.requisitos.add(rc); // Aniadimos el rc
				i--;
			}
		}
		Collections.sort(this.requisitos);
		Collections.reverse(this.requisitos);
	}

	public ArrayList<mochila.Requisito> crearArrayRequisitos() throws SQLException {

		ArrayList<mochila.Requisito> requisitosAdaptados = new ArrayList<mochila.Requisito>();

		TreeMap<Cliente, Integer> cliValor = new TreeMap<Cliente, Integer>();

		for (model.Requisito r : RequisitoDAO.listar()) {
			cliValor.clear();
			for (Cliente c : ClienteDAO.listar()) {
				ClienteHasRequisito chr = ClienteHasRequisitoDAO.obtenerPorID(c.getId(), r.getId());
				if (chr == null)
					cliValor.put(c, 0);
				else
					cliValor.put(c, chr.getValor());
			}

			requisitosAdaptados.add(new Requisito(r.getNombre(), r.getEsfuerzo(), cliValor));
		}

		for (Requisito rAniadir : requisitosAdaptados) {
			int idActual = RequisitoDAO.obtenerPorNombre(rAniadir.nombre).getId();
			for (Requisito r : requisitosAdaptados) {
				if (rAniadir.equals(r))
					continue;
				RequisitoHasRequisito rrel = RequisitoHasRequisitoDAO.obtenerPorID(idActual,
						RequisitoDAO.obtenerPorNombre(r.nombre).getId());

				if (rrel != null) {
					if (rrel.getTipo() == tipo.combinacion) {
						rAniadir.aniadirRelacion(r, "Combinacion");
						r.aniadirRelacion(rAniadir, "Combinacion");
					} else if (rrel.getTipo() == tipo.exclusion) {
						rAniadir.aniadirRelacion(r, "Exclusion");
						r.aniadirRelacion(rAniadir, "Exclusion");
					} else {
						if (rrel.getRequisito_id() == idActual)
							rAniadir.aniadirRelacion(r, "Dependencia");
					}
				}
			}
		}

		return requisitosAdaptados;
	}

	public ArrayList<Requisito> getRequisitos() {
		return this.requisitos;
	}

}
