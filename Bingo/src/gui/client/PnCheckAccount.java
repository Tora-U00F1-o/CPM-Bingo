package gui.client;

import javax.swing.JPanel;

import logic.clients.ID;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;

import events.ProcessTxtFieldEmpty;
import events.ProcessTxtFieldEnter;
import gui.MainWindow;
import gui.utils.BingoPanel;
import gui.utils.Localizer;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

public class PnCheckAccount extends JPanel implements BingoPanel{
	
	private static final long serialVersionUID = 1L;
	
	private MainWindow window;
	private Localizer localizer;
	
	private JPanel pnHeader;
	private JLabel lblHead;
	private JPanel pnIDs;
	private JPanel pnIDsL;
	private JTextField txtIDs;
	private JPanel pnIDsR;
	private JComboBox<String> cbIDs;
	private JPanel pnBtn;
	private JPanel pnBtnLeft;
	private JButton btnCheck;
	private JPanel pnBtnRight;
	private JButton btnBack;
	private JLabel lblTxtEmpty;
	private JPanel pnGhost;
	private JPanel pnContents;

	/**
	 * Create the panel.
	 */
	public PnCheckAccount(MainWindow window, Localizer localizer) {
		setBackground(new Color(51, 204, 255));
		this.window = window;
		this.localizer = localizer;
		setLayout(new BorderLayout(0, 0));
		add(getPnContents());
		add(getPnBtn(), BorderLayout.SOUTH);

	}
	public void setDefaultConfig() {
		getTxtIDs().requestFocus();
		getTxtIDs().setText("");
		getCbIDs().setSelectedIndex(0);
		getLblTxtEmpty().setVisible(false);
		translate();
	}
	public void translate() {
		window.setTitle(localizer.getLocateText("ca.title"));
		getLblHead().setText(localizer.getLocateText("ca.textHead.dni"));
		getLblHead().setDisplayedMnemonic(localizer.getLocateChar("ca.textHead.mn"));
		getLblHead().setDisplayedMnemonicIndex(0);
		getLblTxtEmpty().setText(localizer.getLocateText("pte.lblError"));
		getCbIDs().setModel(new DefaultComboBoxModel<String>(getModeloIDs()));
		getBtnBack().setText(localizer.getLocateText("global.btnBack"));
		getBtnBack().setMnemonic(localizer.getLocateChar("global.btnBack.mn"));
		getBtnCheck().setText(localizer.getLocateText("ca.btnCheck"));
		getBtnCheck().setMnemonic(localizer.getLocateChar("ca.btnCheck.mn"));;
	}
	
	private JPanel getPnHeader() {
		if (pnHeader == null) {
			pnHeader = new JPanel();
			pnHeader.setBorder(new EmptyBorder(0, 100, 0, 0));
			pnHeader.setOpaque(false);
			pnHeader.setLayout(new GridLayout(0, 1, 0, 0));
			pnHeader.add(getLblHead());
		}
		return pnHeader;
	}
	
