package gui.client.numMag;

import javax.swing.JPanel;

import gui.MainWindow;
import gui.utils.BingoPanel;
import gui.utils.Localizer;
import logic.Config;
import logic.clients.ClientManager;
import logic.clients.ID;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import java.awt.Component;

public class PnRegister extends JPanel implements BingoPanel{

	private static final long serialVersionUID = 1L;
	
	private MainWindow window;
	private Localizer localizer;
	private ClientManager manager;
	
	private JLabel lblHead;
	private JPanel pnNavigation;
	private JButton btnSave;
	private JButton btnBack;
	private JPanel pnNavSouth;
	private JPanel pnCliente;
	private JPanel panel_1;
	private JLabel lblId;
	private JLabel lblName;
	private JLabel lblSurname;
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtSurname1;
	private JComboBox<String> cbIDs;
	private JLabel lblTxtEmpty;
	private JPanel pnNavL;
	private JPanel pnNavR;
	
	/**
	 * Create the panel.
	 */
	public PnRegister(MainWindow window, Localizer localizer, ClientManager manager) {
		this.window = window;
		this.localizer = localizer;
		this.manager = manager;
		
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(0, 0));
		add(getPnNavigation(), BorderLayout.SOUTH);
		add(getPnCliente(), BorderLayout.CENTER);
	}

	
	public void setDefaultConfig(int type,String id) {	
		getTxtName().requestFocus();
		
		getTxtName().setText("");
		getTxtSurname().setText("");
		getLblTxtEmpty().setVisible(false);
		translate();
		
		getCbIDs().setSelectedIndex(type);	
		getTxtId().setText(id);
	}
	public void translate() {
		window.setTitle(localizer.getLocateText("reg.title"));
		updatelblHead();
		getCbIDs().setModel(new DefaultComboBoxModel<String>(getModeloIDs()));
		updateLblId();
		getLblName().setText(localizer.getLocateText("reg.name")+": (*)");
		getLblName().setDisplayedMnemonic(localizer.getLocateChar("reg.name.mn"));
		getLblSurname().setText(localizer.getLocateText("reg.surn")+": (*)");
		getLblSurname().setDisplayedMnemonic(localizer.getLocateChar("reg.surn.mn"));
		getLblTxtEmpty().setText(localizer.getLocateText("pte.lblError"));
		getBtnSave().setText(localizer.getLocateText("global.btnSave"));
		getBtnSave().setMnemonic(localizer.getLocateChar("global.btnSave.mn"));
		getBtnBack().setText(localizer.getLocateText("global.btnBack"));
		getBtnBack().setMnemonic(localizer.getLocateChar("global.btnBack.mn"));
	}
	private JLabel getLblHead() {
		if (lblHead == null) {
			lblHead = new JLabel("Sing up to get it");
			lblHead.setForeground(new Color(0, 0, 153));
			lblHead.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 34));
		}
		return lblHead;
	}
	private void updatelblHead() {
		String msg = localizer.getLocateText("reg.head");
		String amount = localizer.getLocateAmout(Config.COUPON_AMOUNT);
		msg = String.format(msg, amount);
		getLblHead().setText(msg);
	}
	private JPanel getPnNavigation() {
		if (pnNavigation == null) {
			pnNavigation = new JPanel();
			pnNavigation.setOpaque(false);
			pnNavigation.setLayout(new GridLayout(0, 1, 0, 0));
			pnNavigation.add(getPnNavSouth());
		}
		return pnNavigation;
	}
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("continue");
			btnSave.setForeground(new Color(255, 255, 255));
			btnSave.setBackground(new Color(0, 153, 0));
			btnSave.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32));
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(isAnyTxtEmpty())
						return; 
					
					String id = getTxtId().getText().trim();
					String idType = ID.values()[getCbIDs().getSelectedIndex()].toString().toLowerCase();
					if(JOptionPane.YES_OPTION == window.showDialogToSave(idType, id)) {
						if(!manager.isRegistered(id))
							addNewClient();
						manager.addCouponTo(id);
						manager.saveData();
						window.showAmountClientDialog(id, true);
						window.backHome(true);
						return;
					} else {
						setDefaultConfig(0, "");
					}
				}
			});
		}
		return btnSave;
	}
	private boolean isAnyTxtEmpty() {
		if(getTxtId().getText().trim().isEmpty()) {
			getTxtId().requestFocus();
			getLblTxtEmpty().setVisible(true);
			return true; 
		} else if(getTxtName().getText().trim().isEmpty()) {
			getTxtName().requestFocus();
			getLblTxtEmpty().setVisible(true);
			return true; 
		}else if(getTxtSurname().getText().trim().isEmpty()) {
			getTxtSurname().requestFocus();
			getLblTxtEmpty().setVisible(true);
			return true; 
		}
		return false;
	}
	private void addNewClient() {
		String idDocument = getTxtId().getText().trim(),
			   name = getTxtName().getText().trim(),
			   surnames = getTxtSurname().getText().trim();
		
		manager.addClient(idDocument, name, surnames, 0);
	}
	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("back");
			btnBack.setForeground(new Color(255, 255, 255));
			btnBack.setBackground(new Color(255, 0, 0));
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					window.showPnNumMag();
				}
			});
			btnBack.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32));
		}
		return btnBack;
	}
	private JPanel getPnNavSouth() {
		if (pnNavSouth == null) {
			pnNavSouth = new JPanel();
			pnNavSouth.setOpaque(false);
			pnNavSouth.setLayout(new GridLayout(0, 2, 0, 0));
			pnNavSouth.add(getPnNavL());
			pnNavSouth.add(getPnNavR());
		}
		return pnNavSouth;
	}
	private JPanel getPnCliente() {
		if (pnCliente == null) {
			pnCliente = new JPanel();
			pnCliente.setOpaque(false);
			pnCliente.setBorder(null);
			pnCliente.setBackground(SystemColor.menu);
			pnCliente.setLayout(new BorderLayout(0, 0));
			pnCliente.add(getPanel_1(), BorderLayout.CENTER);
			pnCliente.add(getLblHead(), BorderLayout.NORTH);
		}
		return pnCliente;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setOpaque(false);
			GroupLayout gl_panel_1 = new GroupLayout(panel_1);
			gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(183)
								.addComponent(getLblTxtEmpty(), GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
									.addComponent(getLblName(), GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
									.addComponent(getLblSurname(), GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_panel_1.createSequentialGroup()
										.addComponent(getCbIDs(), GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
										.addGap(35)
										.addComponent(getLblId(), GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
									.addComponent(getTxtSurname())
									.addComponent(getTxtName())
									.addComponent(getTxtId(), GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))))
						.addGap(80))
			);
			gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(49)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addComponent(getCbIDs(), GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(getLblId(), GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(getLblName(), GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(getLblSurname(), GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(getTxtId(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(getTxtName(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(getTxtSurname(), GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(getLblTxtEmpty(), GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(46, Short.MAX_VALUE))
			);
			gl_panel_1.linkSize(SwingConstants.VERTICAL, new Component[] {getTxtName(), getTxtSurname(), getTxtId()});
			panel_1.setLayout(gl_panel_1);
		}
		return panel_1;
	}
	private JLabel getLblId() {
		if (lblId == null) {
			lblId = new JLabel("id");
			lblId.setLabelFor(getTxtId());
			lblId.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 23));
		}
		return lblId;
	}
	private void updateLblId() {
		int i = getCbIDs().getSelectedIndex();
		String msg = localizer.getLocateText("global."+ID.values()[i].toString().toLowerCase())+": (*)";
		getLblId().setDisplayedMnemonic(localizer.getLocateChar("global."+ID.values()[i].toString().toLowerCase()+".mn"));
		getLblId().setText(msg);
	}
	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("name");
			lblName.setLabelFor(getTxtName());
			lblName.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 23));
		}
		return lblName;
	}
	private JLabel getLblSurname() {
		if (lblSurname == null) {
			lblSurname = new JLabel("surname1");
			lblSurname.setLabelFor(getTxtSurname());
			lblSurname.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 23));
		}
		return lblSurname;
	}
	private JTextField getTxtId() {
		if (txtId == null) {
			txtId = new JTextField();
			txtId.setFont(new Font("Tahoma", Font.PLAIN, 20));
			txtId.setColumns(10);
		}
		return txtId;
	}
	private JTextField getTxtName() {
		if (txtName == null) {
			txtName = new JTextField();
			txtName.setFont(new Font("Tahoma", Font.PLAIN, 20));
			txtName.setText("");
			txtName.setColumns(10);
		}
		return txtName;
	}
	private JTextField getTxtSurname() {
		if (txtSurname1 == null) {
			txtSurname1 = new JTextField();
			txtSurname1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			txtSurname1.setColumns(10);
		}
		return txtSurname1;
	}
	private JComboBox<String> getCbIDs() {
		if (cbIDs == null) {
			cbIDs = new JComboBox<String>();
			cbIDs.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 20));
			cbIDs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					updateLblId();
					getTxtId().setText("");
					getTxtId().requestFocus();
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
	
	private JPanel getPnNavL() {
		if (pnNavL == null) {
			pnNavL = new JPanel();
			pnNavL.setOpaque(false);
			GroupLayout gl_pnBtnLeft = new GroupLayout(pnNavL);
			gl_pnBtnLeft.setHorizontalGroup(
				gl_pnBtnLeft.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnBtnLeft.createSequentialGroup()
						.addGap(128)
						.addComponent(getBtnSave(), GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
						.addGap(70))
			);
			gl_pnBtnLeft.setVerticalGroup(
				gl_pnBtnLeft.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_pnBtnLeft.createSequentialGroup()
						.addGap(22)
						.addComponent(getBtnSave(), GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addContainerGap())
			);
			pnNavL.setLayout(gl_pnBtnLeft);
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
						.addComponent(getBtnBack(), GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
						.addGap(128))
			);
			gl_pnNavR.setVerticalGroup(
				gl_pnNavR.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_pnNavR.createSequentialGroup()
						.addGap(22)
						.addComponent(getBtnBack(), GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addContainerGap())
			);
			pnNavR.setLayout(gl_pnNavR);
		}
		return pnNavR;
	}
}
