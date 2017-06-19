package com.xml2xsd;

import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

/**
 * Javadoc da classe Generica
 * @author katyusco
 */
public class GenericClass {

	/* Constantes */

	public static final int PUBLIC_CONSTANTE = 1;
	private static final int PRIVATE_CONSTANTE = 2;
	
	/* Atributos */
	public float public_atributo; /* Comentário de public_atributo */
	private float private_atributo; /* Comentário de public_atributo */
	

	public enum GenericEnum {

		RED, GREEN, BLUE;

		/* Um comentario de bloco dentro do enum */
	}
	
	/* Métodos */
	
	/**
	 * Javadoc do método public_metodo
	 * @param parametro1
	 * @param parametro2
	 * @return
	 */
	public float public_metodo(String parametro1, boolean parametro2) {
		/* Variavel local */
		int local_variavel;
		return local_variavel = 2;
	}
	
	private float private_metodo(String parametro1, boolean parametro2) {
		return 0;	
	}

	
	public class Interna {
		public float public_atributo_interna; /* Comentário de public_atributo_interna */
		private float private_atributo__interna; /* Comentário de public_atributo_interna */

		public Interna() {
			System.out.println("nada");
		}

		private void coisa() {
			System.out.println("Generic Class");
		}
		
	}

	private class Interna2 extends Interna {}
}

public enum SideEnum {

	ESSA, ENUMERACAO, NUM, FAZ, NADA;

	public void foo(String str){
	}
}