	private JLabel getLblHead() {
		if (lblHead == null) {
			lblHead = new JLabel();
			lblHead.setVerticalAlignment(SwingConstants.BOTTOM);
			lblHead.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 34));
			lblHead.setForeground(new Color(0, 0, 153));
			lblHead.setLabelFor(getTxtIDs());
			lblHead.setHorizontalAlignment(SwingConstants.LEFT);
			lblHead.setHorizontalTextPosition(SwingConstants.CENTER);
			lblHead.setText("Enter DNI number");
		}
		return lblHead;
	}
	private JPanel getPnIDs() {
		if (pnIDs == null) {
			pnIDs = new JPanel();
			pnIDs.setOpaque(false);
			pnIDs.setLayout(new GridLayout(0, 2, 0, 0));
			pnIDs.add(getPnIDsL());
			pnIDs.add(getPnIDsR());
		}
		return pnIDs;
	}
	
	private JPanel getPnIDsL() {
		if (pnIDsL == null) {
			pnIDsL = new JPanel();
			pnIDsL.setOpaque(false);
			GroupLayout gl_pnIDsL = new GroupLayout(pnIDsL);
			gl_pnIDsL.setHorizontalGroup(
				gl_pnIDsL.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnIDsL.createSequentialGroup()
						.addGap(129)
						.addComponent(getTxtIDs(), GroupLayout.DEFAULT_SIZE, 6, Short.MAX_VALUE)
						.addGap(69))
			);
			gl_pnIDsL.setVerticalGroup(
				gl_pnIDsL.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnIDsL.createSequentialGroup()
						.addContainerGap(50, Short.MAX_VALUE)
						.addComponent(getTxtIDs(), GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
			);
			pnIDsL.setLayout(gl_pnIDsL);
		}
		return pnIDsL;
	}
	private JTextField getTxtIDs() {
		if (txtIDs == null) {
			txtIDs = new JTextField();
			txtIDs.addActionListener(new ProcessTxtFieldEnter(getBtnCheck()));
			txtIDs.addFocusListener(new ProcessTxtFieldEmpty(getLblTxtEmpty()));
			txtIDs.setFont(new Font("Tahoma", Font.PLAIN, 20));
			txtIDs.setColumns(10);
		}
		return txtIDs;
	}
	private JPanel getPnIDsR() {
		if (pnIDsR == null) {
			pnIDsR = new JPanel();
			pnIDsR.setOpaque(false);
			GroupLayout gl_pnIDsR = new GroupLayout(pnIDsR);
			gl_pnIDsR.setHorizontalGroup(
				gl_pnIDsR.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnIDsR.createSequentialGroup()
						.addGap(69)
						.addComponent(getCbIDs(), 0, 0, Short.MAX_VALUE)
						.addGap(130))
			);
			gl_pnIDsR.setVerticalGroup(
				gl_pnIDsR.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnIDsR.createSequentialGroup()
						.addContainerGap(48, Short.MAX_VALUE)
						.addComponent(getCbIDs(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
			);
			pnIDsR.setLayout(gl_pnIDsR);
		}
		return pnIDsR;
	}
	private JComboBox<String> getCbIDs() {
		if (cbIDs == null) {
			cbIDs = new JComboBox<String>();
			cbIDs.setForeground(Color.DARK_GRAY);
			cbIDs.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 20));
			cbIDs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int idIndex = cbIDs.getSelectedIndex();
					getLblHead().setText(localizer.getLocateText(
							"ca.textHead."+ID.values()[idIndex].toString().toLowerCase()));
					getLblTxtEmpty().setVisible(false);
					getTxtIDs().setText("");
					getTxtIDs().requestFocus();
				}
			});
			cbIDs.setModel(new DefaultComboBoxModel<String>(getModeloIDs()));
		}
		return cbIDs;
	}
	private String[] getModeloIDs() {
		int nIDs = ID.values().length; 
		String[] modelo = new String[nIDs];
		for(int i=0; i<nIDs; i++) 
			modelo[i] = localizer.getLocateText("global."+ID.values()[i].toString().toLowerCase());
		
		return modelo;
	}
	private JPanel getPnBtn() {
		if (pnBtn == null) {
			pnBtn = new JPanel();
			pnBtn.setBackground(SystemColor.menu);
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
					.addGroup(gl_pnBtnLeft.createSequentialGroup()
						.addGap(128)
						.addComponent(getBtnCheck(), GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
						.addGap(70))
			);
			gl_pnBtnLeft.setVerticalGroup(
				gl_pnBtnLeft.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnBtnLeft.createSequentialGroup()
						.addContainerGap()
						.addComponent(getBtnCheck(), GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addGap(22))
			);
			pnBtnLeft.setLayout(gl_pnBtnLeft);
		}
		return pnBtnLeft;
	}
	private JButton getBtnCheck() {
		if (btnCheck == null) {
			btnCheck = new JButton("Check");
			btnCheck.setForeground(Color.DARK_GRAY);
			btnCheck.setBackground(new Color(255, 204, 0));
			btnCheck.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32));
			btnCheck.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(getTxtIDs().getText().trim().isEmpty()) {
						getTxtIDs().requestFocus();
						getLblTxtEmpty().setVisible(true);
						return; 
					}
					window.showAmountClientDialog(getTxtIDs().getText().trim(), false);
					getTxtIDs().setText("");
					getTxtIDs().requestFocus();
				}
			});
		}
		return btnCheck;
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
					.addGroup(gl_pnBtnRight.createSequentialGroup()
						.addContainerGap()
						.addComponent(getBtnBack(), GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addGap(22))
			);
			pnBtnRight.setLayout(gl_pnBtnRight);
		}
		return pnBtnRight;
	}
	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("Back");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					window.returnFromCAccount();
				}
			});
			btnBack.setForeground(Color.DARK_GRAY);
			btnBack.setBackground(new Color(255, 204, 0));
			btnBack.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32));
		}
		return btnBack;
	}
	private JLabel getLblTxtEmpty() {
		if (lblTxtEmpty == null) {
			lblTxtEmpty = new JLabel("text field empty");
			lblTxtEmpty.setBounds(133, 6, 674, 25);
			lblTxtEmpty.setVisible(false);
			lblTxtEmpty.setForeground(new Color(255, 51, 102));
			lblTxtEmpty.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return lblTxtEmpty;
	}
	private JPanel getPnGhost() {
		if (pnGhost == null) {
			pnGhost = new JPanel();
			pnGhost.setOpaque(false);
			pnGhost.setLayout(null);
			pnGhost.add(getLblTxtEmpty());
		}
		return pnGhost;
	}
	private JPanel getPnContents() {
		if (pnContents == null) {
			pnContents = new JPanel();
			pnContents.setOpaque(false);
			pnContents.setLayout(new GridLayout(3, 1, 0, 0));
			pnContents.add(getPnHeader());
			pnContents.add(getPnIDs());
			pnContents.add(getPnGhost());
		}
		return pnContents;
	}
}
