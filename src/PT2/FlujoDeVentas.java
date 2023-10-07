package PT2;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JOptionPane;

public class FlujoDeVentas {

	public static void main(String[] args) {
		// Declaramos constante del IVA
		final double IVA = 0.21;
		
		// Declaramos diccionario de productos
		Hashtable<String,Double> productos = new Hashtable<String,Double>();
		
		//introducimos datos de productos
		productos.put("Play Station 6 color red", 1555.44);
		productos.put("Banana de color azul 🍌", 5.44);
		productos.put("Super Mario Kart 8 Delux", 69.95);
		productos.put("Zelda Breath Of The Wild", 10.15);
		productos.put("Zelda Tears Of The Kingdom", 30.38);
		productos.put("One Piece Full HD 4k ☠", 1.00);
		productos.put("Bolsa cutre de plástico", 0.06);
		
		// Declaramos lista de compra [nombre_producto, cantidad_articulos, precio_bruto,]
		ArrayList<String[]> carrito = new ArrayList<String[]>();
		
		// Pedimos cantidad de cada producto [nombre_producto, cantidad_articulos, precio_bruto,]
		comprarCantidadProductos(productos, carrito);
		
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
				+ "IVA APLICADO: " + (IVA*100) + "%\n"
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
	
	//metodo para mostrar producto uno por uno y pedir cantidad a comprar [nombre_producto, cantidad_articulos, precio_bruto,]
	public static void comprarCantidadProductos(Hashtable<String,Double> productos, ArrayList<String[]> carrito) {
		Enumeration <String> nombres_productos = productos.keys();
		while (nombres_productos.hasMoreElements()) {
			String nombre = nombres_productos.nextElement();
			String cantidad = "";
			cantidad = JOptionPane.showInputDialog("Introduce la cantidad que desees comprar, 0 si no quieres comprar ninguno.\n"
					+ "Artículo: " + nombre);
			//validamos que la cantidad introducida sea un numero real
			while (!stringEsNumeroNatural(cantidad)) {
				cantidad = JOptionPane.showInputDialog("Artículo: " + nombre + "\n"
						+ "Introduce cantidad a comprar, [0] si no quieres comprar ninguno.");
			}
			//calculamos precio de la cantidad del prducto y guardamos como string
			double precio_num = productos.get(nombre) * Integer.parseInt(cantidad);
			String precio = String.format("%.2f",precio_num).replace(",", ".");//me formata a coma cuando paso a string
			String linea_carrito[] = {nombre,cantidad,precio};
			//System.out.println(linea_carrito[0] + " " + linea_carrito[1] + " " + linea_carrito[2]);
			carrito.add(linea_carrito);
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
	
	/*devolvemos string con los datos de todos los productos existentes y su precio
	public static String getStringProductos(Hashtable<String, Double> productos) {
		String texto_modal_productos = "";
		Enumeration <String> nombres_productos = productos.keys();
		while (nombres_productos.hasMoreElements()) {
			String nombre = nombres_productos.nextElement();
			texto_modal_productos += "Nombre: " + nombre + "  |  Precio: " + String.format("%.2f", productos.get(nombre)) + "€.\n";
		}
		return texto_modal_productos;
	}
	*/

}
