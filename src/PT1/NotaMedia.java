package PT1;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;

public class NotaMedia {

	public static void main(String[] args) {
		// Declaramos teclado
		Scanner keyboard = new Scanner(System.in);
		
		// Declaramos diccionario de medias de alumnos
		Hashtable<String,Double> medias_alumnos = new Hashtable<String,Double>();
		
		//declaramos notas y alumnos
		double notas[][] = new double[3][3];
		String alumnos[] = new String[3];
		
		//Introducimos nombres y notas de alumnos
		for (int i = 0; i < alumnos.length; i++) {
			System.out.println("Introduce el nombre del alumno:");
			alumnos[i] = keyboard.nextLine();
			for (int j = 0; j < notas.length; j++) {
				System.out.println("Introduce la nota Nº" + (j+1) + " del alumno " + alumnos[i] + ":");
				String nota_string = keyboard.nextLine();
				//validamos error para que solo se pueda introducir un numero de 0 a 10
				while (!stringEsDoubleDe0a10(nota_string)) {
					System.out.println("Datos incorrectos, introduce una nota del 0 al 10:");
					nota_string = (String) keyboard.nextLine();
				}
				notas[i][j] = Double.parseDouble(nota_string);
			}
		}
		
		//calculamos medias y guardamos en Hashtable
		for (int i = 0; i < alumnos.length; i++) {
			double media = calcularMedia(notas[i]);
			guardarMediaAlumnos(medias_alumnos, media, alumnos[i]);
		}
		
		//mostramos hashtable
		verMediaAlumnos(medias_alumnos);

	}
	
	//mostramos todos los elementos del hashtable
	public static void verMediaAlumnos(Hashtable<String,Double> medias_alumnos) {
		Enumeration <String> key = medias_alumnos.keys();
		while (key.hasMoreElements()) {
			String nombre = key.nextElement();
			System.out.println("Nombre: " + nombre + "  |  Media: " + String.format("%.2f", medias_alumnos.get(nombre)) );
		}
	}
	
	//llenamos las listas con la media de alumnos
	public static void guardarMediaAlumnos(Hashtable<String,Double> medias_alumnos, double media, String alumno) {
		medias_alumnos.put(alumno, media);
	}
	
	//calcula la media de un array
	public static double calcularMedia(double notas[]) {
		double media = 0;
		for (int i = 0; i < notas.length; i++) {
			media += notas[i];
		}
		media = media / notas.length;
		return media;
	}
	
	//valida si string es numero decimal positivo igual o menor que 10
	public static boolean stringEsDoubleDe0a10(String datos_keyboard) {
		//variable para comprobar si es dato numérico o no
		boolean es_numero_positivo;
		
		//comprueba si el dato es positivo y numérico o no
		try {
			double numero = Double.parseDouble(datos_keyboard);
				es_numero_positivo = true;
			if (numero < 0 || numero > 10) {
				es_numero_positivo = false;
			}
		} catch (Exception e) {
			es_numero_positivo = false;
		}
		return es_numero_positivo;
	}

}
