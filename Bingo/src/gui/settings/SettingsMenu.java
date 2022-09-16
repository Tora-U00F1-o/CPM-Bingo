package gui.settings;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

import javax.swing.border.MatteBorder;

import gui.MainWindow;
import gui.utils.FactoryImage;
import gui.utils.Localizer;
import logic.player.MusicPlayer;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Panel;
import javax.swing.border.TitledBorder;
import javax.swing.JSlider;

public class SettingsMenu extends JDialog {

	private static final long serialVersionUID = 1L;

	public final static String LANG_PN = "pnLanguage";
	public final static String AUDIO_PN = "pnAudio";

	private MainWindow window;

	private JPanel contentPane;
	private JPanel pnMenu;
	private JPanel pnBtn;
	private JPanel pnCentral;
	private JPanel pnAudio;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnLangSett;
	private JButton btnMediaSett;
	private Panel pnContents;
	private JPanel pnLanguage;
	private JRadioButton rbLangDefault;
	private JRadioButton rbLangSpanish;
	private JRadioButton rbLangEnglish;
	private JButton btnLangEs;
	private JButton btnLangEn;
	private JButton btnLangDef;
	private JLabel lblHead;
	private JButton btnClose;
	private JPanel panel;
	private JCheckBox checkAudioEnable;
	private JPanel pnVolLevel;
	private JSlider slVolume;
	private JButton btnApply;
	
	private JPanel currentMenu;
	private JPanel pnMenuBtnContainer;

	/**
	 * Create the frame.
	 */
	public SettingsMenu(MainWindow window) {
		setModal(true);
		this.window = window;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 547, 437);
		setResizable(false);
		setIconImage(FactoryImage.loadImagen(FactoryImage.APP_ICON).getImage());
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setLayout(new BorderLayout(3, 0));
		setContentPane(contentPane);
		contentPane.add(getPnMenu(), BorderLayout.WEST);
		contentPane.add(getPnBtn(), BorderLayout.SOUTH);
		contentPane.add(getPnCentral(), BorderLayout.CENTER);

