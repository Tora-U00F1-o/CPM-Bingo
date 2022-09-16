package gui.ticket;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import events.ProcessBackHome;
import events.ProcessTxtFieldEmpty;
import events.ProcessTxtFieldEnter;
import gui.MainWindow;
import gui.utils.BingoPanel;
import gui.utils.Localizer;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import logic.Config;
import logic.tickets.TicketManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

public class PnTicketValidator extends JPanel implements BingoPanel {
	
	private static final long serialVersionUID = 1L;
	
	private MainWindow window;
	private TicketManager manager;
	private Localizer localizer;
	
	private JLabel lblHead;
	private JTextField txtTicket;
	private JPanel pnBtn;
	private JPanel pnBtnLeft;
	private JButton btnPlay;
	private JPanel pnBtnRight;
	private JButton btnBack;
	private JPanel pnTicket;
	private JLabel lblTxtEmpty;
	private JPanel panel;
	private JPanel pnContents;

	/**
	 * Create the panel.
	 */
	public PnTicketValidator(MainWindow window, Localizer localizer) {
		this.window = window;
		this.localizer = localizer;
		this.manager = window.getTicketManager();
		setLayout(new BorderLayout(0, 0));
		add(getPnContents());
		add(getPnBtn(), BorderLayout.SOUTH);

	}
	
	public void setDefaultConfig() {
		manager.inicialize();
		
		getTxtTicket().requestFocus();
		getTxtTicket().setText("");
		getLblTxtEmpty().setVisible(false);
		translate();
	}
	
	public void translate() {
		window.setTitle(localizer.getLocateText("tv.title"));
		getLblHead().setText(localizer.getLocateText("tv.head"));
		getLblHead().setDisplayedMnemonic(localizer.getLocateChar("tv.head.mn"));
		getLblTxtEmpty().setText(localizer.getLocateText("pte.lblError"));
		getBtnPlay().setText(localizer.getLocateText("global.btnPlay"));
		getBtnPlay().setMnemonic(localizer.getLocateChar("global.btnPlay.mn"));
		getBtnBack().setText(localizer.getLocateText("global.btnBack"));
		getBtnBack().setMnemonic(localizer.getLocateChar("global.btnBack.mn"));
	}

