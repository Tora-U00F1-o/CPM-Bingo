package logic.tickets;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import logic.Config;
import utils.Check;
import utils.fileUtils.FileUtil;

public class TicketManager {
	
	public final static String FILE_NAME = "tickets.dat";
	
	public final static int EAN_PARSE_POS = 0;
	public final static int AMOUNT_PARSE_POS = 1;
	
	private List<Ticket> ticketsList;
	private Ticket ticketUsed;
	
	
	public TicketManager() {
		ticketsList = new ArrayList<Ticket>();
		parseData();
	}
	
	public void inicialize() {
		ticketsList.clear();
		parseData();
		ticketUsed = null;
	}
	
	private void parseData() {
		List<String[]> data = FileUtil.loadFile(FILE_NAME, "TicketManager.parseData");
		
		for(String[] line: data) {
			String ean = line[EAN_PARSE_POS];
			double amount = Double.parseDouble(line[AMOUNT_PARSE_POS]);
			ticketsList.add(new Ticket(ean, amount));
		}
	}
	
	public boolean isTicketValid(String ean) {
		Check.isTrue(ean!=null, "TicketManager", "isTicketValid");
		for(Ticket t: ticketsList)
			if(t.equals(ean.trim())) 
				if(t.getAmount() >= Config.TICKET_MIN_AMOUNT) {
					ticketUsed = t;
					return true;
				} else
					return false; //SI no llega al limite
			
		return false;
	}
	
	public void invalidateTicket() {
		if(ticketUsed == null) return;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				removeLine();
			}
		});
	}
	
	private void removeLine() {
		ticketsList.remove(ticketUsed);
		saveData();
		ticketUsed = null;
	}
	
	public void saveData() {
		List<String> formated = new ArrayList<String>();
		for(Ticket c: ticketsList)
			formated.add(c.toFormat());
		
		FileUtil.saveToFile(FILE_NAME, formated , "TicketManager.saveData");
	}
	
	
	public class Ticket {
		
		private String ean;
		private double amount;
		
		public Ticket(String ean, double amount) {
			setEAN(ean);
			setAmount(amount);			
		}
		
		public String getEAN() {
			return ean;
		}
		
		private void setEAN(String ean) {
			Check.checkString(ean, "Ticket.setEAN");
			this.ean = ean;
		}
		
		public double getAmount() {
			return amount;
		}
		
		private void setAmount(double amount) {
			Check.isTrue(amount >= 0, "Ticket.setAmount", "Cantidad del ticket no valida");
			this.amount = amount;
		}
		
		// 54286@100.75
		public String toFormat() {
			String sep = FileUtil.PARSE_SEPARATOR,
					patron = ean +sep +"%.2f";
			
			return String.format(patron, amount).replace(',', '.');
		}
		
		public boolean equals(String ean) {
			if(ean != null && getEAN().equals(ean))
				return true;
			return false;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((ean == null) ? 0 : ean.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Ticket other = (Ticket) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (ean == null) {
				if (other.ean != null)
					return false;
			} else if (!ean.equals(other.ean))
				return false;
			return true;
		}

		private TicketManager getEnclosingInstance() {
			return TicketManager.this;
		}
		
	}
}
