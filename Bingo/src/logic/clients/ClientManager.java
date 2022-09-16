package logic.clients;

import java.util.ArrayList;
import java.util.List;

import logic.Config;
import utils.Check;
import utils.fileUtils.FileUtil;

public class ClientManager {
	
	public final static String FILE_NAME = "clientes.dat";
	
	public final static int ID_PARSE_POS = 0;
	public final static int NAME_PARSE_POS = 1;
	public final static int SURNAME_PARSE_POS = 2;
	public final static int AMOUNT_PARSE_POS = 3;
	
	
	private List<Client> clientList;
	
	public ClientManager() {
		clientList = new ArrayList<Client>();
		parseData();
	}
	
	public void inicialize() {
		clientList.clear();
		parseData();
	}
	
	public void addClient(String idDocument, String name, String surnames, double amount) {
		Client client = new Client(idDocument, name, surnames, amount);
		clientList.add(client);
	}
	
	public boolean isRegistered(String id) {
		Check.checkNotNull(id, "[ClientManager.isRegistered: La cadena introducida no es valida -NULO-]");
		for(Client c: clientList) {
			if(c.equals(id)) 
				return true;;
		}
		return false;
	}
	
	public void addCouponTo(String id) {
		Check.checkNotNull(id, "[ClientManager.addCouponTo: La cadena introducida no es valida -NULO-]");
		for(Client c: clientList) {
			if(c.equals(id)) {
				c.setAmount(c.getAmount() +Config.COUPON_AMOUNT);
				return;
			} 
		}
	}
	
	public Client getClient(String id){
		Check.checkNotNull(id, "[ClientManager.getClient: La cadena introducida no es valida -NULO-]");
		for(Client c: clientList) 
			if(c.equals(id)) 
				return c;
		
		return null;
	}
	
	private void parseData() {
		List<String[]> data = FileUtil.loadFile(FILE_NAME, "ClientManager.parseData");
		
		for(String[] line: data) {
			String idDocument = line[ID_PARSE_POS];
			String name = line[NAME_PARSE_POS];
			String surnames = line[SURNAME_PARSE_POS];
			double amount = Double.parseDouble(line[AMOUNT_PARSE_POS]);
			addClient(idDocument, name, surnames, amount);
		}
	}
	
	public void saveData() {
		List<String> formated = new ArrayList<String>();
		for(Client c: clientList)
			formated.add(c.toFormat());
		
		FileUtil.saveToFile(FILE_NAME, formated , "ClientManager.saveData");
	}
}
