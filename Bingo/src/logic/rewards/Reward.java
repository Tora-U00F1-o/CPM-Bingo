package logic.rewards;

import utils.Check;

public class Reward {

	public final static String DEFAULT_FOTO_PATH = "/img/";
	public final static char LINE_CHAR = 'l';
	public final static char BINGO_CHAR = 'b';
	
	private String code;
	private String denomination;
	private char price;  // l = line , b = bingo
	private int uds;
	
	public Reward(String code, String denomination, char price, int uds) {
		setCode(code);
		setDenomination(denomination);
		setPrice(price);	
		setUds(uds);
	}
	
	public Reward(Reward other, int uds) {
		this(other.getCode(), other.getDenomination(), other.getPrice(), uds);
	}

	public String getCode() {
		return code;
	}

	private void setCode(String code) {
		Check.checkString(code, "Reward.setCode");
		this.code = code;
	}

	public String getDenomination() {
		return denomination;
	}

	private void setDenomination(String denomination) {
		Check.checkString(code, "Reward.setDenomination");
		this.denomination = denomination;
	}

	public char getPrice() {
		return price;
	}

	private void setPrice(char price) {
		Check.isTrue((price == LINE_CHAR || price == BINGO_CHAR), 
						"Reward.setPrice", "Valor de precio invalido");
		this.price = price;
	}

	public int getUds() {
		return uds;
	}

	protected void setUds(int uds) {
		Check.isTrue(uds>=0, "Reward.setUds", "Cantidad introducida no válida: <0");
		this.uds = uds;
	}

	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(this == o)
			return true;
		
		if(!this.getClass().equals(o.getClass()))
			return false;
		Reward other = (Reward) o;
		if(this.getCode().equals(other.getCode()))
			return true;
		
		return false;
		
	}

	// Método que redefine toString
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.denomination);
		buffer.append(" - 1 ");
		buffer.append(this.price);
		
		return buffer.toString();
	}
	

}
