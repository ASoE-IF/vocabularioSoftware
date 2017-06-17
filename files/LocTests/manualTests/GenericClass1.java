package com.xml2xsd;

import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

public class GenericClass {

	public static final int PUBLIC_CONSTANTE = 1;
	private static final int PRIVATE_CONSTANTE = 2;
	
	public float public_atributo; 
	private float private_atributo; 
	

	public enum GenericEnum {

		RED, GREEN, BLUE;

	}
	
	public float public_metodo(String parametro1, boolean parametro2) {
		int local_variavel;
		return local_variavel = 2;
	}
	
	private float private_metodo(String parametro1, boolean parametro2) {
		return 0;	
	}

	
	public class Interna {
		public float public_atributo_interna; 
		private float private_atributo__interna; 

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
