package logic.game;

public class Box implements Comparable<Box>{

	private int number;
	private boolean marked;
	private boolean magicNumber;
	private int position;
	
	public Box(int number, boolean magicNumber, int numberOfCasilla) {
		setNumber(number);
		setMagicNumber(magicNumber);
		setPosition(numberOfCasilla);
	}
	
	public Box(Box other) {
		this(other.getNumber(), other.isMagicNumber(), other.getPosition());
	}
	
	public int getNumber() {
		return number;
	}
	
	private void setNumber(int number) {
		this.number = number;
	}
	
	public boolean isMagicNumber() {
		return magicNumber;
	}
	
	protected void setMagicNumber(boolean magicNumber) {
		this.magicNumber = magicNumber;
	}

	public void mark() {
		marked = true;
	}
	
	public boolean isMarked() {
		return this.marked;
	}

	public int getPosition() {
		return position;
	}

	private void setPosition(int numberOfCasilla) {
		this.position = numberOfCasilla;
	}
	
	public int getRow() {
		return position/Board.N_COLUMNS_BOARD;
	}
	
	public int getColumn() {
		return position%Board.N_COLUMNS_BOARD;
	}

	
	public int compareTo(Box other) {
		return this.getNumber() - other.getNumber();
	}
	
	
}
