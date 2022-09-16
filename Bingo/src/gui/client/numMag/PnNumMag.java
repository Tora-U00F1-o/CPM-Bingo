package gui.client.numMag;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import events.ProcessTxtFieldEmpty;
import events.ProcessTxtFieldEnter;
import gui.MainWindow;
import gui.utils.BingoPanel;
import gui.utils.Localizer;
import logic.Config;
import logic.clients.ClientManager;
import logic.clients.ID;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;

public class PnNumMag extends JPanel implements BingoPanel {
	
	private static final long serialVersionUID = 1L;
	
	private MainWindow window;
	private Localizer localizer;
	private ClientManager manager;
	
	private JLabel lblHead;
	private JPanel pnBtnNavigation;
	private JButton btnSave;
	private JButton btnExit;
	private JPanel pnCentral;
	private JPanel pnHead;
	private JTextField txtIDs;
	private JComboBox<String> cbIDs;
	private JLabel lblTxtEmpty;
	private JPanel pnContents;
	private JPanel pnNavL;
	private JPanel pnNavR;
	private JLabel lblHead2;

	/**
	 * Create the panel.
	 */
	public PnNumMag(MainWindow window, Localizer localizer, ClientManager manager) {
		setOpaque(false);
		this.window = window;
		this.localizer = localizer;
		this.manager = manager;
		setLayout(new BorderLayout(0, 0));
		add(getPnBtnNavigation(), BorderLayout.SOUTH);
		add(getPnContents());

	}
	public void setDefaultConfig() {
		getTxtIDs().requestFocus();
		
		getTxtIDs().setText("");
		getCbIDs().setSelectedIndex(0);
		getLblTxtEmpty().setVisible(false);
		
		translate();
	}
	public void translate() {
		window.setTitle(localizer.getLocateText("num.title"));
		getLblHead().setText(localizer.getLocateText("num.head1"));
		updatelblHead2();
		getLblHead2().setDisplayedMnemonic(localizer.getLocateChar("num.head2.mn"));;
		getLblTxtEmpty().setText(localizer.getLocateText("pte.lblError"));
		getLblTxtEmpty().setText(localizer.getLocateText("pte.lblError"));
		getBtnSave().setText(localizer.getLocateText("global.btnSave"));
		getBtnSave().setMnemonic(localizer.getLocateChar("global.btnSave.mn"));
		getBtnExit().setText(localizer.getLocateText("global.btnExit"));
		getBtnExit().setMnemonic(localizer.getLocateChar("global.btnExit.mn"));
		getCbIDs().setModel(new DefaultComboBoxModel<String>(getModeloIDs()));
	}
	
