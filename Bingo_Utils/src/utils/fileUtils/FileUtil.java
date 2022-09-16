package utils.fileUtils;

import java.io.*;
import java.util.*;

public abstract class FileUtil {

	public final static String PARSE_SEPARATOR = "@";
	public final static String DATA_PATH = "files/";
	
	
	public static List<String[]> loadFile(String fileName, String procedencia) {
		String line;
		List<String[]> data = new ArrayList<String[]>();
		
		try {
			BufferedReader file = new BufferedReader(new FileReader(DATA_PATH +fileName));
			while (file.ready()) {
				line = file.readLine();
				if(line.trim().length() != 0)
					data.add(line.split(PARSE_SEPARATOR));
			}
			file.close();
			
		} catch (FileNotFoundException fnfe) {
			return data;
		} catch (IOException ioe) {
			new RuntimeException("["+procedencia +".FileUtil.loadFile: error de E/S]");
		}
		
		return data;
	}

	
	public static void saveToFile(String fileName, List<String> data, String procedencia) {
		try {
			BufferedWriter file = new BufferedWriter(new FileWriter(DATA_PATH + fileName));
			for (String line : data)
				file.write(line + "\n");
			file.close();
			
		} catch (FileNotFoundException fnfe) {
			new RuntimeException("["+procedencia +".FileUtil.saveToFile: El archivo no se ha podido guardar]");
			
		} catch (IOException ioe) {
			new RuntimeException("["+procedencia +".FileUtil.saveToFile: Error de E/S]");
		}
	}
}
