package logic.clients;

import utils.Check;
import utils.fileUtils.FileUtil;

public class Client {
	
	private String idDocument;
	private String name;
	private String surnames;
	private double amount;
	
	public Client(String idDocument, String name, String surnames, double amount) {
		setIdDocument(idDocument);
		setName(name);
		setSurnames(surnames);
		setAmount(amount);
	}

	public String getIdDocument() {
		return idDocument;
	}

	private void setIdDocument(String idDocument) {
		Check.checkString(idDocument, "Client.setIdDocument");
		this.idDocument = idDocument;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		Check.checkString(name, "Client.setName");
		this.name = name;
	}

	public String getSurnames() {
		return surnames;
	}

	private void setSurnames(String surnames) {
		Check.checkString(surnames, "Client.setSurnames");
		this.surnames = surnames;
	}

	public double getAmount() {
		return amount;
	}

	protected void setAmount(double d) {
		Check.isTrue(d>=0, "Client.setAmount", "Cantidad introducida no válida: <0");
		this.amount = d;
	}
	
	// 10452782@Marta@Perez Lopez@20
	// documento_identificacion@nombre@apellidos@importe_acumulado
	public String toFormat() {
		String sep = FileUtil.PARSE_SEPARATOR;
		return idDocument +sep +name +sep +surnames +sep +amount;
	}
	
	public boolean equals(String id) {
		return this.idDocument.equals(id);		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDocument == null) ? 0 : idDocument.hashCode());
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
		Client other = (Client) obj;
		if (idDocument == null) {
			if (other.idDocument != null)
				return false;
		} else if (!idDocument.equals(other.idDocument))
			return false;
		return true;
	}
	
}