	private JLabel getLblHead() {
		if (lblHead == null) {
			lblHead = new JLabel("Enter Ticket nº EAN");
			lblHead.setForeground(new Color(0, 0, 153));
			lblHead.setVerticalAlignment(SwingConstants.BOTTOM);
			lblHead.setLabelFor(getTxtTicket());
			lblHead.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 34));
		}
		return lblHead;
	}
	private JTextField getTxtTicket() {
		if (txtTicket == null) {
			txtTicket = new JTextField();
			txtTicket.setFont(new Font("Tahoma", Font.PLAIN, 20));
			txtTicket.setColumns(10);
			txtTicket.addActionListener(new ProcessTxtFieldEnter(getBtnPlay()));
			txtTicket.addFocusListener(new ProcessTxtFieldEmpty(getLblTxtEmpty()));
		}
		return txtTicket;
	}
	private JPanel getPnBtn() {
		if (pnBtn == null) {
			pnBtn = new JPanel();
			pnBtn.setOpaque(false);
			pnBtn.setLayout(new GridLayout(0, 2, 0, 0));
			pnBtn.add(getPnBtnLeft());
			pnBtn.add(getPnBtnRight());
		}
		return pnBtn;
	}
	private JPanel getPnBtnLeft() {
		if (pnBtnLeft == null) {
			pnBtnLeft = new JPanel();
			pnBtnLeft.setOpaque(false);
			GroupLayout gl_pnBtnLeft = new GroupLayout(pnBtnLeft);
			gl_pnBtnLeft.setHorizontalGroup(
				gl_pnBtnLeft.createParallelGroup(Alignment.TRAILING)
					.addGroup(Alignment.LEADING, gl_pnBtnLeft.createSequentialGroup()
						.addGap(128)
						.addComponent(getBtnPlay(), GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
						.addGap(70))
			);
			gl_pnBtnLeft.setVerticalGroup(
				gl_pnBtnLeft.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_pnBtnLeft.createSequentialGroup()
						.addGap(25)
						.addComponent(getBtnPlay(), GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addGap(22))
			);
			pnBtnLeft.setLayout(gl_pnBtnLeft);
		}
		return pnBtnLeft;
	}
	private JButton getBtnPlay() {
		if (btnPlay == null) {
			btnPlay = new JButton("play");
			btnPlay.setBackground(new Color(255, 204, 0));
			btnPlay.setForeground(Color.DARK_GRAY);
			btnPlay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(getTxtTicket().getText().trim().isEmpty()) {
						getTxtTicket().requestFocus();
						getLblTxtEmpty().setVisible(true);
						return; 
					}
					checkTicket();
				}
			});
			btnPlay.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32));
		}
		return btnPlay;
	}
	private void checkTicket() {
		String ticket = getTxtTicket().getText();
		
		if(!manager.isTicketValid(ticket)) {
			getTxtTicket().setText("");
			String msg = String.format(localizer.getLocateText("tv.msgInvalid"), localizer.getLocateAmout(Config.TICKET_MIN_AMOUNT));
			String title = localizer.getLocateText("tv.titleInvalid");
			JOptionPane.showMessageDialog(window, msg, title, JOptionPane.ERROR_MESSAGE);
			getTxtTicket().requestFocus();
			return;
		}
		window.showPnBingo();
	}
	private JPanel getPnBtnRight() {
		if (pnBtnRight == null) {
			pnBtnRight = new JPanel();
			pnBtnRight.setOpaque(false);
			GroupLayout gl_pnBtnRight = new GroupLayout(pnBtnRight);
			gl_pnBtnRight.setHorizontalGroup(
				gl_pnBtnRight.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnBtnRight.createSequentialGroup()
						.addGap(70)
						.addComponent(getBtnBack(), GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
						.addGap(128))
			);
			gl_pnBtnRight.setVerticalGroup(
				gl_pnBtnRight.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_pnBtnRight.createSequentialGroup()
						.addGap(25)
						.addComponent(getBtnBack(), GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addGap(22))
			);
			pnBtnRight.setLayout(gl_pnBtnRight);
		}
		return pnBtnRight;
	}
	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("back");
			btnBack.setBackground(new Color(255, 204, 0));
			btnBack.setForeground(Color.DARK_GRAY);
			btnBack.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32));
			btnBack.addActionListener(new ProcessBackHome(window, null, false));
		}
		return btnBack;
	}
	private JPanel getPnTicket() {
		if (pnTicket == null) {
			pnTicket = new JPanel();
			pnTicket.setOpaque(false);
			GroupLayout gl_pnTicket = new GroupLayout(pnTicket);
			gl_pnTicket.setHorizontalGroup(
				gl_pnTicket.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnTicket.createSequentialGroup()
						.addGap(109)
						.addGroup(gl_pnTicket.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnTicket.createSequentialGroup()
								.addComponent(getLblTxtEmpty(), GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
								.addGap(213))
							.addGroup(gl_pnTicket.createSequentialGroup()
								.addComponent(getTxtTicket(), GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
								.addGap(344))))
			);
			gl_pnTicket.setVerticalGroup(
				gl_pnTicket.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnTicket.createSequentialGroup()
						.addGap(41)
						.addComponent(getTxtTicket(), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(getLblTxtEmpty(), GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			pnTicket.setLayout(gl_pnTicket);
		}
		return pnTicket;
	}
	private JLabel getLblTxtEmpty() {
		if (lblTxtEmpty == null) {
			lblTxtEmpty = new JLabel("text field empty");
			lblTxtEmpty.setVisible(false);
			lblTxtEmpty.setForeground(new Color(220, 20, 60));
			lblTxtEmpty.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return lblTxtEmpty;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setForeground(new Color(0, 0, 153));
			panel.setOpaque(false);
			panel.setBorder(new EmptyBorder(0, 95, 0, 0));
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			panel.add(getLblHead());
		}
		return panel;
	}
	private JPanel getPnContents() {
		if (pnContents == null) {
			pnContents = new JPanel();
			pnContents.setOpaque(false);
			pnContents.setLayout(new GridLayout(2, 1, 0, 0));
			pnContents.add(getPanel());
			pnContents.add(getPnTicket());
		}
		return pnContents;
	}
}