	private JLabel getLblHead() {
		if (lblHead == null) {
			lblHead = new JLabel("You have The Magic Number");
			lblHead.setForeground(new Color(0, 0, 153));
			lblHead.setVerticalAlignment(SwingConstants.BOTTOM);
			lblHead.setLabelFor(getTxtIDs());
			lblHead.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 34));
		}
		return lblHead;
	}
	private JLabel getLblHead2() {
		if (lblHead2 == null) {
			lblHead2 = new JLabel("Enter DNI number");
			lblHead2.setForeground(new Color(0, 0, 153));
			lblHead2.setVerticalAlignment(SwingConstants.BOTTOM);
			lblHead2.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 34));
		}
		return lblHead2;
	}
	private void updatelblHead2() {
		int i = getCbIDs().getSelectedIndex();
		String msg = localizer.getLocateText("num.head2");
		String typeId = localizer.getLocateText("global."+ID.values()[i].toString().toLowerCase());
		String amount = localizer.getLocateAmout(Config.COUPON_AMOUNT);
		msg = String.format(msg, typeId, amount);
		getLblHead2().setText(msg);
	}
	private JPanel getPnBtnNavigation() {
		if (pnBtnNavigation == null) {
			pnBtnNavigation = new JPanel();
			pnBtnNavigation.setOpaque(false);
			pnBtnNavigation.setLayout(new GridLayout(0, 2, 0, 0));
			pnBtnNavigation.add(getPnNavL());
			pnBtnNavigation.add(getPnNavR());
		}
		return pnBtnNavigation;
	}
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("continue");
			btnSave.setForeground(new Color(255, 255, 255));
			btnSave.setBackground(new Color(0, 153, 0));
			btnSave.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32));
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String id = getTxtIDs().getText().trim(); 
					if(id.isEmpty()) {
						getTxtIDs().requestFocus();
						getLblTxtEmpty().setVisible(true);
						return; 
					}
					if(manager.isRegistered(id)) {
						String idType = ID.values()[getCbIDs().getSelectedIndex()].toString().toLowerCase();
						if(JOptionPane.YES_OPTION == window.showDialogToSave(idType, id)) {
							manager.addCouponTo(id);
							manager.saveData();
							window.showAmountClientDialog(id, true);
							window.backHome(true);
							return;
						} else {
							setDefaultConfig();
						}
					} else 
						showPnRegister(getCbIDs().getSelectedIndex(), id);
				}
			});
		}
		return btnSave;
	}
	private void showPnRegister(int type, String id) {
		window.showPnRegister(type, id);
	}
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("exit");
			btnExit.setForeground(new Color(255, 255, 255));
			btnExit.setBackground(new Color(255, 0, 0));
			btnExit.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32));
			btnExit.addActionListener(window.getProcessExit());
		}
		return btnExit;
	}
	private JPanel getPnCentral() {
		if (pnCentral == null) {
			pnCentral = new JPanel();
			pnCentral.setOpaque(false);
			GroupLayout gl_pnCentral = new GroupLayout(pnCentral);
			gl_pnCentral.setHorizontalGroup(
				gl_pnCentral.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnCentral.createSequentialGroup()
						.addGap(130)
						.addGroup(gl_pnCentral.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnCentral.createSequentialGroup()
								.addComponent(getLblTxtEmpty(), GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
								.addGap(234))
							.addGroup(gl_pnCentral.createSequentialGroup()
								.addComponent(getTxtIDs(), GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
								.addGap(143)
								.addComponent(getCbIDs(), 0, 253, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)))
						.addGap(129))
			);
			gl_pnCentral.setVerticalGroup(
				gl_pnCentral.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnCentral.createSequentialGroup()
						.addGap(22)
						.addGroup(gl_pnCentral.createParallelGroup(Alignment.BASELINE)
							.addComponent(getTxtIDs(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
							.addComponent(getCbIDs(), GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addComponent(getLblTxtEmpty())
						.addContainerGap(48, Short.MAX_VALUE))
			);
			pnCentral.setLayout(gl_pnCentral);
		}
		return pnCentral;
	}
	private JPanel getPnHead() {
		if (pnHead == null) {
			pnHead = new JPanel();
			pnHead.setOpaque(false);
			pnHead.setBorder(new EmptyBorder(0, 100, 0, 0));
			pnHead.setLayout(new GridLayout(0, 1, 0, 0));
			pnHead.add(getLblHead());
			pnHead.add(getLblHead2());
		}
		return pnHead;
	}
	private JTextField getTxtIDs() {
		if (txtIDs == null) {
			txtIDs = new JTextField();
			txtIDs.addFocusListener(new ProcessTxtFieldEmpty(getLblTxtEmpty()));
			txtIDs.addActionListener(new ProcessTxtFieldEnter(getBtnSave()));
			txtIDs.setFont(new Font("Tahoma", Font.PLAIN, 20));
			txtIDs.setColumns(10);
		}
		return txtIDs;
	}
	private JComboBox<String> getCbIDs() {
		
		if (cbIDs == null) {
			cbIDs = new JComboBox<String>();
			cbIDs.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 20));
			cbIDs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					updatelblHead2();
					getTxtIDs().setText("");
					getTxtIDs().requestFocus();
					getLblTxtEmpty().setVisible(false);
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
	private JLabel getLblTxtEmpty() {
		if (lblTxtEmpty == null) {
			lblTxtEmpty = new JLabel("text field empty");
			lblTxtEmpty.setVisible(false);
			lblTxtEmpty.setForeground(new Color(220, 20, 60));
			lblTxtEmpty.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return lblTxtEmpty;
	}
	private JPanel getPnContents() {
		if (pnContents == null) {
			pnContents = new JPanel();
			pnContents.setOpaque(false);
			pnContents.setLayout(new GridLayout(0, 1, 0, 0));
			pnContents.add(getPnHead());
			pnContents.add(getPnCentral());
		}
		return pnContents;
	}
	private JPanel getPnNavL() {
		if (pnNavL == null) {
			pnNavL = new JPanel();
			pnNavL.setOpaque(false);
			GroupLayout gl_pnNavL = new GroupLayout(pnNavL);
			gl_pnNavL.setHorizontalGroup(
				gl_pnNavL.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnNavL.createSequentialGroup()
						.addGap(128)
						.addComponent(getBtnSave(), GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
						.addGap(70))
			);
			gl_pnNavL.setVerticalGroup(
				gl_pnNavL.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnNavL.createSequentialGroup()
						.addContainerGap()
						.addComponent(getBtnSave(), GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addGap(22))
			);
			pnNavL.setLayout(gl_pnNavL);
		}
		return pnNavL;
	}
	private JPanel getPnNavR() {
		if (pnNavR == null) {
			pnNavR = new JPanel();
			pnNavR.setOpaque(false);
			GroupLayout gl_pnNavR = new GroupLayout(pnNavR);
			gl_pnNavR.setHorizontalGroup(
				gl_pnNavR.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnNavR.createSequentialGroup()
						.addGap(70)
						.addComponent(getBtnExit(), GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
						.addGap(128))
			);
			gl_pnNavR.setVerticalGroup(
				gl_pnNavR.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnNavR.createSequentialGroup()
						.addContainerGap()
						.addComponent(getBtnExit(), GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addGap(22))
			);
			pnNavR.setLayout(gl_pnNavR);
		}
		return pnNavR;
	}
	
}
