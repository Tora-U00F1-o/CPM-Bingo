package utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;





public class CodeUtil {

	
	public static  boolean isInArray(int n, int[] nUseds) {
		for(Integer number: nUseds)
			if(number.equals(n))
				return true;
		
		return false;
	}
	
	public static int getRandomNumber(int minLimit, int maxLimit, int[] nUseds) {
		while(true) {
			int n = getRandomNumber(minLimit, maxLimit);
			if(!isInArray(n, nUseds))
				return n;
		}
		
	}
	
	public static int getRandomNumber(int minLimit, int maxLimit) {
			return (int) (Math.random()*(maxLimit-minLimit+1)+minLimit);
	}
	

	
	
	
	/**
	 * Devuelve un string de el numero introducido con un añadido:
	 * si tiene una cifra le pone un 0 delante, si tiene mas 
	 * de una cifra no hace nada
	 * @param n	numero a analizar
	 * @return string de el numero introducido
	 */
	public static String makeNumberToStringPretty(int n) {
		return n/10==0 ? "0"+n : ""+n;
	}
	
	/**
	 * Devuelve el string -parametro- pero x espacios en blanco
	 * hasta que la longitud del string total sea la indicada 
	 * en el entero -parametro-
	 * @param s La cadena a tratar
	 * @param n la longitud final de la cadena
	 * @return la cadena tratada
	 */
	public static String fillStringWithWhites(String s, int n) {
		Check.checkNotNull(s, "String a tratar no valido");
		return s +stringMultiplicator(" ", n-s.length());
	}
	
	/**
	 * Devuelve el String -parametro- multiplicado por n -parametro-
	 * @param s Cadena a tratar
	 * @param n Numero de veces a repetir
	 * @return El String -parametro- multiplicado por n -parametro-
	 */
	public static String stringMultiplicator(String s, int n) {
		Check.checkNotNull(s, "String a tratar no valido");
		
		String result = "";
		for(int i=0; i<n; i++)
			result += s;
		return result;
	}
	
	/**
	 * Ordena la lista introducida -parametro- en funcion a los criterios del
	 * comparator -parametro-
	 * @param <T>
	 * @param list
	 */
	public static <T> void sort(List<T> list, Comparator<T> comparator) {
		Check.checkNotNull(list, "Lista a ordenar nula");
		Check.checkNotNull(comparator, "Comparador nulo");
		Collections.sort(list, comparator);
	}
	
}