package logic.game;

import java.util.Arrays;

import utils.CodeUtil;
import utils.exceptions.UserException;

public class Board {

	public final static int N_ROWS_BOARD = 3;
	public final static int N_COLUMNS_BOARD = 3;
	
	public final static int MIN_NUM_COL_0 = 1;
 	public final static int MAX_NUM_COL_0 = 10;
	
	private Box[][] board;
	
	private boolean[] lineStatus;
	private int nlinesMarked;
	private int nBoxMarked;
	
	private boolean hasBingo;
	private boolean hasMagicNumber;
	
	
	public Board() {
		board = new Box[N_ROWS_BOARD][N_COLUMNS_BOARD];
		fillBoard();
		lineStatus = new boolean[N_ROWS_BOARD];
	}
	
	public Box getBox(int row, int col) {
		return board[row][col];
	}
	public Box getBox(int pos) {
		int row = pos / Board.N_COLUMNS_BOARD;
		int col = pos % Board.N_COLUMNS_BOARD;
		return board[row][col];
	}
	
	public void mark(int row, int column, int lastBall) throws UserException {
		if(board[row][column].getNumber() == lastBall) {
			board[row][column].mark();
			nBoxMarked++;
		} else
			throw new UserException();
	}
	
	public void markLine(int lastBall) throws UserException {
		if(nlinesMarked < N_ROWS_BOARD-1 && nBoxMarked < N_COLUMNS_BOARD*N_ROWS_BOARD) 
			for(int nLine = 0; nLine<N_ROWS_BOARD; nLine++) 
				if(!lineStatus[nLine]) 
					if( isLineComplete(board[nLine]) && CodeUtil.isInArray(lastBall, getIntegersOfRow(board[nLine])) ) {
						lineStatus[nLine] = true;
						nlinesMarked++;
						if(lineHasMagicNumber(board[nLine]))
							hasMagicNumber = true;
						return;
					}
			
		throw new UserException("No puedes cantar ninguna linea");
	}
	
	public void markBingo(int lastBall) throws UserException {
		if(hasBingo)
			throw new UserException();
		
		if(nBoxMarked == N_COLUMNS_BOARD*N_ROWS_BOARD)
			for(int nLine = 0; nLine<N_ROWS_BOARD; nLine++) 
				if(CodeUtil.isInArray(lastBall, getIntegersOfRow(board[nLine])) ) {
					hasBingo = true;
					hasMagicNumber = true;
					return;
				}
			
		throw new UserException("No puedes cantar ninguna linea");
	}
	
	public int getNLinesMarked() {
		return nlinesMarked;
	}
	
	public boolean hasBingo() {
		return hasBingo;
	}
	
	public boolean hasMagicNumber() {
		return hasMagicNumber;
	}
	
	private int[] getIntegersOfRow(Box[] row) {
		int[] ints = new int[row.length];
		for(int i=0; i<row.length; i++)
			ints[i] = row[i].getNumber();
		
		return ints;
	}
	
	private boolean isLineComplete(Box[] line) {
		for(Box b: line)
			if(b.isMarked() == false)
				return false;
		return true;
	}
	
	private boolean lineHasMagicNumber(Box[] line) {
		for(Box b: line)
			if(b.isMagicNumber())
				return true;
		return false;
	}
	
	private void fillBoard() {
		int pos = 0;
		for(int j = 0; j<N_COLUMNS_BOARD; j++) {
			
			int[] nUseds = new int[N_ROWS_BOARD];
			for(int i=0; i<N_ROWS_BOARD; i++) {
				int number = getNewNumber(j, nUseds);
				board[i][j] = new Box(number, false, pos++);
				nUseds[i] = number;
			}
			sortColumn(j);
		}
		
		int magicNumber = CodeUtil.getRandomNumber(0, (N_ROWS_BOARD*N_COLUMNS_BOARD)-1);
		getBox(magicNumber).setMagicNumber(true);
	}
	
	private void sortColumn(int column) {
		Box[] col = new Box[N_ROWS_BOARD];
		for(int i=0; i<N_ROWS_BOARD; i++)
			col[i] = board[i][column];
		
		Arrays.sort(col);
		for(int i=0; i<N_ROWS_BOARD; i++) {
			Box box = col[i];
			board[i][column] = new Box(box.getNumber(), box.isMagicNumber(), i+column+(N_ROWS_BOARD-1)*i );
		}
	}
	
	private int getNewNumber(int nColumn, int[] nUseds) {
		return CodeUtil.getRandomNumber(MIN_NUM_COL_0 + nColumn*10, MAX_NUM_COL_0 + nColumn*10, nUseds);
	}

}
