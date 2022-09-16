package gui.client;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.MainWindow;
import gui.utils.FactoryImage;
import gui.utils.Localizer;
import logic.clients.Client;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AmountClientDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private MainWindow window;
	private Client client;
	private Localizer localizer;
	
	private JPanel contentPane;
	private JLabel lblHead;
	private JButton btnBack;
	private JLabel lblHeadName;
	

	/**
	 * Create the frame.
	 */
	public AmountClientDialog(MainWindow window, Client client, Localizer localizer) {
		setBackground(new Color(51, 204, 255));
		setResizable(false);
		setModal(true);
		this.window = window;
		this.client = client;
		this.localizer = localizer;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		setTitle(localizer.getLocateText("ca.title"));
		setIconImage(FactoryImage.loadImagen(FactoryImage.APP_ICON).getImage());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 528, 292);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblHead());
		contentPane.add(getBtnBack());
		contentPane.add(getLblHeadName());
		
		setDefaultConfig();
	}
	
	private void close() {
		dispose();
	}

	private void setDefaultConfig() {
		String msg = "";
		if(client != null) {
			msg = localizer.getLocateText("acdialog.registered.name");
			String name = client.getName();
			msg = String.format(msg, name);
			getLblHeadName().setText(msg);
			
			msg = localizer.getLocateText("acdialog.registered.amount");
			String amountStr = localizer.getLocateAmout(client.getAmount());
			msg = String.format(msg, amountStr);
			getLblHead().setText(msg);
		} else {
			msg = localizer.getLocateText("acdialog.noRegistered");
			getLblHead().setText(msg);
		}
	}
	private JLabel getLblHead() {
		if (lblHead == null) {
			lblHead = new JLabel("You have 20");
			lblHead.setBounds(30, 90, 497, 53);
			lblHead.setForeground(new Color(0, 0, 153));
			lblHead.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32));
		}
		return lblHead;
	}
	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton(localizer.getLocateText("global.btnBack"));
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					close();
				}
			});
			btnBack.setBackground(new Color(255, 204, 0));
			btnBack.setForeground(Color.DARK_GRAY);
			btnBack.setBounds(332, 162, 160, 70);
			btnBack.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.BOLD, 28));
			btnBack.setMnemonic(localizer.getLocateChar("global.btnBack.mn"));
		}
		return btnBack;
	}
	private JLabel getLblHeadName() {
		if (lblHeadName == null) {
			lblHeadName = new JLabel("");
			lblHeadName.setForeground(new Color(0, 0, 153));
			lblHeadName.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32));
			lblHeadName.setBounds(30, 38, 497, 53);
		}
		return lblHeadName;
	}
}
