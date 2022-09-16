package utils.log;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import utils.Check;


public class Log {

	private final static String LOG_FILE_NAME = "log.log";

	/**
	 * Sends the string received as message to the log prefixing it with 
	 * a timestamp 
	 * @param message
	 */
	public static void log(String message) {
		Check.checkNotNull(message, "[Log.log: Texto a escribir NULO]");
		BufferedWriter writer = null;
		
		try {
			try {
				writer = new BufferedWriter(new FileWriter(LOG_FILE_NAME, true));
				writer.write(new Date()+": " +message +"\n");
				
			} finally {
				writer.close();
			}

		} catch (FileNotFoundException e) {
			throw new RuntimeException("[Log.log: Archivo de registro: " +LOG_FILE_NAME +" NO ENCONTRADO]");
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Sends the full stack trace of the exception received to the log
	 * prefixing it with a timestamp 
	 * @param t, the exception to be logged
	 */
	public static void log(Throwable t) {
		log(t.getClass() +" " +t.getCause()+": " +t.getMessage() +" " +t.getStackTrace());
	}
}
