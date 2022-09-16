package utils;


public class Check {

	public static void checkNotNull(Object o, String msg) {
		 if(o == null)
			 throw new IllegalArgumentException(msg);
	}
	
	public static void checkString(String str, String procedencia) {
		checkNotNull(str, "["+procedencia+ ": La cadena introducida no es valida -NULO-]");
		if(str.trim().isEmpty())
			 throw new IllegalArgumentException("["+procedencia+": La cadena introducida está vacia]");
	}
	
	public static void isTrue(boolean conditionTrue, String procedencia, String msg) {
		if(conditionTrue) return;
		throw new IllegalArgumentException("["+procedencia+": " +msg +"]");
	}
	
}