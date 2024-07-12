package mochilaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Cliente;
import mochila.MochilaNRP;
import mochila.Requisito;

class testMochila {
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
	void testNingunRequisito() {
		//Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		MochilaNRP mochila = new MochilaNRP(5);
		mochila.cargarListaRequisitos(requisitos);
		
		assertEquals("Los requisitos escogidos para el sprint son : []",mochila.obtenerRequisitosAIntroducir());
		
	}
	
	@Test
	void testRequisitoEsfValido() {
		//Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		requisitos.add(req5);
		MochilaNRP mochila = new MochilaNRP(5);
		mochila.cargarListaRequisitos(requisitos);
		
		assertEquals("Los requisitos escogidos para el sprint son : [R5]",mochila.obtenerRequisitosAIntroducir());
		
	}
	
	@Test
	void testRequisitoEsfNoValido() {
		//Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		requisitos.add(req5);
		MochilaNRP mochila = new MochilaNRP(4);
		mochila.cargarListaRequisitos(requisitos);
		
		assertEquals("Los requisitos escogidos para el sprint son : []",mochila.obtenerRequisitosAIntroducir());
	}
	
	@Test
	void testRequisitosNoDependientesValido() {
		//Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		requisitos.add(req2);
		requisitos.add(req5);
		MochilaNRP mochila = new MochilaNRP(8);
		mochila.cargarListaRequisitos(requisitos);
		
		assertEquals("Los requisitos escogidos para el sprint son : [R2, R5]",mochila.obtenerRequisitosAIntroducir());
	}
	
	@Test
	void testRequisitosDependientesValido() {
		//Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		req2.aniadirRelacion(req5, "Dependencia");
		req2.setSatisfaccion(30);
		req5.setSatisfaccion(31);
		requisitos.add(req2);
		requisitos.add(req5);
		MochilaNRP mochila = new MochilaNRP(2);
		mochila.cargarListaRequisitos(requisitos);
		
		assertEquals("Los requisitos escogidos para el sprint son : []",mochila.obtenerRequisitosAIntroducir());
	}
	
	@Test
	void testRequisitosExcluyentes() {
		//Arrange
		ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
		req1.setEsfuerzo(20);
		req2.setEsfuerzo(4);
		req1.aniadirRelacion(req2, "Exclusion");
		req2.aniadirRelacion(req1, "Exclusion");

		requisitos.add(req1);
		requisitos.add(req2);
		MochilaNRP mochila = new MochilaNRP(25);
		mochila.cargarListaRequisitos(requisitos);
		
		assertEquals("Los requisitos escogidos para el sprint son : [R1]",mochila.obtenerRequisitosAIntroducir());
	}
	
	@Test
	void test3req() {
        //Arrange
        ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
        requisitos.add(req1);
        requisitos.add(req2);
        requisitos.add(req3);
        MochilaNRP mochila = new MochilaNRP(25);
        mochila.cargarListaRequisitos(requisitos);

        assertEquals("Los requisitos escogidos para el sprint son : [R1, R2, R3]",mochila.obtenerRequisitosAIntroducir());
    }
	
	@Test
	void test4reqConCombinado() {
        //Arrange
        ArrayList<Requisito> requisitos = new ArrayList<Requisito>();
        req1.aniadirRelacion(req2, "Combinacion");
        req2.aniadirRelacion(req1, "Combinacion");
        req3.aniadirRelacion(req2, "Dependencia");
        req4.aniadirRelacion(req1, "Exclusion");
        req1.aniadirRelacion(req4, "Exclusion");
        req4.setEsfuerzo(1);
        requisitos.add(req1);
        requisitos.add(req2);
        requisitos.add(req3);
        requisitos.add(req4);
        MochilaNRP mochila = new MochilaNRP(4);
        mochila.cargarListaRequisitos(requisitos);
        mochila.obtenerRequisitosAIntroducir();
        mochila.tratarRequisitosResultado();
        assertEquals("Los requisitos escogidos para el sprint son : [R1, R2, R3, R4]",mochila.listadoResult.toString());
    }

}
