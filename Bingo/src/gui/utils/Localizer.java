package gui.utils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

public class Localizer {

	public final static String RESOURCES_FILE = "rcs/texts";
	
	public final static String ES_CODE = "es";
	public final static String EN_CODE = "en";
	
	public final static int AUTO_MODE = 0;
	public final static int ES_MODE = 1;
	public final static int EN_MODE = 2;

	private int mode;
	private Locale location;
	private ResourceBundle rsc;
	
	/**
	 * Create the frame.
	 */
	public Localizer() {
		location = Locale.getDefault(Locale.Category.FORMAT);
		rsc = ResourceBundle.getBundle(RESOURCES_FILE, location);
	}
	
	public void setMode(int mode) {
		this.mode = mode;
		switch(mode) {
		case ES_MODE:
			changeLocation(new Locale(ES_CODE));
			break;
		case EN_MODE:
			changeLocation(new Locale(EN_CODE));
			break;
		default:
			changeLocation(Locale.getDefault(Locale.Category.FORMAT));
			break;
		}
	}
	
	public int getMode() {
		return this.mode;
	}
	
	public Locale getLocation() {
		return this.location;
	}
	
	public void changeLocation(Locale locale) {
		location = locale;
		rsc = ResourceBundle.getBundle(RESOURCES_FILE, locale);

		JOptionPane.setDefaultLocale(location);
	}
	
	public String getLocateText(String key) {
		return rsc.getString(key);
	}
	
	public char getLocateChar(String key) {
		return rsc.getString(key).charAt(0);
	}
	
	public String getLocateDate() {
		Date fechaHora = new Date();
		
		DateFormat formatoFecha = DateFormat.getDateInstance(DateFormat.LONG, location);
		return formatoFecha.format(fechaHora);
	}
	
	public String getLocateTime() {
		Date fechaHora = new Date();
		
		DateFormat formatoHora = DateFormat.getTimeInstance(DateFormat.LONG, location);
		return formatoHora.format(fechaHora);
	}
	
	public String getLocateAmout(double amount) {
		NumberFormat formatoImporte = NumberFormat.getNumberInstance(location);
		return formatoImporte.format(amount);
	}
}
