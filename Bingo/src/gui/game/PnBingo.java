package gui.game;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import logic.game.Bingo;
import logic.game.Board;
import logic.tickets.TicketManager;
import utils.exceptions.UserException;

import java.awt.Color;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import events.ProcessMarked;
import gui.MainWindow;
import gui.utils.BingoPanel;
import gui.utils.FactoryImage;
import gui.utils.Localizer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

public class PnBingo extends JPanel implements BingoPanel {
	private static final long serialVersionUID = 1L;
	
	private MainWindow window;
	private TicketManager manager;
	private Localizer localizer;
	private Bingo bingo; 
	
	private ProcessMarked processMarked;
	private JLabel lblImgLastBall;
	private JPanel pnBoard;
	private JPanel pnBoxes;
	private JPanel pnActions;
	private JButton btnBingo;
	private JButton btnLine;
	private JButton btnNewBall;
	private JButton btnContinue;
	private JButton btnExit;
	private JPanel pnObtainedBall;
	private JPanel pnBtnNavigation;
	private JPanel pnCentral;
	private JPanel pnInteraction;
	private JPanel pnBtnActions;
	private JPanel pnBallContents;
	private JPanel pnHead;
	private JPanel pnHead_1;
	private JPanel pnHead_2;
	private JPanel pnHead_3;
	private JLabel lblHead_1;
	private JLabel lblHead_2;
	private JLabel lblHead_3;
	private JPanel pnIntContents;
	private JLabel lblStar;
	private JPanel pnNewBall;
	private JPanel pnActions1;
	private JLabel lblNBallsLeft;

