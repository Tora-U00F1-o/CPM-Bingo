package logic.game;


import logic.tickets.TicketManager;
import utils.CodeUtil;
import utils.exceptions.UserException;

public class Bingo {
	public final static int N_TYPES_AWARDS = 2;
	public final static int POS_N_LINES = 0;
	public final static int POS_N_BINGO = 1;
	
	public final static int N_MAX_BALLS = 30;
	
	private TicketManager manager;
	private Board board;
	private int nBalls;
	private int[] obtainedBalls;
	
	
	public Bingo(TicketManager manager) {
		this.manager = manager;
		board = new Board();
		obtainedBalls = new int[N_MAX_BALLS];
	}
	
	public void newGame(TicketManager manager) {
		this.manager = manager;
		board = new Board();
		obtainedBalls = new int[N_MAX_BALLS];
		nBalls = 0;
	}
	
	public int getBallsLeft() {
		return N_MAX_BALLS-nBalls;
	}
	public int getNewBall() {
		if(isGameOver())
			return -1;
		if(nBalls==0)
			manager.invalidateTicket();
		int maxNumberPosibleInBoard = Board.MAX_NUM_COL_0 + (Board.N_COLUMNS_BOARD-1)*10,
			ball; 
		do {
			ball = CodeUtil.getRandomNumber(Board.MIN_NUM_COL_0, maxNumberPosibleInBoard);
			
		} while(CodeUtil.isInArray(ball, obtainedBalls));
		
		obtainedBalls[nBalls] = ball;
		nBalls++;
		return ball;		
	}
	
	public int getLastBall() throws UserException {
		if(nBalls == 0)
			throw new UserException("Tienes que sacar una bola");
		return obtainedBalls[nBalls-1];
	}
	
	public void mark(int row, int column) throws UserException {
		board.mark(row, column, getLastBall());
	}
	
	public void markLine() throws UserException {
		board.markLine(getLastBall());
	}
	
	public void markBingo() throws UserException {
		board.markBingo(getLastBall());
	}
	
	public boolean isGameOver() {
		return nBalls >= N_MAX_BALLS || board.hasBingo();
	}
	
	public boolean hasWonSomething() {
		int nRewards = 0;
		nRewards += board.hasBingo() ? 1 : 0;
		nRewards += board.getNLinesMarked();
		return nRewards != 0;
	}
	
	public boolean hasmagicNumber() {
		return board.hasMagicNumber();
	}
	
	public int[] getScore() {
		int[] awrd = new int[N_TYPES_AWARDS];
		awrd[POS_N_LINES] = board.getNLinesMarked();
		awrd[POS_N_BINGO] = board.hasBingo() ? 1 : 0;
		
		return awrd;				
	}
	
	public Board getBoard() {
		return this.board;
	}
	
}