		setDefaultConfig();
	}

	public void setDefaultConfig() {
		getCheckAudioEnable().setSelected(!window.getPlayer().isMute());
		slVolume.setValue((int) (window.getPlayer().getVolume()));
		setRbSelected(window.getLocalizer().getMode());
		showPnLanguage();
		translate();
	}

	public void translate() {
		Localizer localizer = window.getLocalizer();
		setTitle(localizer.getLocateText("sett.title"));
		translateHead();
		getBtnMediaSett().setText(localizer.getLocateText("sett.btnMedia"));
		getBtnMediaSett().setMnemonic(localizer.getLocateChar("sett.btnMedia.mn"));
		getCheckAudioEnable().setText(localizer.getLocateText("sett.btnAudEnabled"));
		getCheckAudioEnable().setMnemonic(localizer.getLocateChar("sett.btnAudEnabled.mn"));
		((TitledBorder) getPnVolLevel().getBorder()).setTitle(localizer.getLocateText("sett.pnSliderVol"));
		getBtnLangSett().setText(localizer.getLocateText("sett.btnLang"));
		getBtnLangSett().setMnemonic(localizer.getLocateChar("sett.btnLang.mn"));
		getRbLangDefault().setText(localizer.getLocateText("sett.rbLangDef"));
		getRbLangDefault().setMnemonic(localizer.getLocateChar("sett.rbLangDef.mn"));
		getRbLangEnglish().setMnemonic(localizer.getLocateChar("sett.rbLangEn.mn"));
		getRbLangSpanish().setMnemonic(localizer.getLocateChar("sett.rbLangEs.mn"));
		getBtnApply().setText(localizer.getLocateText("sett.btnApply"));
		getBtnApply().setMnemonic(localizer.getLocateChar("sett.btnApply.mn"));
		getBtnClose().setText(localizer.getLocateText("sett.btnClose"));
		getBtnClose().setMnemonic(localizer.getLocateChar("sett.btnClose.mn"));
	}
	private void translateHead() {
		String key = null;
		switch(currentMenu.getName()) {
		case LANG_PN:
			key = "sett.headLang";
			break;
		case AUDIO_PN:
			key = "sett.headMedia";
			break;
		}
		if(key == null) {
			showPnLanguage();
			return;
		}
		getLblHead().setText(" "+window.getLocalizer().getLocateText(key));
	}
	private JPanel getPnMenu() {
		if (pnMenu == null) {
			pnMenu = new JPanel();
			pnMenu.setBackground(new Color(51, 204, 255));
			pnMenu.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
			pnMenu.setLayout(new GridLayout(1, 1, 0, 0));
			pnMenu.add(getPnMenuBtnContainer());
		}
		return pnMenu;
	}

	private JPanel getPnBtn() {
		if (pnBtn == null) {
			pnBtn = new JPanel();
			pnBtn.setBackground(new Color(173, 216, 230));
			pnBtn.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
			pnBtn.setLayout(new BorderLayout(0, 0));
			pnBtn.add(getPanel(), BorderLayout.EAST);
		}
		return pnBtn;
	}

	private JPanel getPnCentral() {
		if (pnCentral == null) {
			pnCentral = new JPanel();
			pnCentral.setBorder(new EmptyBorder(4, 0, 0, 2));
			pnCentral.setBackground(new Color(204, 255, 255));
			pnCentral.setLayout(new BorderLayout(0, 0));
			pnCentral.add(getPnContents(), BorderLayout.CENTER);
			pnCentral.add(getLblHead(), BorderLayout.NORTH);
		}
		return pnCentral;
	}

	private JButton getBtnLangSett() {
		if (btnLangSett == null) {
			btnLangSett = new JButton("language");
			btnLangSett.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 17));
			btnLangSett.setOpaque(false);
			btnLangSett.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showPnLanguage();
				}
			});
		}
		return btnLangSett;
	}

	private JButton getBtnMediaSett() {
		if (btnMediaSett == null) {
			btnMediaSett = new JButton("media");
			btnMediaSett.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 17));
			btnMediaSett.setOpaque(false);
			btnMediaSett.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showPnAudio();
				}
			});
		}
		return btnMediaSett;
	}

	private JPanel getPnAudio() {
		if (pnAudio == null) {
			pnAudio = new JPanel();
			pnAudio.setOpaque(false);
			pnAudio.setName(AUDIO_PN);
			pnAudio.setLayout(null);
			pnAudio.add(getCheckAudioEnable());
			pnAudio.add(getPnVolLevel());
		}
		return pnAudio;
	}
	private void showPnAudio() {
		((CardLayout) getPnContents().getLayout()).show(getPnContents(), AUDIO_PN);
		getLblHead().setText(" "+window.getLocalizer().getLocateText("sett.headMedia"));
		currentMenu = getPnAudio();
	}
	private Panel getPnContents() {
		if (pnContents == null) {
			pnContents = new Panel();
			pnContents.setBackground(new Color(204, 255, 255));
			pnContents.setLayout(new CardLayout(0, 0));
			pnContents.add(getPnLanguage(), LANG_PN);
			pnContents.add(getPnAudio(), AUDIO_PN);
		}
		return pnContents;
	}

	private JPanel getPnLanguage() {
		if (pnLanguage == null) {
			pnLanguage = new JPanel();
			pnLanguage.setOpaque(false);
			pnLanguage.setName(LANG_PN);
			pnLanguage.setLayout(null);
			pnLanguage.add(getRbLangDefault());
			pnLanguage.add(getRbLangSpanish());
			pnLanguage.add(getRbLangEnglish());
			pnLanguage.add(getBtnLangEs());
			pnLanguage.add(getBtnLangEn());
			pnLanguage.add(getBtnLangDef());
		}
		return pnLanguage;
	}

	private void showPnLanguage() {
		((CardLayout) getPnContents().getLayout()).show(getPnContents(), LANG_PN);
		getLblHead().setText(" "+window.getLocalizer().getLocateText("sett.headLang"));
		currentMenu = getPnLanguage();
	}

	private JRadioButton getRbLangDefault() {
		if (rbLangDefault == null) {
			rbLangDefault = new JRadioButton("default");
			buttonGroup.add(rbLangDefault);
			rbLangDefault.setActionCommand("" + Localizer.AUTO_MODE);
			rbLangDefault.setSelected(true);
			rbLangDefault.setOpaque(false);
			rbLangDefault.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 20));
			rbLangDefault.setBounds(96, 43, 127, 25);
		}
		return rbLangDefault;
	}

	private JRadioButton getRbLangSpanish() {
		if (rbLangSpanish == null) {
			rbLangSpanish = new JRadioButton("Espa\u00F1ol");
			buttonGroup.add(rbLangSpanish);
			rbLangSpanish.setOpaque(false);
			rbLangSpanish.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 20));
			rbLangSpanish.setBounds(96, 102, 127, 25);
			rbLangSpanish.setActionCommand("" + Localizer.ES_MODE);
		}
		return rbLangSpanish;
	}

	private JRadioButton getRbLangEnglish() {
		if (rbLangEnglish == null) {
			rbLangEnglish = new JRadioButton("English");
			buttonGroup.add(rbLangEnglish);
			rbLangEnglish.setOpaque(false);
			rbLangEnglish.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 20));
			rbLangEnglish.setBounds(96, 160, 127, 25);
			rbLangEnglish.setActionCommand("" + Localizer.EN_MODE);
		}
		return rbLangEnglish;
	}
	
	private JRadioButton getRbSelected() {
		JRadioButton[] b = new JRadioButton[] { getRbLangDefault(), getRbLangSpanish(), getRbLangEnglish() };
		for (int i = 0; i < b.length; i++)
			if (b[i].isSelected())
				return b[i];
		return null;
	}
	
	private void setRbSelected(int mode) {
		JRadioButton[] b = new JRadioButton[] { getRbLangDefault(), getRbLangSpanish(), getRbLangEnglish() };
		for (int i = 0; i < b.length; i++)
			if (b[i].getActionCommand().equals(""+mode))
				b[i].setSelected(true);
	}

	private JButton getBtnLangEs() {
		if (btnLangEs == null) {
			btnLangEs = new JButton("");
			btnLangEs.setContentAreaFilled(false);
			btnLangEs.setOpaque(false);
			btnLangEs.setBorderPainted(false);
			btnLangEs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getRbLangSpanish().doClick();
				}
			});
			btnLangEs.setBounds(21, 88, 56, 52);
			ImageIcon icon = FactoryImage.loadImagen("/media/img/langImg/es.png");
			FactoryImage.setAdaptedImgButton(btnLangEs, icon.getImage());
		}
		return btnLangEs;
	}

	private JButton getBtnLangEn() {
		if (btnLangEn == null) {
			btnLangEn = new JButton("");
			btnLangEn.setContentAreaFilled(false);
			btnLangEn.setOpaque(false);
			btnLangEn.setBorderPainted(false);
			btnLangEn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getRbLangEnglish().doClick();
				}
			});
			btnLangEn.setBounds(21, 145, 56, 52);
			ImageIcon icon = FactoryImage.loadImagen("/media/img/langImg/en.png");
			FactoryImage.setAdaptedImgButton(btnLangEn, icon.getImage());
		}
		return btnLangEn;
	}

	private JButton getBtnLangDef() {
		if (btnLangDef == null) {
			btnLangDef = new JButton("");
			btnLangDef.setContentAreaFilled(false);
			btnLangDef.setOpaque(false);
			btnLangDef.setBorderPainted(false);
			btnLangDef.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getRbLangDefault().doClick();
				}
			});
			btnLangDef.setBounds(21, 34, 56, 52);
			ImageIcon icon = FactoryImage.loadImagen("/media/img/langImg/lang.png");
			FactoryImage.setAdaptedImgButton(btnLangDef, icon.getImage());
		}
		return btnLangDef;
	}

	private JLabel getLblHead() {
		if (lblHead == null) {
			lblHead = new JLabel("Language");
			lblHead.setBackground(new Color(204, 255, 255));
			lblHead.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 25));
			lblHead.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		}
		return lblHead;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setOpaque(false);
			panel.add(getBtnApply());
			panel.add(getBtnClose());
		}
		return panel;
	}

	private JCheckBox getCheckAudioEnable() {
		if (checkAudioEnable == null) {
			checkAudioEnable = new JCheckBox("Audio enable");
			checkAudioEnable.setOpaque(false);
			checkAudioEnable.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 20));
			checkAudioEnable.setSelected(true);
			checkAudioEnable.setBounds(28, 32, 188, 29);
		}
		return checkAudioEnable;
	}

	private JPanel getPnVolLevel() {
		if (pnVolLevel == null) {
			pnVolLevel = new JPanel();
			pnVolLevel.setOpaque(false);
			Font font = new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 18);
			pnVolLevel.setBorder(
					new TitledBorder(null, "Volume level", TitledBorder.LEADING, TitledBorder.TOP, font, null));
			pnVolLevel.setBounds(28, 92, 368, 89);
			pnVolLevel.setLayout(new BorderLayout(0, 0));
			pnVolLevel.add(getSlVolume());
		}
		return pnVolLevel;
	}

	private JSlider getSlVolume() {
		if (slVolume == null) {
			slVolume = new JSlider();
			slVolume.setMaximum(MusicPlayer.MAX_VOL);
			slVolume.setFont(new Font("Tahoma", Font.PLAIN, 15));
			slVolume.setToolTipText("Volume Control");
			slVolume.setFocusable(false);
			slVolume.setBackground(new Color(255, 255, 255));
			slVolume.setForeground(Color.DARK_GRAY);
			slVolume.setPaintTicks(true);
			slVolume.setPaintLabels(true);
			slVolume.setMinorTickSpacing(10);
			slVolume.setMajorTickSpacing(20);
		}
		return slVolume;
	}

	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton("Apply");
			btnApply.setForeground(new Color(255, 255, 255));
			btnApply.setBackground(new Color(0, 153, 0));
			btnApply.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 17));
			btnApply.addActionListener(new ProcessApply());
		}
		return btnApply;
	}

	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("cancel");
			btnClose.setForeground(new Color(255, 255, 255));
			btnClose.setBackground(new Color(255, 0, 0));
			btnClose.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 17));
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					closeSettings();
				}
			});
		}
		return btnClose;
	}

	private void closeSettings() {
		dispose();
	}

	private class ProcessApply implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// apply Lang config
			JRadioButton button = getRbSelected();
			int mode = Integer.parseInt(button.getActionCommand());

			window.getLocalizer().setMode(mode);
			window.translateAll();
			translate();

			// apply media config
			if(getCheckAudioEnable().isSelected()) {
				int value = getSlVolume().getValue();
				window.getPlayer().setMute(false);
				window.getPlayer().setVolume(value, getSlVolume().getMaximum());
			} else 
				window.getPlayer().setMute(true);
		}
	}
	private JPanel getPnMenuBtnContainer() {
		if (pnMenuBtnContainer == null) {
			pnMenuBtnContainer = new JPanel();
			pnMenuBtnContainer.setBackground(new Color(102, 204, 255));
			pnMenuBtnContainer.setOpaque(false);
			pnMenuBtnContainer.setBorder(new EmptyBorder(3, 3, 0, 3));
			pnMenuBtnContainer.setLayout(new GridLayout(10, 1, 0, 0));
			pnMenuBtnContainer.add(getBtnLangSett());
			pnMenuBtnContainer.add(getBtnMediaSett());
		}
		return pnMenuBtnContainer;
	}
}