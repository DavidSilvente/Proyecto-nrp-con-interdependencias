package mochilaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mochila.MochilaNRP;
import mochila.Requisito;
import mochila.RequisitoCombinado;
import model.Cliente;
class testCargarListaRequisitos {
	private Cliente cli1;
	private Cliente cli2;
	private Cliente cli3;
	
	private Requisito req1;
	private Requisito req2;
	private Requisito req3;
	private Requisito req4;
	private Requisito req5;

	@BeforeEach
	public void setUp() throws Exception {
		// Arrange
		// Creacion clientes
		cli1 = new Cliente("C1", 10);
		cli2 = new Cliente("C2", 8);
		cli3 = new Cliente("C3", 6);
		
		// Creacion requisitos

		TreeMap<Cliente, Integer> valR1 = new TreeMap<Cliente, Integer>();
		valR1.put(cli1, 4);
		valR1.put(cli2, 2);
		valR1.put(cli3, 6);
		req1 = new Requisito("R1", 1, valR1);

		TreeMap<Cliente, Integer> valR2 = new TreeMap<Cliente, Integer>();
		valR2.put(cli1, 2);
		valR2.put(cli2, 3);
		valR2.put(cli3, 4);
		req2 = new Requisito("R2", 2, valR2);
		
		TreeMap<Cliente, Integer> valR3 = new TreeMap<Cliente, Integer>();
		valR3.put(cli1, 1);
		valR3.put(cli2, 1);
		valR3.put(cli3, 1);
		req3 = new Requisito("R3", 3, valR3);
		
		TreeMap<Cliente, Integer> valR4 = new TreeMap<Cliente, Integer>();
		valR4.put(cli1, 3);
		valR4.put(cli2, 5);
		valR4.put(cli3, 1);
		req4 = new Requisito("R4", 4, valR4);
		
		TreeMap<Cliente, Integer> valR5 = new TreeMap<Cliente, Integer>();
		valR5.put(cli1, 10);
		valR5.put(cli2, 10);
		valR5.put(cli3, 10);
		req5 = new Requisito("R5", 5, valR5);
	}
	
	@Test
	void testListaVacia() {
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		
		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);

		// Act
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		assertEquals(0, mochila.getRequisitos().size());
	}

	@Test
	void testAniadirRequisitoNoCombinado() {
		// No hay relaciones

		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		requisitos.add(req1);
		
		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);

		// Act
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		assertEquals(1, mochila.getRequisitos().size());
		assertEquals(1, mochila.getRequisitos().get(0).esfuerzo);

	}
	
	@Test
	void testAniadirUnCombinado() {
		// Relaciones
		req2.aniadirRelacion(req1, "Combinacion");
		req1.aniadirRelacion(req2, "Combinacion");

		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		requisitos.add(req1);
		requisitos.add(req2);

		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);

		// Act
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		assertEquals(1, mochila.getRequisitos().size()); //Un req combinado?
		assertEquals(3, mochila.getRequisitos().get(0).esfuerzo);//El esfuerzo es la suma de los esfuerzos de la lista?
		RequisitoCombinado rc = (RequisitoCombinado) mochila.getRequisitos().get(0);
		assertEquals(2, rc.combinados.size());//El req combinado contiene 2 req?
	}
	
	@Test
	void testAniadirRequisitoNoCombinadoYCombinado() {
		// No hay relaciones
		req2.aniadirRelacion(req1, "Dependencia");
		req2.aniadirRelacion(req3, "Combinacion");
		req3.aniadirRelacion(req2, "Combinacion");
		
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		requisitos.add(req1);
		requisitos.add(req2);
		requisitos.add(req3);

		
		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);

		// Act
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		System.out.println("NoCombinado y combinado --> " + mochila.getRequisitos());
		assertEquals(2, mochila.getRequisitos().size()); //Un req combinado?
		assertEquals(5, mochila.getRequisitos().get(0).esfuerzo);//El esfuerzo es la suma de los esfuerzos de la lista?

	}
	
	@Test
	void testAniadirRequisitoCombinadoYNoCombinado() {
		// No hay relaciones
		req1.aniadirRelacion(req2, "Combinacion");
		req2.aniadirRelacion(req1, "Combinacion");
		req3.aniadirRelacion(req2, "Dependencia");
		
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		requisitos.add(req1);
		requisitos.add(req2);
		requisitos.add(req3);

		
		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);

		// Act
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		System.out.println("Combinado y no combinado --> " + mochila.getRequisitos());
		assertEquals(2, mochila.getRequisitos().size()); //Un req combinado?
		assertEquals(3, mochila.getRequisitos().get(0).esfuerzo);//El esfuerzo es la suma de los esfuerzos de la lista?

	}
	
	

	
	@Test
	void testAniadirCombinadoNoCombinadoCombinado() {
		// Relaciones
		req2.aniadirRelacion(req1, "Combinacion");
		req1.aniadirRelacion(req2, "Combinacion");
		req3.aniadirRelacion(req2, "Dependencia");
		req4.aniadirRelacion(req3, "Dependencia");
		req4.aniadirRelacion(req5, "Combinacion");
		req5.aniadirRelacion(req4, "Combinacion");


		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		requisitos.add(req1);
		requisitos.add(req2);
		requisitos.add(req3);
		requisitos.add(req4);
		requisitos.add(req5);


		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);

		// Act
		mochila.cargarListaRequisitos(requisitos);
		
		System.out.println(mochila.getRequisitos().toString());
		// Assert
		assertEquals(3, mochila.getRequisitos().size()); //Un req combinado?
		assertEquals(9, mochila.getRequisitos().get(0).esfuerzo);//El esfuerzo es la suma de los esfuerzos de la lista?
		assertEquals(3, mochila.getRequisitos().get(2).esfuerzo);//El esfuerzo es la suma de los esfuerzos de la lista?

		RequisitoCombinado rc = (RequisitoCombinado) mochila.getRequisitos().get(0);
		assertEquals(2, rc.combinados.size());//El req combinado contiene 2 req?
		
		RequisitoCombinado rc1 = (RequisitoCombinado) mochila.getRequisitos().get(1);
		assertEquals(2, rc1.combinados.size());//El req combinado contiene 2 req?


	}
	
	@Test
	void testAniadirNoCombinadoCombinado3NoCombinado() {
		// Relaciones
		req2.aniadirRelacion(req3, "Combinacion");
		req3.aniadirRelacion(req1, "Dependencia");
		req3.aniadirRelacion(req2, "Combinacion");
		req3.aniadirRelacion(req4, "Combinacion");
		req4.aniadirRelacion(req3, "Combinacion");
		req5.aniadirRelacion(req4, "Dependencia");


		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		requisitos.add(req1);
		requisitos.add(req2);
		requisitos.add(req3);
		requisitos.add(req4);
		requisitos.add(req5);


		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);

		// Act
		mochila.cargarListaRequisitos(requisitos);
		
		System.out.println(mochila.getRequisitos().toString());
		// Assert
		System.out.println("NC,RC, NC --->" + mochila.getRequisitos().toString());
		assertEquals(3, mochila.getRequisitos().size()); //Un req combinado?

		RequisitoCombinado rc = (RequisitoCombinado) mochila.getRequisitos().get(1);
		assertEquals(3, rc.combinados.size());//El req combinado contiene 2 req?
	}
}
