package PT3;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JOptionPane;

public class StockProductos {

	public static void main(String[] args) {
		// Declaramos diccionario de stock de productos y declaramos uno auxiliar para asignarle un numero a cada nombre
		Hashtable<String,Integer> stock = new Hashtable<String,Integer>();
		Hashtable<Integer,String> id_producto = new Hashtable<Integer,String>();
		
		//llenamos los diccionarios con datos
		llenarDatos(id_producto, stock);
		
		//MENU
		String opt = "";
		while (!stringEsNumeroNatural(opt)) {
			//mostramos opciones del menu
			opt = JOptionPane.showInputDialog("MENÚ DE STOCK DEPRODUCTOS:\n"
					+ "[1] Añadir producto y su stock\n"
					+ "[2] Consultar stock de producto\n"
					+ "[3] Listar información por terminal\n"
					+ "[0] Salir del menú");
			
			//realizamos accion seleccionada por el usuario
			switch (opt) {
			//Añadir producto y su stock
			case "1":
				createProduct(id_producto, stock);
				opt = "";
				break;
			//Consultar stock de producto
			case "2":
				viewStockProduct(id_producto, stock);
				opt = "";
				break;
			//Listar información por terminal
			case "3":
				listProducts(id_producto, stock);
				opt = "";
				break;
			//Salir
			case "0":
				JOptionPane.showMessageDialog(null, "Adios!");
				break;
			default:
				JOptionPane.showMessageDialog(null, "Dato no reconocido, introduce una de las opciones existentes. (ej. 1)");
				break;
			}
		}
		
	}
	
	public static void listProducts(Hashtable<Integer,String> id_producto, Hashtable<String,Integer> stock) {
		String str_content = "LISTA DE PRODUCTOS Y STOCK:";
		for (int i = 0; i < id_producto.size(); i++) {
			str_content += "\n" + (i+1) + " - " + id_producto.get(i) + "   -> Stock:" + stock.get(id_producto.get(i));
			System.out.println((i+1) + " - " + id_producto.get(i) + "   -> Stock:" + stock.get(id_producto.get(i))); //muestra por terminal
		}
		JOptionPane.showMessageDialog(null, str_content);
	}
	
	public static void viewStockProduct(Hashtable<Integer,String> id_producto, Hashtable<String,Integer> stock) {
		//Mostramos diccionario y pedimos que se seleccione el numero de producto a conocer su stock
		String str_product_id = JOptionPane.showInputDialog("Introduce el número de producto para ver su stock:" + stringOfIntHashtableContent(id_producto));
		while (!stringEsNumeroNatural(str_product_id)) {
			str_product_id = JOptionPane.showInputDialog("Datos no numéricos naturales, introduce el número del producto a ver su stock:" + stringOfIntHashtableContent(id_producto));
		}
		int product_id = Integer.parseInt(str_product_id);
		while (product_id >= id_producto.size()) {
			str_product_id = JOptionPane.showInputDialog("Opción inexistente, introduce el número del producto a ver su stock:" + stringOfIntHashtableContent(id_producto));
			while (!stringEsNumeroNatural(str_product_id)) {
				str_product_id = JOptionPane.showInputDialog("Datos no numéricos naturales, introduce el número del producto a ver su stock:" + stringOfIntHashtableContent(id_producto));
			}
			product_id = Integer.parseInt(str_product_id);
		}
		//recogemos nombre correspondiente a la id introducida por el usuario y mostramos cantidad de stock
		String product_name = id_producto.get(product_id);
		int product_stock = stock.get(product_name);
		JOptionPane.showMessageDialog(null, "El producto \"" + product_name + "\" tiene un total de " + product_stock + " unidades disponibles.");
	}
	
	//función que devuelve los datos de un diccionario como string
	public static String stringOfIntHashtableContent(Hashtable<Integer,String> id_producto){
		String str_content = "";
		for (int i = 0; i < id_producto.size(); i++) {
			str_content += "[" + i + "] " + id_producto.get(i) + "\n";
		}
		return str_content;
	}
	
	//método para crear un producto nuevo
	public static void createProduct(Hashtable<Integer,String> id_producto, Hashtable<String,Integer> stock) {
		String nombre = JOptionPane.showInputDialog("Introduce el nombre del nuevo producto:");
		String str_cantidad_stock = JOptionPane.showInputDialog("Introduce cantidad de stock del producto:");
		while (!stringEsNumeroNatural(str_cantidad_stock)) {
			str_cantidad_stock = JOptionPane.showInputDialog("Datos no numéricos naturales, introduce cantidad de stock del producto:");
		}
		//creamos id de referencia y añadimos el producto
		id_producto.put(id_producto.size(), nombre);
		stock.put(nombre, Integer.parseInt(str_cantidad_stock));
	}
	
	//Llena los datos de los dos diccionarios con valores preestablecidos
	public static void llenarDatos(Hashtable<Integer,String> id_producto, Hashtable<String,Integer> stock) {
		//introducimos datos de stock de prooductos
		stock.put("Play Station 6 color red", 5);
		stock.put("Banana de color azul 🍌", 8);
		stock.put("Super Mario Kart 8 Delux", 145);
		stock.put("Zelda Breath Of The Wild", 300);
		stock.put("Zelda Tears Of The Kingdom", 30);
		stock.put("One Piece Full HD 4k ☠", 1);
		stock.put("Bolsa cutre de plástico", 1000);
		stock.put("Ensalada Cesar Mercadona", 100);
		stock.put("Antivirus Avast 2023", 33);
		stock.put("Chainsaw Man Temporada 1", 200);
		
		//llenamos con id numerico por cada elemento de productos
		Enumeration<String> ids = stock.keys();
		int contador = 0;
		while (ids.hasMoreElements()) {
			String nombre = ids.nextElement();
			id_producto.put(contador, nombre);
			contador++;
		}
	}
	
	//valida si string es numero positivo
	public static boolean stringEsNumeroNatural(String datos_keyboard) {
		//variable para comprobar si es dato numérico o no
		boolean es_natural;
		//comprueba si el dato es positivo y numéricoo no
		try {
			int numero = Integer.parseInt(datos_keyboard);
				es_natural = true;
			if (numero < 0) {
				es_natural = false;
			}
		} catch (Exception e) {
			es_natural = false;
		}
		return es_natural;
	}
	
}