	/**
	 * Create the panel.
	 */
	public PnBingo(MainWindow window, Localizer localizer, Bingo bingo, TicketManager manager) {
		setBackground(new Color(51, 204, 255));
		setOpaque(false);
		
		this.window = window;
		this.manager = manager;
		this.localizer = localizer;
		this.bingo = bingo;
		
		this.processMarked = new ProcessMarked(this);

		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(0, 0));
		add(getPnCentral(), BorderLayout.CENTER);
		
	}
	public Bingo getBingo() {
		return bingo;
	}
	public void resize() {
		try {
			updatelblLastBall();
			setBoxIcons();

			ImageIcon icon = FactoryImage.loadImagen(FactoryImage.IMAGE_RABBIT);
			FactoryImage.setAdaptedImgLbl(lblStar, icon.getImage());
		} catch (IllegalArgumentException e) { }
	}
	public void setDefaultConfig() {
		bingo.newGame(manager);

		fillBoard();
		getLblImgLastBall().setText("");
		getLblImgLastBall().setIcon(null);
		getLblImgLastBall().setVisible(false);

		updateInterface();
		translate();
	}
	public void translate() {
		window.setTitle(localizer.getLocateText("bingo.title"));

		getLblImgLastBall().setText(localizer.getLocateText("bingo.obtBall"));
		
		getBtnNewBall().setText(localizer.getLocateText("bingo.btnNewBall"));
		getBtnNewBall().setMnemonic(localizer.getLocateChar("bingo.btnNewBall.mn"));
		updatelblNBallsLeft();
		
		((TitledBorder) getPnActions().getBorder()).setTitle(localizer.getLocateText("bingo.pnActions"));
		getBtnLine().setText(localizer.getLocateText("bingo.btnLine"));
		getBtnLine().setMnemonic(localizer.getLocateChar("bingo.btnLine.mn"));
		getBtnBingo().setText(localizer.getLocateText("bingo.btnBingo"));
		getBtnBingo().setMnemonic(localizer.getLocateChar("bingo.btnBingo.mn"));
		
		getBtnContinue().setText(localizer.getLocateText("global.btnContinue"));
		getBtnContinue().setMnemonic(localizer.getLocateChar("global.btnContinue.mn"));
		getBtnExit().setText(localizer.getLocateText("global.btnExit"));
		getBtnExit().setMnemonic(localizer.getLocateChar("global.btnExit.mn"));
		
		ImageIcon icon = FactoryImage.loadImagen(FactoryImage.IMAGE_RABBIT);
		FactoryImage.setAdaptedImgLbl(lblStar, icon.getImage());
		
		setToolTipBoxButtons();
	}
	public void updateInterface() {
		boolean isGameOver = bingo.isGameOver();

		getBtnContinue().setEnabled(isGameOver); 
		getBtnNewBall().setEnabled(!isGameOver);
		
		try {
			updatelblLastBall();
			updatelblNBallsLeft();
			setBoxIcons();
		} catch (IllegalArgumentException e) { }
	}
	
	private void fillBoard() {
		getPnBoxes().removeAll();
		getPnBoxes().validate();
		for(int i=0; i<Board.N_ROWS_BOARD; i++)
			for(int j=0; j<Board.N_COLUMNS_BOARD; j++)
				getPnBoxes().add(newBox(i, j));
	}
	private JButton newBox(int row, int col) {
		JButton box = new JButton();
		box.setBackground(Color.WHITE);
		box.addActionListener(processMarked);
		box.setActionCommand(""+getBingo().getBoard().getBox(row, col).getPosition());
		return box;
	}
	private void setBoxIcons() {
		int pos = 0;
		for(Component c: getPnBoxes().getComponents()) {
			JButton button = (JButton) c;
			ImageIcon icon = FactoryImage.getImageBox(bingo.getBoard().getBox(pos));
			FactoryImage.setAdaptedImgButton(button, icon.getImage());
			
			pos++;
		}
	}
	private void setToolTipBoxButtons() {
		int pos = 0;
		for(Component b: getPnBoxes().getComponents()) {
			String msg = localizer.getLocateText("bingo.btnBox.tt");
			int number = bingo.getBoard().getBox(pos).getNumber();
			msg = String.format(msg, number);
			((JButton) b).setToolTipText(msg);
			pos++;
		}
	}
	private JLabel getLblImgLastBall() {
		if (lblImgLastBall == null) {
			lblImgLastBall = new JLabel("Obtained Ball:");
			lblImgLastBall.setVisible(false);
			lblImgLastBall.setForeground(new Color(0, 0, 153));
			lblImgLastBall.setHorizontalTextPosition(SwingConstants.CENTER);
			lblImgLastBall.setVerticalTextPosition(SwingConstants.TOP);
			lblImgLastBall.setHorizontalAlignment(SwingConstants.CENTER);
			lblImgLastBall.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 25));
		}
		return lblImgLastBall;
	}
	private void updatelblLastBall() {
		ImageIcon icon;
		try {
			icon = FactoryImage.getImageBall(bingo.getLastBall());
			
			int delta = 50;
			JLabel lbl = getLblImgLastBall();
			int minorSide = lbl.getWidth() < lbl.getHeight() ? lbl.getWidth() : lbl.getHeight();
			Image imgEscaled = icon.getImage().getScaledInstance(minorSide-delta, minorSide-delta, Image.SCALE_FAST);
			ImageIcon iconEscaled = new ImageIcon(imgEscaled);
			lbl.setIcon(iconEscaled);
			throw new UserException();
		} catch (UserException e) {	}
	}
	private JPanel getPnBoard() {
		if (pnBoard == null) {
			pnBoard = new JPanel();
			pnBoard.setBackground(new Color(255, 51, 51));
			pnBoard.setLayout(new BorderLayout(0, 0));
			pnBoard.add(getPnBoxes(), BorderLayout.CENTER);
			pnBoard.add(getPnHead(), BorderLayout.NORTH);
		}
		return pnBoard;
	}
	private JPanel getPnBoxes() {
		if (pnBoxes == null) {
			pnBoxes = new JPanel();
			pnBoxes.setOpaque(false);
			pnBoxes.setBorder(new EmptyBorder(2, 10, 10, 10));
			pnBoxes.setBackground(new Color(51, 204, 255));
			pnBoxes.setLayout(new GridLayout(Board.N_ROWS_BOARD, Board.N_COLUMNS_BOARD, 7, 7));
		}
		return pnBoxes;
	}
	private JPanel getPnActions() {
		if (pnActions == null) {
			pnActions = new JPanel();
			pnActions.setOpaque(false);
			pnActions.setBackground(new Color(255, 0, 0));
			pnActions.setForeground(new Color(255, 51, 0));
			Font font = new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 27);
			pnActions.setBorder(new TitledBorder(null, "actions", TitledBorder.LEADING, TitledBorder.TOP, font, null));
			((TitledBorder) pnActions.getBorder()).setTitleColor(new Color(0, 0, 153));
			pnActions.setLayout(new GridLayout(0, 1, 0, 5));
			pnActions.add(getPnBtnActions());
		}
		return pnActions;
	}
	private JButton getBtnBingo() {
		if (btnBingo == null) {
			btnBingo = new JButton("Have Bingo");
			btnBingo.setBorder(new LineBorder(new Color(255, 102, 51), 2));
			btnBingo.setForeground(new Color(0, 0, 153));
			btnBingo.setBackground(new Color(255, 204, 0));
			btnBingo.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 30));
			btnBingo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						bingo.markBingo();

						ImageIcon icon = FactoryImage.loadImagen(FactoryImage.IMAGE_RABBIT_BINGO);
						FactoryImage.setAdaptedImgLbl(lblStar, icon.getImage());
						
						String msg = localizer.getLocateText("bingo.mHaveBingoMsg");
						String title = localizer.getLocateText("bingo.mHaveAward");
						showInfo(msg, title);
					} catch (UserException e) {
						String msg = localizer.getLocateText("bingo.mErrBingoMsg");
						String title = localizer.getLocateText("bingo.mErrBingoTitle");
						showInfo(msg, title);
					}
					updateInterface();
				}
			});
		}
		return btnBingo;
	}
	private JButton getBtnLine() {
		if (btnLine == null) {
			btnLine = new JButton("Have Line");
			btnLine.setBorder(new LineBorder(new Color(255, 102, 51), 2));
			btnLine.setForeground(new Color(0, 0, 153));
			btnLine.setBackground(new Color(255, 204, 0));
			btnLine.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						bingo.markLine();
						
						String imgName = localizer.getLocateText("bingo.rabbit.line");
						ImageIcon icon = FactoryImage.loadImagen(FactoryImage.IMAGE_RABBIT_PATH +imgName);
						FactoryImage.setAdaptedImgLbl(lblStar, icon.getImage());
						
						String msg = localizer.getLocateText("bingo.mHaveLineMsg");
						String title = localizer.getLocateText("bingo.mHaveAward");
						showInfo(msg, title);
					} catch (UserException e) {
						String msg = localizer.getLocateText("bingo.mErrLineMsg");
						String title = localizer.getLocateText("bingo.mErrLineTitle");
						showInfo(msg, title);
					}
					updateInterface();
				}
			});
			btnLine.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 30));
		}
		return btnLine;
	}
	private void showInfo(String msg, String title) {
		JOptionPane.showMessageDialog(window, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}
	private JButton getBtnNewBall() {
		if (btnNewBall == null) {
			btnNewBall = new JButton("New Ball");
			btnNewBall.setBackground(new Color(0, 153, 0));
			btnNewBall.setForeground(new Color(0, 0, 153));
			btnNewBall.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					bingo.getNewBall();

					getLblImgLastBall().setVisible(true);
					updateInterface();

					ImageIcon icon = FactoryImage.loadImagen(FactoryImage.IMAGE_RABBIT);
					FactoryImage.setAdaptedImgLbl(lblStar, icon.getImage());
				}
			});
			btnNewBall.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 29));
		}
		return btnNewBall;
	}
	private JButton getBtnContinue() {
		if (btnContinue == null) {
			btnContinue = new JButton("continue");
			btnContinue.setForeground(new Color(255, 255, 255));
			btnContinue.setBackground(new Color(0, 153, 0));
			btnContinue.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 27));
			btnContinue.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(bingo.hasWonSomething())
						window.showPnReward();
					else
						window.backHome(true);
				}
			});
		}
		return btnContinue;
	}
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("exit");
			btnExit.setBackground(new Color(255, 0, 0));
			btnExit.setForeground(new Color(255, 255, 255));
			btnExit.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 27));
			btnExit.addActionListener(window.getProcessExit());
		}
		return btnExit;
	}
	private JPanel getPnObtainedBall() {
		if (pnObtainedBall == null) {
			pnObtainedBall = new JPanel();
			pnObtainedBall.setOpaque(false);
			pnObtainedBall.setLayout(new BorderLayout(0, 0));
			pnObtainedBall.add(getLblImgLastBall());
		}
		return pnObtainedBall;
	}
	private JPanel getPnBtnNavigation() {
		if (pnBtnNavigation == null) {
			pnBtnNavigation = new JPanel();
			pnBtnNavigation.setOpaque(false);
			pnBtnNavigation.setBorder(new EmptyBorder(15, 30, 15, 30));
			pnBtnNavigation.setLayout(new GridLayout(0, 2, 30, 0));
			pnBtnNavigation.add(getBtnContinue());
			pnBtnNavigation.add(getBtnExit());
		}
		return pnBtnNavigation;
	}
	private JPanel getPnCentral() {
		if (pnCentral == null) {
			pnCentral = new JPanel();
			pnCentral.setOpaque(false);
			pnCentral.setLayout(new GridLayout(0, 2, 10, 0));
			pnCentral.add(getPnBoard());
			pnCentral.add(getPnInteraction());
		}
		return pnCentral;
	}
	private JPanel getPnInteraction() {
		if (pnInteraction == null) {
			pnInteraction = new JPanel();
			pnInteraction.setOpaque(false);
			pnInteraction.setLayout(new BorderLayout(0, 0));
			pnInteraction.add(getPnBtnNavigation(), BorderLayout.SOUTH);
			pnInteraction.add(getPanel_1_1());
		}
		return pnInteraction;
	}
	private JPanel getPnBtnActions() {
		if (pnBtnActions == null) {
			pnBtnActions = new JPanel();
			pnBtnActions.setOpaque(false);
			pnBtnActions.setBackground(new Color(255, 0, 0));
			pnBtnActions.setBorder(new EmptyBorder(15, 30, 15, 30));
			pnBtnActions.setLayout(new GridLayout(2, 1, 0, 7));
			pnBtnActions.add(getBtnLine());
			pnBtnActions.add(getBtnBingo());
		}
		return pnBtnActions;
	}
	private JPanel getPanel_1() {
		if (pnBallContents == null) {
			pnBallContents = new JPanel();
			pnBallContents.setOpaque(false);
			pnBallContents.setLayout(new GridLayout(1, 2, 0, 0));
			pnBallContents.add(getPnObtainedBall());
			pnBallContents.add(getPnNewBall());
		}
		return pnBallContents;
	}
	private JPanel getPnHead() {
		if (pnHead == null) {
			pnHead = new JPanel();
			pnHead.setOpaque(false);
			pnHead.setLayout(new GridLayout(0, 3, 0, 0));
			pnHead.add(getPnHead_1());
			pnHead.add(getPnHead_2());
			pnHead.add(getPnHead_3());
		}
		return pnHead;
	}
	private JPanel getPnHead_1() {
		if (pnHead_1 == null) {
			pnHead_1 = new JPanel();
			pnHead_1.setOpaque(false);
			pnHead_1.setLayout(new GridLayout(0, 1, 0, 0));
			pnHead_1.add(getLblHead_1());
		}
		return pnHead_1;
	}
	private JPanel getPnHead_2() {
		if (pnHead_2 == null) {
			pnHead_2 = new JPanel();
			pnHead_2.setOpaque(false);
			pnHead_2.setLayout(new GridLayout(1, 0, 0, 0));
			pnHead_2.add(getLblHead_2());
		}
		return pnHead_2;
	}
	private JPanel getPnHead_3() {
		if (pnHead_3 == null) {
			pnHead_3 = new JPanel();
			pnHead_3.setOpaque(false);
			pnHead_3.setLayout(new GridLayout(1, 0, 0, 0));
			pnHead_3.add(getLblHead_3());
		}
		return pnHead_3;
	}
	private JLabel getLblHead_1() {
		if (lblHead_1 == null) {
			lblHead_1 = new JLabel("E");
			lblHead_1.setBorder(new EmptyBorder(5, 0, 0, 0));
			lblHead_1.setForeground(new Color(255, 255, 255));
			lblHead_1.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 28));
			lblHead_1.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblHead_1;
	}
	private JLabel getLblHead_2() {
		if (lblHead_2 == null) {
			lblHead_2 = new JLabel("I");
			lblHead_2.setBorder(new EmptyBorder(5, 0, 0, 0));
			lblHead_2.setForeground(new Color(255, 255, 255));
			lblHead_2.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 28));
			lblHead_2.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblHead_2;
	}
	private JLabel getLblHead_3() {
		if (lblHead_3 == null) {
			lblHead_3 = new JLabel("I");
			lblHead_3.setBorder(new EmptyBorder(5, 0, 0, 0));
			lblHead_3.setForeground(new Color(255, 255, 255));
			lblHead_3.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 28));
			lblHead_3.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblHead_3;
	}
	private JPanel getPanel_1_1() {
		if (pnIntContents == null) {
			pnIntContents = new JPanel();
			pnIntContents.setOpaque(false);
			pnIntContents.setLayout(new GridLayout(0, 1, 0, 0));
			pnIntContents.add(getPanel_1());
			pnIntContents.add(getPnActions1());
		}
		return pnIntContents;
	}
	private JLabel getLblStar() {
		if (lblStar == null) {
			lblStar = new JLabel("");
			lblStar.setHorizontalAlignment(SwingConstants.CENTER);
			lblStar.setFont(new Font("Tahoma", Font.PLAIN, 29));
		}
		return lblStar;
	}
	private JPanel getPnNewBall() {
		if (pnNewBall == null) {
			pnNewBall = new JPanel();
			pnNewBall.setBorder(new EmptyBorder(0, 0, 0, 30));
			pnNewBall.setOpaque(false);
			pnNewBall.setLayout(new BorderLayout(0, 0));
			pnNewBall.add(getLblStar());
			pnNewBall.add(getBtnNewBall(), BorderLayout.SOUTH);
		}
		return pnNewBall;
	}
	private JPanel getPnActions1() {
		if (pnActions1 == null) {
			pnActions1 = new JPanel();
			pnActions1.setOpaque(false);
			pnActions1.setBorder(new EmptyBorder(0, 30, 0, 30));
			pnActions1.setLayout(new BorderLayout(0, 5));
			pnActions1.add(getPnActions());
			pnActions1.add(getLblNBallsLeft(), BorderLayout.NORTH);
		}
		return pnActions1;
	}
	private JLabel getLblNBallsLeft() {
		if (lblNBallsLeft == null) {
			lblNBallsLeft = new JLabel("New label");
			lblNBallsLeft.setHorizontalAlignment(SwingConstants.LEFT);
			lblNBallsLeft.setForeground(new Color(255, 255, 255));
			lblNBallsLeft.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 28));
		}
		return lblNBallsLeft;
	}
	private void updatelblNBallsLeft() {
		String msg = localizer.getLocateText("bingo.ballsLeft");
		msg = String.format(msg, bingo.getBallsLeft());
		getLblNBallsLeft().setText(msg);
	}
}
