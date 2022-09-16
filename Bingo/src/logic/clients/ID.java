package logic.clients;

public enum ID {
	DNI, PASSPORT;

	public static ID parseID(String type) {
		if(type.toLowerCase().equals("dni"))
			return DNI;
		else return PASSPORT;
	}
	
}
