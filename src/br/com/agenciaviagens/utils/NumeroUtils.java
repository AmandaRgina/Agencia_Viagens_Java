package br.com.agenciaviagens.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

public abstract class NumeroUtils {
	
	private static final DecimalFormat FORMATO = new DecimalFormat("#,##0.00");
	
	public static String converte(Double valor) {
		
		if(valor == null) {
			
			return null;
		}
		
		return FORMATO.format(valor);
		
	}
	
	public static Double converte(String valor) throws ParseException {
		
		if(valor == null || valor.isBlank()) {
			
			return null;
		}
		
		Number numero = FORMATO.parse(valor);
		
		return numero.doubleValue();
		
	}
	
	
}

