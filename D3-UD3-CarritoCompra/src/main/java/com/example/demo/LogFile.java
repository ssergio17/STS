package com.example.demo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogFile {
	protected final static String LOG_PATH = "logs.json";
	private List<LogEntry> entry;
	
	public LogFile() {
		super();
		entry = new ArrayList<>();
		loadLogFile();
	}
	
	public void loadLogFile() {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			File file = new File(LOG_PATH);
			entry = mapper.readValue(file,
					new TypeReference<List<LogEntry>>() {});
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void saveLogFile() {
		ObjectMapper mapper = new ObjectMapper();
	    File fichero = new File(LOG_PATH);

	    try {
	        mapper
	            .writerWithDefaultPrettyPrinter()
	            .writeValue(fichero, entry);
	    } catch (IOException e) {
	        System.out.println("Error guardando el archivo de log: " + e.getMessage());
	    }
	}
	
}
