package com.example.demo;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class RepositorioStock {

	protected Map<String, Integer> stock;
	protected final static String FILE_PATH = "stock.data";
	
	public RepositorioStock() {
		super();
		stock = new HashMap<String, Integer>();
		carga();
	}

	public void carga() {
		// TRAZA PARA SABER DÓNDE RESUELVE LA RUTA RELATIVA:
        // System.out.println((new File(FILE_PATH)).getAbsolutePath());
		// Intentamos leer el mapa del archivo, si no podemos quedará vacío
		try (
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))
		) {
			stock = (HashMap<String, Integer>) in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Problema leyendo archivo: " + FILE_PATH);
		}
	}

	public boolean guardar() {
		try (
				ObjectOutputStream close = new ObjectOutputStream(new FileOutputStream(FILE_PATH))
		) {
			close.writeObject(stock);
		} catch (IOException e1) {
			System.err.println("ERROR GUARDANDO PUNTUACIONES");
			return false;
		}
		return true;
	}

	public Map<String, Integer> getAll() {
		return stock;
	}

	public Integer getStock(String producto) {
		return stock.get(producto);
	}

	public void addStock(String producto, Integer cantidad) {
		stock.put(producto, cantidad);
		guardar();
	}

	public void removeStock(String producto) {
	        stock.remove(producto);
	    	guardar();
		}

	public void modifyStock(String producto, Integer cantidad) {
			stock.put(producto, cantidad);
	    	guardar();
		}

    // Prueba de concepto para ejecutar desde Eclipe o Visual
    ///*
	public static void main(String[] args) {
		RepositorioStock repo = new RepositorioStock();
		System.out.println(repo.getAll());
		repo.addStock("Peras",10);
		repo.addStock("Manzanas", 12);
		repo.guardar();
		
	}
    //*/

	
}
