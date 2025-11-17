package com.example.demo;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Stock {
	
	protected Map<String, Integer> stock;
	protected final static String FILE_PATH = "stock.data";

	public Stock() {
		super();
		stock = load();
	}

	public Map<String,Integer> load() {
		// TRAZA PARA SABER DÓNDE RESUELVE LA RUTA RELATIVA:
        // System.out.println((new File(RUTA_FICHERO)).getAbsolutePath());
		// intentamos leer el mapa del archivo, si no podemos quedará vacío
		try (
			ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(FILE_PATH))
		) {
			return (HashMap<String, Integer>) entrada.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Problema leyendo archivo " + FILE_PATH);
             return new HashMap<String,Integer>();
		}
       
	}

	public boolean save() {
		try (
				ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(FILE_PATH))
		) {
			salida.writeObject(stock);
		} catch (IOException e1) {
			System.err.println("ERROR GUARDANDO STOCK");
			return false;
		}
		return true;
	}

	public Map<String, Integer> getAll() {
		return stock;
	}

	public Integer getOne(String producto) {
		return stock.get(producto);
	}

	public void add(String producto, Integer cantidad) {
		stock.put(producto, cantidad);
		save();
	}

	public void del(String producto) {
	        stock.remove(producto);
	    	save();
		}

	public void modify(String producto, Integer cantidad) {
			stock.put(producto, cantidad);
	    	save();
		}

    
	public static void main(String[] args) {
		Stock stock = new Stock();
		System.out.println(stock.getAll());
		stock.add("Platanos", 10);
		stock.add("Kiwis", 20);
		stock.add("Naranjas", 15);
	}
}
