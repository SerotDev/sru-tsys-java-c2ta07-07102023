package PT4;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JOptionPane;

public class VentasYStock {

	public static void main(String[] args) {
		// Declaramos diccionario de stock de productos y declaramos uno auxiliar para asignarle un numero a cada nombre y una lista para el carrito
		Hashtable<String,Integer> stock = new Hashtable<String,Integer>();
		Hashtable<Integer,String> id_producto = new Hashtable<Integer,String>();
		ArrayList<String[]> carrito = new ArrayList<String[]>(); // Declaramos lista de compra [nombre_producto, cantidad_articulos, precio_bruto,]
		
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
					+ "[4] Comprar Productos\n"
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
			//Comprar productos
			case "4":
				final double IVA = 0.21;
				comprarCantidadProductos(id_producto, stock, carrito);
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
	
	private static void comprarCantidadProductos(Hashtable<Integer, String> id_producto, Hashtable<String, Integer> stock, ArrayList<String[]> carrito) {
		final double IVA = 0.21;
		//Mostramos diccionario y pedimos que se seleccione el producto a comprar
		String str_product_id = "";
		while(!str_product_id.equals(String.valueOf(id_producto.size()))) {
			str_product_id= JOptionPane.showInputDialog("Introduce el número de producto para comprar:" + stringOfIntHashtableContent(id_producto) + "[" + id_producto.size() +"] FINALIZAR COMPRA");
			while (!stringEsNumeroNatural(str_product_id)) {
				str_product_id = JOptionPane.showInputDialog("Datos no numéricos naturales, introduce el número de producto para comprar:" + stringOfIntHashtableContent(id_producto));
			}
			int product_id = Integer.parseInt(str_product_id);
			while (product_id > id_producto.size()) {
				str_product_id = JOptionPane.showInputDialog("Opción inexistente, introduce el número de producto para comprar:" + stringOfIntHashtableContent(id_producto));
				while (!stringEsNumeroNatural(str_product_id)) {
					str_product_id = JOptionPane.showInputDialog("Datos no numéricos naturales, introduce el número de producto para comprar:" + stringOfIntHashtableContent(id_producto));
				}
				product_id = Integer.parseInt(str_product_id);
			}
			if(!str_product_id.equals(String.valueOf(id_producto.size()))) {
				//recogemos nombre correspondiente a la id introducida por el usuario
				String product_name = id_producto.get(product_id);
				
				//listamos producto en el carrito
				listarEnCarrito(product_name, stock, carrito);
			}
		}
		
		// Recogemos precio totales, calculamos y mostramos compra
		int cantidad_total = getCantidadTotal(carrito);
		double precio_total_bruto = getPrecioTotalBruto(carrito);
		double precio_total_neto = precio_total_bruto * (1+IVA);
				
		//pedimos al usuario que finalice la compra
		String str_pago_cliente;
		str_pago_cliente = JOptionPane.showInputDialog("El precio total de los artículos a comprar asciende a " + String.format("%.2f",precio_total_neto) + "€\n"
				+ "Introduzca la cantidad de dinero a pagar:");
		//validamos que el precio que pague sea superior al que asciende la compra
		while (!stringEsNumeroPositivo(str_pago_cliente)) {
			str_pago_cliente = JOptionPane.showInputDialog("Fallo de transacción, introduce un valor igual o superior a " + String.format("%.2f",precio_total_neto) + "\n"
					+ "Introduzca la cantidad de dinero a pagar:");
		}
		while (Double.parseDouble(str_pago_cliente) < precio_total_neto) {
			str_pago_cliente = JOptionPane.showInputDialog("Dinero insuficiente, introduce un valor igual o superior a " + String.format("%.2f",precio_total_neto) + "\n"
					+ "Introduzca la cantidad de dinero a pagar:");
			while (!stringEsNumeroPositivo(str_pago_cliente)) {
				str_pago_cliente = JOptionPane.showInputDialog("Fallo de transacción, introduce un valor igual o superior a " + String.format("%.2f",precio_total_neto) + "\n"
						+ "Introduzca la cantidad de dinero a pagar:");
			}
		}
				
		//se calcula el dinero a devolver
		double pago_cliente = Double.parseDouble(str_pago_cliente);
		double dinero_a_devolver = pago_cliente - precio_total_neto;
		
		// Mostramos datos de compra
		JOptionPane.showMessageDialog(null, "-----------------  TICKET DE COMPRA  -----------------\n"
				+ "" + getListaEnString(carrito) + ""
				+ "------------------------------------------------------\n"
				+ "IVA APLICADO: " + String.format("%.2f",(IVA*100)) + "%\n"
				+ "TOTAL BRUTO: " + String.format("%.2f",precio_total_bruto) + "€\n"
				+ "TOTAL NETO (+IVA): " + String.format("%.2f",precio_total_neto) + "€\n"
				+ "CANTIDAD DE ARTÍCULOS COMPRADOS: " + cantidad_total + "\n"
				+ "CANTIDAD PAGADA: " + String.format("%.2f",pago_cliente) + "€\n"
				+ "CAMBIO A DEVOLVER: " + String.format("%.2f",dinero_a_devolver) + "€");

	}
	
	//metodo para devolver en string los datos del arraylist
	public static String getListaEnString(ArrayList<String[]> carrito) {
		String str_lista = "";
		for(int i = 0; i < carrito.size(); i++) {
			str_lista += carrito.get(i)[0] + "  |  Cantidad:" + carrito.get(i)[1] + "  |  Precio:" + carrito.get(i)[2] + "€\n";
		}
		return str_lista;
	}

	//metodo para devolver la cantidad total de todos productos del carrito.
	public static int getCantidadTotal(ArrayList<String[]> carrito) {
		int cantidad = 0;
		for(int i = 0; i < carrito.size(); i++) {
			cantidad += Integer.parseInt(carrito.get(i)[1]); //vamos sumando la cantidad de cada producto
		}
		return cantidad;
	}
	
	//metodo para devolver el precio bruto de todos productos del carrito.
	public static double getPrecioTotalBruto(ArrayList<String[]> carrito) {
		double precio = 0;
		for(int i = 0; i < carrito.size(); i++) {
			precio += Double.parseDouble(carrito.get(i)[2]);  //vamos sumando los precios totales en bruto
		}
		return precio;
	}
	
	//metodo para insertar un producto al carrito y actualizar stock [nombre_producto, cantidad_articulos, precio_bruto,]
	public static void listarEnCarrito(String product_name, Hashtable<String, Integer> stock, ArrayList<String[]> carrito) {
		String cantidad = "";
		cantidad = JOptionPane.showInputDialog("Introduce la cantidad que desees comprar, 0 si no quieres comprar ninguno.\n"
				+ "Artículo: " + product_name);
		
		//validamos que la cantidad introducida sea un numero real
		while (!stringEsNumeroNatural(cantidad)) {
			cantidad = JOptionPane.showInputDialog("Artículo: " + product_name + "\n"
					+ "Introduce cantidad a comprar, [0] si no quieres comprar ninguno.");
		}
		
		//validamos stock disponible y actualizamos stock
		int quantity = Integer.parseInt(cantidad);
		int avalible_stock = stock.get(product_name);
		if (Integer.parseInt(cantidad) >= avalible_stock) {
			quantity = avalible_stock;
			JOptionPane.showMessageDialog(null, "Stock insuficiente, se han seleccionado todas las unidades disponibles (" + avalible_stock + ")");
		}
		avalible_stock -= quantity;
		stock.replace(product_name, avalible_stock);
		
		//calculamos precio de la cantidad del producto y guardamos datos como array de string
		double price_num = stock.get(product_name) * quantity;
		String price = String.format("%.2f",price_num).replace(",", ".");//me formata a coma cuando paso a string
		String linea_carrito[] = {product_name,String.valueOf(quantity),price};

		//añadimos linia al carrito
		carrito.add(linea_carrito);
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
	
	//valida si string es numero positivo
	public static boolean stringEsNumeroPositivo(String datos_keyboard) {
		//variable para comprobar si es dato numérico o no
		boolean es_numero_positivo;
		//comprueba si el dato es positivo y numéricoo no
		try {
			double numero = Double.parseDouble(datos_keyboard);
			es_numero_positivo = true;
			if (numero < 0) {
				es_numero_positivo = false;
			}
		} catch (Exception e) {
			es_numero_positivo = false;
		}
		return es_numero_positivo;
	}

}
