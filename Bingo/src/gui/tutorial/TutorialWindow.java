package gui.tutorial;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.MainWindow;
import gui.utils.FactoryImage;
import gui.utils.Localizer;

import javax.swing.JLabel;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;

public class TutorialWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public final static String PAGE_1 = "page1";
	public final static String PAGE_2 = "page2";
	public final static String PAGE_3 = "page3";
	public final static String PAGE_4 = "page4";
	
	private MainWindow window;
	
	private JPanel contentPane;
	private JPanel pnCentral;
	private JPanel pnHead;
	private JLabel lblLogo;
	private JPanel pnNav;
	private JPanel pnHeadBtn;
	private JButton btnLang;
	private JPanel pnNavBtn;
	private JButton btnClose;
	private JPanel pnBtnPrev;
	private JPanel pnBtnNext;
	private JButton btnPrev;
	private JButton btnNext;
	private JLabel lblPage1;
	private JLabel lblPage2;
	private JLabel lblPage3;
	private JLabel lblPage4;
	private int currentPage;

	/**
	 * Create the frame.
	 */
	public TutorialWindow(MainWindow window) {
		setModal(true);
		setResizable(false);
		this.window = window;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				updatePages();
			}
		});
		
		setTitle("Super Bingo: Tutorial");
		setIconImage(FactoryImage.loadImagen(FactoryImage.APP_ICON).getImage());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1050, 685);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 20));
		contentPane.setBackground(new Color(51, 204, 255));
		setContentPane(contentPane);
		contentPane.add(getPnCentral(), BorderLayout.CENTER);
		contentPane.add(getPnHead(), BorderLayout.NORTH);
		contentPane.add(getPnNav(), BorderLayout.SOUTH);
		contentPane.add(getPnBtnPrev(), BorderLayout.WEST);
		contentPane.add(getPnBtnNext(), BorderLayout.EAST);
		
		setDefaultConfig();
	}

	public void setDefaultConfig() {
		currentPage = 1;
		updateBtns();
		translate(0);
	}
	public void translate(int o) {
		Localizer loc = window.getLocalizer();

		getBtnLang().setToolTipText(loc.getLocateText("global.btnLang.tt"));
		
		String msg = currentPage<4 ? "tuto.btnSkip" : "global.btnExit";
		msg = window.getLocalizer().getLocateText(msg);
		getBtnClose().setText(msg);
		
		msg = currentPage<4 ? "tuto.btnSkip.mn" : "global.btnExit.mn";
		char mn = window.getLocalizer().getLocateChar(msg);
		getBtnClose().setMnemonic(mn);
		
		getBtnPrev().setToolTipText(loc.getLocateText("tuto.btnPrev.tt"));
		getBtnNext().setToolTipText(loc.getLocateText("tuto.btnNext.tt"));
		if(o == 0)
			return;
		updatePages();
	}
	private void updatePages() {
		Localizer loc = window.getLocalizer();
		
		FactoryImage.setPageTuto(getLblPage1(), loc.getLocateText("tuto.page1"));
		FactoryImage.setPageTuto(getLblPage2(), loc.getLocateText("tuto.page2"));
		FactoryImage.setPageTuto(getLblPage3(), loc.getLocateText("tuto.page3"));
		FactoryImage.setPageTuto(getLblPage4(), loc.getLocateText("tuto.page4"));
	}
	
	private JPanel getPnHead() {
		if (pnHead == null) {
			pnHead = new JPanel();
			pnHead.setOpaque(false);
			pnHead.setLayout(new BorderLayout(0, 0));
			pnHead.add(getLblLogo());
			pnHead.add(getPnHeadBtn(), BorderLayout.EAST);
		}
		return pnHead;
	}
	private JLabel getLblLogo() {
		if (lblLogo == null) {
			lblLogo = new JLabel("");
			
			ImageIcon icon = FactoryImage.loadImagen(FactoryImage.LOGO_MED);
			icon = FactoryImage.scaleImg(icon.getImage(), 300, 65);
			lblLogo.setIcon(icon);
		}
		return lblLogo;
	}
	private JPanel getPnNav() {
		if (pnNav == null) {
			pnNav = new JPanel();
			pnNav.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
			pnNav.setOpaque(false);
			pnNav.setLayout(new BorderLayout(0, 0));
			pnNav.add(getPnNavBtn(), BorderLayout.EAST);
		}
		return pnNav;
	}
	private JPanel getPnHeadBtn() {
		if (pnHeadBtn == null) {
			pnHeadBtn = new JPanel();
			pnHeadBtn.setOpaque(false);
			pnHeadBtn.setLayout(new GridLayout(0, 1, 0, 0));
			pnHeadBtn.add(getBtnLang());
		}
		return pnHeadBtn;
	}
	private JButton getBtnLang() {
		if (btnLang == null) {
			btnLang = new JButton("");
			btnLang.setContentAreaFilled(false);
			btnLang.setBorderPainted(false);
			btnLang.setBackground(new Color(255, 102, 153));
			btnLang.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					window.openSettings();
					translate(1);
				}
			});
			ImageIcon icon = FactoryImage.loadImagen(FactoryImage.LANG_ICON);
			icon = FactoryImage.scaleImg(icon.getImage(), 50, 50);
			btnLang.setIcon(icon);
		}
		return btnLang;
	}
	private JPanel getPnNavBtn() {
		if (pnNavBtn == null) {
			pnNavBtn = new JPanel();
			pnNavBtn.setBorder(new EmptyBorder(0, 0, 7, 7));
			pnNavBtn.setOpaque(false);
			pnNavBtn.add(getBtnClose());
		}
		return pnNavBtn;
	}
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("Skip Tutorial");
			btnClose.setForeground(Color.WHITE);
			btnClose.setBackground(new Color(255, 0, 0));
			btnClose.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 25));
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}
	private JPanel getPnBtnPrev() {
		if (pnBtnPrev == null) {
			pnBtnPrev = new JPanel();
			pnBtnPrev.setOpaque(false);
			pnBtnPrev.setLayout(new BorderLayout(0, 0));
			pnBtnPrev.add(getBtnPrev(), BorderLayout.CENTER);
		}
		return pnBtnPrev;
	}
	private JPanel getPnBtnNext() {
		if (pnBtnNext == null) {
			pnBtnNext = new JPanel();
			pnBtnNext.setOpaque(false);
			pnBtnNext.setLayout(new BorderLayout(0, 0));
			pnBtnNext.add(getBtnNext());
		}
		return pnBtnNext;
	}
	private void updateBtns() {
		getBtnPrev().setEnabled(currentPage>1);

		getBtnNext().setEnabled(currentPage<4);
		
		String msg = currentPage<4 ? "tuto.btnSkip" : "global.btnExit";
		msg = window.getLocalizer().getLocateText(msg);
		getBtnClose().setText(msg);
		
		msg = currentPage<4 ? "tuto.btnSkip.mn" : "global.btnExit.mn";
		char mn = window.getLocalizer().getLocateChar(msg);
		getBtnClose().setMnemonic(mn);
		
	}
	private JButton getBtnPrev() {
		if (btnPrev == null) {
			btnPrev = new JButton("\u2039");
			btnPrev.setForeground(Color.DARK_GRAY);
			btnPrev.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					((CardLayout) getPnCentral().getLayout()).previous(getPnCentral());
					currentPage--;
					updateBtns();
				}
			});
			btnPrev.setFont(new Font("Arial", Font.BOLD, 86));
			btnPrev.setContentAreaFilled(false);
			btnPrev.setBorderPainted(false);
		}
		return btnPrev;
	}
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("\u203A");
			btnNext.setForeground(Color.DARK_GRAY);
			btnNext.setFont(new Font("Arial", Font.BOLD, 86));
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((CardLayout) getPnCentral().getLayout()).next(getPnCentral());
					currentPage++;
					updateBtns();
				}
			});
			btnNext.setContentAreaFilled(false);
			btnNext.setBorderPainted(false);
		}
		return btnNext;
	}
	private JPanel getPnCentral() {
		if (pnCentral == null) {
			pnCentral = new JPanel();
			pnCentral.setBorder(new LineBorder(new Color(255, 153, 51), 4));
			pnCentral.setLayout(new CardLayout(0, 0));
			pnCentral.add(getLblPage1(), PAGE_1);
			pnCentral.add(getLblPage2(), PAGE_2);
			pnCentral.add(getLblPage3(), PAGE_3);
			pnCentral.add(getLblPage4(), PAGE_4);
		}
		return pnCentral;
	}
	private JLabel getLblPage1() {
		if (lblPage1 == null) {
			lblPage1 = new JLabel("");
		}
		return lblPage1;
	}
	private JLabel getLblPage2() {
		if (lblPage2 == null) {
			lblPage2 = new JLabel("");
		}
		return lblPage2;
	}
	private JLabel getLblPage3() {
		if (lblPage3 == null) {
			lblPage3 = new JLabel("");
		}
		return lblPage3;
	}
	private JLabel getLblPage4() {
		if (lblPage4 == null) {
			lblPage4 = new JLabel("");
		}
		return lblPage4;
	}
}
