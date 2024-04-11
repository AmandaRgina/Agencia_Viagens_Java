package br.com.agenciaviagens.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DataUtils {
	
	private static final SimpleDateFormat FORMATO = new SimpleDateFormat("dd/MM/yyyy");
	
	
	public static Date converteData(String data) throws ParseException {
		
		if(data == null || data.isBlank()) {
			
			return null;
			
		}
		
		return FORMATO.parse(data);
		
	}
	
	public static String converteData(Date data) {
		
		if(data == null) {
			
			return null;
		}
		
		return FORMATO.format(data);
	}

}
