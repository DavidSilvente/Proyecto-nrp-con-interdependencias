package mochilaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mochila.MochilaNRP;
import mochila.Requisito;
import model.Cliente;

class testCumplimientoRelaciones {
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
	void testUnRequisito() {
		// Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		requisitos.add(req1);

		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		assertEquals(true, mochila.cumplimientoRelaciones(1, new int[] { 1 }));
		assertEquals(true, mochila.cumplimientoRelaciones(1, new int[] { 0 }));
	}

	@Test
	void testDosRequisitosDependientes() {
		// Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		req2.aniadirRelacion(req1, "Dependencia");
		requisitos.add(req1);
		requisitos.add(req2);

		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 1, 1 }));
		assertEquals(false, mochila.cumplimientoRelaciones(2, new int[] { 0, 1 }));
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 1, 1 }));

	}
	
	@Test
	void testDosRequisitosExcluyentes() {
		// Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		req1.aniadirRelacion(req2, "Exclusion");
		req2.aniadirRelacion(req1, "Exclusion");
		requisitos.add(req1);
		requisitos.add(req2);

		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		assertEquals(false, mochila.cumplimientoRelaciones(2, new int[] { 1, 1 }));
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 0, 1 }));
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 1, 0 }));
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 0, 0 }));
		assertEquals(false, mochila.cumplimientoRelaciones(2, new int[] { 1, 1 }));
	}
	
	@Test
	void testUnCombinado() {
		// Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		req1.aniadirRelacion(req2, "Combinacion");
		req2.aniadirRelacion(req1, "Combinacion");
		requisitos.add(req1);
		requisitos.add(req2);

		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		assertEquals(true, mochila.cumplimientoRelaciones(1, new int[] { 1}));
		assertEquals(true, mochila.cumplimientoRelaciones(1, new int[] { 0}));
	}
	
	@Test
	void testCombinadoDependienteNoCombinado() {
		// Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		req1.aniadirRelacion(req2, "Combinacion");
		req2.aniadirRelacion(req1, "Combinacion");
		req2.aniadirRelacion(req3, "Dependencia");
		requisitos.add(req1);
		requisitos.add(req2);
		requisitos.add(req3);

		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 0, 1}));
		assertEquals(false, mochila.cumplimientoRelaciones(2, new int[] { 1, 0}));
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 1, 1}));
	}
	
	@Test
	void testCombinadoExcluyenteNoCombinado() {
		// Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		req1.aniadirRelacion(req2, "Combinacion");
		req2.aniadirRelacion(req1, "Combinacion");
		req2.aniadirRelacion(req3, "Exclusion");
		req3.aniadirRelacion(req2, "Exclusion");
		requisitos.add(req1);
		requisitos.add(req2);
		requisitos.add(req3);

		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 0, 1}));
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 1, 0}));
		assertEquals(false, mochila.cumplimientoRelaciones(2, new int[] { 1, 1}));
	}
	
	@Test
	void testCombinadoDependienteCombinado() {
		// Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		req1.aniadirRelacion(req2, "Combinacion");
		req2.aniadirRelacion(req1, "Combinacion");
		req3.aniadirRelacion(req4, "Combinacion");
		req4.aniadirRelacion(req3, "Combinacion");
		req3.aniadirRelacion(req2, "Dependencia");
		req4.aniadirRelacion(req1, "Dependencia");
		requisitos.add(req1);
		requisitos.add(req2);
		requisitos.add(req3);
		requisitos.add(req4);

		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		assertEquals(false, mochila.cumplimientoRelaciones(2, new int[] { 0, 1}));
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 1, 0}));
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 1, 1}));
	}
	
	@Test
	void testCombinadoExcluyenteCombinado() {
		// Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		req1.aniadirRelacion(req2, "Combinacion");
		req2.aniadirRelacion(req1, "Combinacion");
		req3.aniadirRelacion(req4, "Combinacion");
		req4.aniadirRelacion(req3, "Combinacion");
		req3.aniadirRelacion(req2, "Exclusion");
		req4.aniadirRelacion(req1, "Exclusion");
		requisitos.add(req1);
		requisitos.add(req2);
		requisitos.add(req3);
		requisitos.add(req4);

		// Creacion Mochila
		MochilaNRP mochila = new MochilaNRP(7);
		mochila.cargarListaRequisitos(requisitos);

		// Assert
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 0, 1}));
		assertEquals(true, mochila.cumplimientoRelaciones(2, new int[] { 1, 0}));
		assertEquals(false, mochila.cumplimientoRelaciones(2, new int[] { 1, 1}));
	}
}
