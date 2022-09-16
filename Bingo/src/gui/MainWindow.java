package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.help.*;
import java.net.*;
import java.io.*;

import events.ProcessBackHome;
import gui.client.AmountClientDialog;
import gui.client.PnCheckAccount;
import gui.client.numMag.PnNumMag;
import gui.client.numMag.PnRegister;
import gui.game.PnBingo;
import gui.rewards.PnReward;
import gui.settings.SettingsMenu;
import gui.ticket.PnTicketValidator;
import gui.transition.PnTransition;
import gui.tutorial.TutorialWindow;
import gui.utils.BingoPanel;
import gui.utils.FactoryImage;
import gui.utils.Localizer;
import logic.Config;
import logic.clients.Client;
import logic.clients.ClientManager;
import logic.game.Bingo;
import logic.player.MusicPlayer;
import logic.tickets.TicketManager;

import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import javax.swing.JSeparator;

public class MainWindow extends JFrame {
	private final static int TIME_OF_TRANSITION_SEC = 1;
	
	public static final String CP_HOME = "pnHome";
	public static final String CP_BASE = "pnBase";
	
	public static final String BASE_CA = "pnCheckAccount";
	public static final String BASE_TICKET = "pnTicket";
	public static final String BASE_BINGO = "pnBingo";
	public static final String BASE_REWARD = "pnReward";
	public static final String BASE_NUMMAG = "pnNumMag";
	public static final String BASE_REGISTER = "pnRegister";
	public static final String BASE_TRANSITION = "pnTransition";
	
	private static final long serialVersionUID = 1L;

	private ProcessBackHome processExit;
	
	private Bingo bingo;
	private TicketManager ticketManager;
	private ClientManager clientManager;
	
	private Localizer localizer;
	private MusicPlayer player;
	
	private URL hsURL;
	private HelpSet hs;
	private HelpBroker hb;
	
	private JPanel contentPane;
	
	private BingoPanel currentPane;
	private BingoPanel prevPane;
	
	private JPanel pnHome;
	
	private JLabel lblLogoBig;
	private JLabel lblHead;
	private JPanel pnHomeNav;
	private JPanel pnHomeNavLeft;
	private JButton btnCheckAccount;
	private JPanel pnHomeNavRight;
	private JButton btnPlay;
	
	private JPanel pnBase;
	private JLabel lblLogoMedium;
	
	private JPanel pnCardBase;
	
	private PnCheckAccount pnCheckAccount; 
	private PnTicketValidator pnTicketValidator;
	private PnBingo pnBingo;
	private PnReward pnReward;
	private PnNumMag pnNumMag;
	private PnRegister pnRegister;
	private PnTransition pnTransition;
	
	private JMenuBar menuBar;
	private JMenu mnSettings;
	private JPanel pnHomeContainer;
	private JPanel pnNorth;
	private JPanel pnBaseBtn;
	private JButton btnLang;
	private JButton btnHelp;
	private JMenu mnGame;
	private JMenu mnHelp;
	private JMenuItem itExit;
	private JMenuItem itPreferences;
	private JMenuItem itContents;
	private JMenuItem itAbout;
	private JPanel pnCentral;
	private JPanel pnHomeBt;
	private JPanel pnHomeBtn;
	private JLabel lblHead2;
	private JSeparator separator;
	private JButton btnQuickCheckAcc;
	private JButton btnOpenTutorial;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(currentPane == null) {
					dispose();
					player.stop();
				} else 
					closingApp();					
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				if(currentPane != null && currentPane.equals(pnBingo))
					pnBingo.resize();
			}
		});
		ticketManager = new TicketManager();
		bingo = new Bingo(ticketManager);
		clientManager = new ClientManager();
		localizer = new Localizer();
		
		player = new MusicPlayer();
	
		processExit = new ProcessBackHome(this, null, true, true, localizer);
		
	
		setMinimumSize(new Dimension(1085, 647));
		setIconImage(FactoryImage.loadImagen(FactoryImage.APP_ICON).getImage());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1085, 647);
		setLocationRelativeTo(null);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		contentPane.add(getPnHome(), CP_HOME);
		contentPane.add(getPnBase(), CP_BASE);
		
		loadHelp();
		
		translateHome();
		showPnHome();
	}
	private void loadHelp() {
		try {
			File fichero = new File("src/help/Ayuda.hs");
			hsURL = fichero.toURI().toURL();
			hs = new HelpSet(null, hsURL);
			
		} catch (Exception e) {
			return;
		}

		hb = hs.createHelpBroker();

		hb.enableHelpKey(getRootPane(), "introduccion", hs);
		hb.enableHelpOnButton(getItContents(), "introduccion", hs);
		hb.enableHelpOnButton(getBtnHelp(), "introduccion", hs);
	}
	private void updateBtnHelpFuntion() {
		String key ="";
		if(currentPane == null)
			key = "introduccion";
		else if(currentPane == getPnCheckAccount())
			key = "saldo";
		else if(currentPane == getPnTicketValidator() || currentPane == getPnBingo())
			key = "jugar";
		else if(currentPane == getPnReward())
			key = "canjear";
		else
			key = "guardarBono";
		
		hb.enableHelpKey(getRootPane(), key, hs);
		hb.enableHelpOnButton(getItContents(), key, hs);
		hb.enableHelpOnButton(getBtnHelp(), key, hs);
	}
	
	private void closingApp() {
		String msg = localizer.getLocateText("pbh.msg");
		String title = localizer.getLocateText("pbh.title");
		
		int choose = JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(JOptionPane.YES_OPTION == choose) 
			showPnHome();
	}
	public void backHome(boolean makeTransition) {
		if(!makeTransition) {
			showPnHome();
			return;
		}
			
		showPnTransition();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				showPnHome();
				cancel();
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, TIME_OF_TRANSITION_SEC *1000, 1);
		
		clientManager.saveData();
		clientManager.inicialize();
	}
	public ProcessBackHome getProcessExit() {
		return processExit;
	}
	public Bingo getBingo() {
		return bingo;
	}
	public TicketManager getTicketManager() {
		return ticketManager;
	}
	public ClientManager getClientManager() {
		return clientManager;
	}
	public Localizer getLocalizer() {
		return localizer;
	}
	public MusicPlayer getPlayer() {
		return this.player;
	}
	public void showPnOfContentPane(String pnName) {
		((CardLayout) getContentPane().getLayout()).show(getContentPane(), pnName);
	}
	
	private JPanel getPnHome() {
		if (pnHome == null) {
			pnHome = new JPanel();
			pnHome.setName(CP_HOME);
			pnHome.setOpaque(false);
			pnHome.setBackground(new Color(51, 102, 204));
			pnHome.setLayout(new BorderLayout(0, 0));
			pnHome.add(getPnHomeBt(), BorderLayout.NORTH);
			pnHome.add(getPnHomeContainer());
		}
		return pnHome;
	}
	public void showPnHome() {
		showPnOfContentPane(MainWindow.CP_HOME);
		currentPane = null;
		changeQuickButtons(true);
		setDefaultConfig();
		updateBtnHelpFuntion();
	}
	private void setDefaultConfig() {
		setTitle(localizer.getLocateText("global.title"));
		player.setVolume(50, 100);
//		player.playSoundTrack();
		localizer.setMode(Localizer.AUTO_MODE);
		translateAll();
	}
	public void translateAll() {
		if(currentPane == null)
			translateHome();
		else {
			currentPane.translate();
			if(prevPane != null)
				prevPane.translate();
		}
		
		translateMenu();
	}
	public void translateHome() {
		setTitle(localizer.getLocateText("global.title"));
		getLblHead().setText(localizer.getLocateText("home.textHead1"));
		
		String head2 = localizer.getLocateText("home.textHead2");
		String amount = localizer.getLocateAmout(Config.TICKET_MIN_AMOUNT);
		getLblHead2().setText(String.format(head2, amount));
		getBtnPlay().setText(localizer.getLocateText("global.btnPlay"));
		getBtnPlay().setMnemonic(localizer.getLocateChar("global.btnPlay.mn"));
		getBtnCheckAccount().setText(localizer.getLocateText("home.btnCheckAccount"));
		getBtnCheckAccount().setMnemonic(localizer.getLocateChar("home.btnCheckAccount.mn"));
	}
	public void translateMenu() {
		getBtnHelp().setToolTipText(localizer.getLocateText("global.btnHelp.tt"));
		getBtnLang().setToolTipText(localizer.getLocateText("global.btnLang.tt"));
		getBtnQuickCheckAcc().setToolTipText(localizer.getLocateText("global.btnQCA.tt"));
		getBtnOpenTutorial().setToolTipText(localizer.getLocateText("global.btnTutorial.tt"));
		
		getMnGame().setText(localizer.getLocateText("mn.game"));
		getMnGame().setMnemonic(localizer.getLocateChar("mn.game.mn"));
		getItExit().setText(localizer.getLocateText("mn.game.exit"));
		getItExit().setMnemonic(localizer.getLocateChar("mn.game.exit.mn"));
		getMnSettings().setText(localizer.getLocateText("mn.sett"));
		getMnSettings().setMnemonic(localizer.getLocateChar("mn.sett.mn"));
		getItPreferences().setText(localizer.getLocateText("mn.sett.pref"));
		getItPreferences().setMnemonic(localizer.getLocateChar("mn.sett.pref.mn"));
		getMnHelp().setText(localizer.getLocateText("mn.help"));
		getMnHelp().setMnemonic(localizer.getLocateChar("mn.help.mn"));
		getItContents().setText(localizer.getLocateText("mn.help.contents"));
		getItContents().setMnemonic(localizer.getLocateChar("mn.help.contents.mn"));
		getItAbout().setText(localizer.getLocateText("mn.help.about"));
		getItAbout().setMnemonic(localizer.getLocateChar("mn.help.about.mn"));
	}
	private JLabel getLblLogoBig() {
		if (lblLogoBig == null) {
			lblLogoBig = new JLabel("");
			lblLogoBig.setIcon(FactoryImage.loadImagen(FactoryImage.LOGO_BIG));
			lblLogoBig.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblLogoBig;
	}
	private JLabel getLblHead() {
		if (lblHead == null) {
			lblHead = new JLabel(localizer.getLocateText("home.textHead1"));
			lblHead.setVerticalAlignment(SwingConstants.BOTTOM);
			lblHead.setForeground(new Color(0, 0, 153));
			lblHead.setHorizontalAlignment(SwingConstants.CENTER);
			lblHead.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 31));
		}
		return lblHead;
	}
	private JPanel getPnCentral() {
		if (pnCentral == null) {
			pnCentral = new JPanel();
			pnCentral.setOpaque(false);
			pnCentral.setLayout(new GridLayout(2, 1, 0, 0));
			pnCentral.add(getLblHead());
			pnCentral.add(getLblHead2());
		}
		return pnCentral;
	}
	private JLabel getLblHead2() {
		if (lblHead2 == null) {
			lblHead2 = new JLabel(localizer.getLocateText("home.textHead2"));
			lblHead2.setVerticalAlignment(SwingConstants.TOP);
			lblHead2.setForeground(new Color(0, 0, 153));
			lblHead2.setHorizontalAlignment(SwingConstants.CENTER);
			lblHead2.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 31));
		}
		return lblHead2;
	}
	private JPanel getPnHomeNav() {
		if (pnHomeNav == null) {
			pnHomeNav = new JPanel();
			pnHomeNav.setOpaque(false);
			pnHomeNav.setLayout(new GridLayout(0, 2, 0, 0));
			pnHomeNav.add(getPnHomeNavLeft());
			pnHomeNav.add(getPnHomeNavRight());
		}
		return pnHomeNav;
	}
	
	private JButton getBtnPlay() {
		if (btnPlay == null) {
			btnPlay = new JButton(localizer.getLocateText("global.btnPlay"));
			btnPlay.setBackground(new Color(255, 204, 0));
			btnPlay.setForeground(Color.DARK_GRAY);
			btnPlay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					showPnTicketValidator();
				}
			});
			btnPlay.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32));
		}
		return btnPlay;
	}
	private JButton getBtnCheckAccount() {
		if (btnCheckAccount == null) {
			btnCheckAccount = new JButton(localizer.getLocateText("home.btnCheckAccount"));
			btnCheckAccount.setBackground(new Color(255, 204, 0));
			btnCheckAccount.setForeground(Color.DARK_GRAY);
			btnCheckAccount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					showPnCheckAccount();
				}
			});
			btnCheckAccount.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32));
		}
		return btnCheckAccount;
	}
	private JPanel getPnHomeNavLeft() {
		if (pnHomeNavLeft == null) {
			pnHomeNavLeft = new JPanel();
			pnHomeNavLeft.setOpaque(false);
			pnHomeNavLeft.setBackground(new Color(255, 153, 204));
			GroupLayout gl_pnHomeNavLeft = new GroupLayout(pnHomeNavLeft);
			gl_pnHomeNavLeft.setHorizontalGroup(
				gl_pnHomeNavLeft.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnHomeNavLeft.createSequentialGroup()
						.addGap(128)
						.addComponent(getBtnCheckAccount(), GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
						.addGap(70))
			);
			gl_pnHomeNavLeft.setVerticalGroup(
				gl_pnHomeNavLeft.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnHomeNavLeft.createSequentialGroup()
						.addContainerGap()
						.addComponent(getBtnCheckAccount(), GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addGap(22))
			);
			pnHomeNavLeft.setLayout(gl_pnHomeNavLeft);
		}
		return pnHomeNavLeft;
	}
	private JPanel getPnHomeNavRight() {
		if (pnHomeNavRight == null) {
			pnHomeNavRight = new JPanel();
			pnHomeNavRight.setOpaque(false);
			pnHomeNavRight.setBackground(new Color(255, 0, 102));
			GroupLayout gl_pnHomeNavRight = new GroupLayout(pnHomeNavRight);
			gl_pnHomeNavRight.setHorizontalGroup(
				gl_pnHomeNavRight.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnHomeNavRight.createSequentialGroup()
						.addGap(70)
						.addComponent(getBtnPlay(), GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
						.addGap(128))
			);
			gl_pnHomeNavRight.setVerticalGroup(
				gl_pnHomeNavRight.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnHomeNavRight.createSequentialGroup()
						.addContainerGap()
						.addComponent(getBtnPlay(), GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addGap(22))
			);
			pnHomeNavRight.setLayout(gl_pnHomeNavRight);
		}
		return pnHomeNavRight;
	}
	private JPanel getPnBase() {
		if (pnBase == null) {
			pnBase = new JPanel();
			pnBase.setName(CP_BASE);
			pnBase.setBackground(new Color(135, 206, 250));
			pnBase.setOpaque(false);
			pnBase.setLayout(new BorderLayout(0, 0));
			pnBase.add(getPnCardBase(), BorderLayout.CENTER);
			pnBase.add(getPnNorth(), BorderLayout.NORTH);
		}
		return pnBase;
	}
	private JLabel getLblLogoMedium() {
		if (lblLogoMedium == null) {
			lblLogoMedium = new JLabel("SUPER BINGO");
			lblLogoMedium.setForeground(new Color(255, 204, 0));
			lblLogoMedium.setBackground(new Color(255, 255, 255));
			lblLogoMedium.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 40));
		}
		return lblLogoMedium;
	}
	
	private JPanel getPnCardBase() {
		if (pnCardBase == null) {
			pnCardBase = new JPanel();
			pnCardBase.setOpaque(false);
			pnCardBase.setBackground(new Color(135, 206, 250));
			pnCardBase.setLayout(new CardLayout(0, 0));
			pnCardBase.add(getPnCheckAccount(), BASE_CA);
			pnCardBase.add(getPnTicketValidator(), BASE_TICKET);
			pnCardBase.add(getPnBingo(), BASE_BINGO);
			pnCardBase.add(getPnReward(), BASE_REWARD);
			pnCardBase.add(getPnNumMag(), BASE_NUMMAG);
			pnCardBase.add(getPnRegister(), BASE_REGISTER);
			pnCardBase.add(getPnTransition(), BASE_TRANSITION);
		}
		return pnCardBase;
	}
	public void showPnOfPnBase(String pnName) {
		((CardLayout) getPnCardBase().getLayout()).show(getPnCardBase(), pnName);
		
		getLblLogoMedium().setText("");
		ImageIcon icon = FactoryImage.loadImagen(FactoryImage.LOGO_MED);
		icon = FactoryImage.scaleImg(icon.getImage(), 350, 75);
		getLblLogoMedium().setIcon(icon);
		changeQuickButtons(false);
	}
	
	private PnCheckAccount getPnCheckAccount() {
		if (pnCheckAccount == null) {
			pnCheckAccount = new PnCheckAccount(this, localizer);
			pnCheckAccount.setName(BASE_CA);
			pnCheckAccount.setOpaque(false);
		}
		return pnCheckAccount;
	}
	public void showPnCheckAccount() {
		prevPane = currentPane;
		currentPane = getPnCheckAccount();
		showPnOfContentPane(CP_BASE);
		showPnOfPnBase(BASE_CA);
		getPnCheckAccount().setDefaultConfig();
		updateBtnHelpFuntion();
	}
	public void returnFromCAccount() {
		if(prevPane == null)
			showPnHome();
		else if(prevPane == getPnCheckAccount()) 
			backHome(true);
		else {
			showPnOfContentPane(CP_BASE);
			showPnOfPnBase(((JPanel) prevPane).getName());
			currentPane = prevPane;
			changeQuickButtons(false);
		}
		updateBtnHelpFuntion();
	}
	private PnTicketValidator getPnTicketValidator() {
		if (pnTicketValidator == null) {
			pnTicketValidator = new PnTicketValidator(this, localizer);
			pnTicketValidator.setName(BASE_TICKET);
			pnTicketValidator.setOpaque(false);
		}
		return pnTicketValidator;
	}
	public void showPnTicketValidator() {
		currentPane = getPnTicketValidator();
		showPnOfContentPane(CP_BASE);
		showPnOfPnBase(BASE_TICKET);
		getPnTicketValidator().setDefaultConfig();
		updateBtnHelpFuntion();
	}
	protected PnBingo getPnBingo() {
		if (pnBingo == null) {
			pnBingo = new PnBingo(this, localizer, bingo, ticketManager);
			pnBingo.setName(BASE_BINGO);
			pnBingo.setOpaque(false);
		}
		return pnBingo;
	}
	public void showPnBingo() {
		currentPane = getPnBingo();
		showPnOfContentPane(CP_BASE);
		showPnOfPnBase(BASE_BINGO);
		getPnBingo().setDefaultConfig();	
		setSize(getWidth(), getHeight()+1);
		setSize(getWidth(), getHeight()-1);
		updateBtnHelpFuntion();
		
		showTutorial();
	}
	public void showTutorial() {
		TutorialWindow tutorial = new TutorialWindow(this);
		tutorial.setLocationRelativeTo(this);
		tutorial.setVisible(true);
	}
	protected PnReward getPnReward() {
		if (pnReward == null) {
			pnReward = new PnReward(this, localizer, bingo);
			pnReward.setName(BASE_REWARD);
			pnReward.setOpaque(false);
		}
		return pnReward;
	}
	public void showPnReward() {
		currentPane = getPnReward();
		showPnOfContentPane(CP_BASE);
		showPnOfPnBase(BASE_REWARD);
		getPnReward().setDefaultConfig(bingo);
		updateBtnHelpFuntion();
	}
	protected PnNumMag getPnNumMag() {
		if (pnNumMag == null) {
			pnNumMag = new PnNumMag(this, localizer, clientManager);
			pnNumMag.setName(BASE_NUMMAG);
			pnNumMag.setOpaque(false);
		}
		return pnNumMag;
	}
	public void showPnNumMag() {
		currentPane = getPnNumMag();
		showPnOfContentPane(CP_BASE);
		showPnOfPnBase(BASE_NUMMAG);
		getPnNumMag().setDefaultConfig();
		updateBtnHelpFuntion();
	}
	protected PnRegister getPnRegister() {
		if (pnRegister == null) {
			pnRegister = new PnRegister(this, localizer, clientManager);
			pnRegister.setName(BASE_REGISTER);
			pnRegister.setOpaque(false);
		}
		return pnRegister;
	}
	public void showPnRegister(int type,String id) {
		currentPane = getPnRegister();
		showPnOfContentPane(CP_BASE);
		showPnOfPnBase(BASE_REGISTER);
		getPnRegister().setDefaultConfig(type, id);
		updateBtnHelpFuntion();
	}
	public int showDialogToSave(String idType, String id) {
		String msg = localizer.getLocateText("num.isRegMsg");
		String typeId = localizer.getLocateText("global."+idType);
		msg = String.format(msg, typeId, id);
		String title = localizer.getLocateText("num.isRegTitle");
		
		return JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	public void showAmountClientDialog(String id, boolean doTransition) {
		Client c = clientManager.getClient(id.trim());
		AmountClientDialog dialog = new AmountClientDialog(this, c, localizer);
		dialog.setModal(true);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}
	protected PnTransition getPnTransition() {
		if (pnTransition == null) {
			pnTransition = new PnTransition(localizer);
			pnTransition.setOpaque(false);
		}
		return pnTransition;
	}
	public void showPnTransition() {
		showPnOfContentPane(CP_BASE);
		showPnOfPnBase(BASE_TRANSITION);
		getPnTransition().setDefaultConfig();
		currentPane = getPnTransition();
	}
	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnGame());
			menuBar.add(getMnSettings());
			menuBar.add(getMnHelp());
		}
		return menuBar;
	}
	private JMenu getMnSettings() {
		if (mnSettings == null) {
			mnSettings = new JMenu("Settings");
			mnSettings.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 17));
			mnSettings.add(getItPreferences());
		}
		return mnSettings;
	}
	private JPanel getPnHomeContainer() {
		if (pnHomeContainer == null) {
			pnHomeContainer = new JPanel();
			pnHomeContainer.setOpaque(false);
			pnHomeContainer.setLayout(new BorderLayout(0, 0));
			pnHomeContainer.add(getPnHomeNav(), BorderLayout.SOUTH);
			pnHomeContainer.add(getLblLogoBig(), BorderLayout.NORTH);
			pnHomeContainer.add(getPnCentral(), BorderLayout.CENTER);
		}
		return pnHomeContainer;
	}
	private JPanel getPnNorth() {
		if (pnNorth == null) {
			pnNorth = new JPanel();
			pnNorth.setBorder(new EmptyBorder(0, 20, 0, 0));
			pnNorth.setOpaque(false);
			pnNorth.setLayout(new BorderLayout(0, 0));
			pnNorth.add(getLblLogoMedium());
			pnNorth.add(getPnBaseBtn(), BorderLayout.EAST);
		}
		return pnNorth;
	}
	private JPanel getPnBaseBtn() {
		if (pnBaseBtn == null) {
			pnBaseBtn = new JPanel();
			pnBaseBtn.setOpaque(false);
			pnBaseBtn.setLayout(new GridLayout(1, 0, 1, 0));
			pnBaseBtn.add(getBtnQuickCheckAcc());
			pnBaseBtn.add(getBtnLang());
			pnBaseBtn.add(getBtnHelp());
		}
		return pnBaseBtn;
	}
	private JButton getBtnLang() {
		if (btnLang == null) {
			btnLang = new JButton("");
			btnLang.setContentAreaFilled(false);
			btnLang.setBorderPainted(false);
			btnLang.setBackground(new Color(255, 102, 153));
			btnLang.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openSettings();
				}
			});
			ImageIcon icon = FactoryImage.loadImagen(FactoryImage.LANG_ICON);
			icon = FactoryImage.scaleImg(icon.getImage(), 50, 50);
			btnLang.setIcon(icon);
		}
		return btnLang;
	}
	private JButton getBtnHelp() {
		if (btnHelp == null) {
			btnHelp = new JButton("");
			btnHelp.setContentAreaFilled(false);
			btnHelp.setBorderPainted(false);
			btnHelp.setBackground(new Color(255, 102, 153));
			ImageIcon icon = FactoryImage.loadImagen(FactoryImage.HELP_ICON);
			icon = FactoryImage.scaleImg(icon.getImage(), 50, 50);
			getBtnHelp().setIcon(icon);
		}
		return btnHelp;
	}
	private JButton getBtnQuickCheckAcc() {
		if (btnQuickCheckAcc == null) {
			btnQuickCheckAcc = new JButton("");
			btnQuickCheckAcc.setContentAreaFilled(false);
			btnQuickCheckAcc.setBorderPainted(false);
			btnQuickCheckAcc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showPnCheckAccount();
				}
			});
		}
		ImageIcon icon = FactoryImage.loadImagen(FactoryImage.WALLET_ICON);
		icon = FactoryImage.scaleImg(icon.getImage(), 50, 50);
		btnQuickCheckAcc.setIcon(icon);
		return btnQuickCheckAcc;
	}
	private JButton getBtnOpenTutorial() {
		if (btnOpenTutorial == null) {
			btnOpenTutorial = new JButton("");
			btnOpenTutorial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showTutorial();
				}
			});
			btnOpenTutorial.setContentAreaFilled(false);
			btnOpenTutorial.setBorderPainted(false);
			ImageIcon icon = FactoryImage.loadImagen(FactoryImage.TUTORIAL_ICON);
			icon = FactoryImage.scaleImg(icon.getImage(), 50, 50);
			btnOpenTutorial.setIcon(icon);
			btnOpenTutorial.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.BOLD, 32));
		}
		return btnOpenTutorial;
	}	
	private void changeQuickButtons(boolean toHome) {
		if(toHome) {
			getPnBaseBtn().remove(getBtnLang());
			getPnBaseBtn().remove(getBtnHelp());

			getPnHomeBtn().add(getBtnLang());
			getPnHomeBtn().add(getBtnHelp());
		} else {
			getPnHomeBtn().remove(getBtnLang());
			getPnHomeBtn().remove(getBtnHelp());

			getPnBaseBtn().add(getBtnLang());
			getPnBaseBtn().add(getBtnHelp());
		}
		
		getBtnQuickCheckAcc().setVisible(!(currentPane == getPnCheckAccount()));
		if(currentPane == getPnBingo())
			getPnBaseBtn().add(getBtnOpenTutorial(), 2);
		else
			getPnBaseBtn().remove(getBtnOpenTutorial());
	}
	private JMenu getMnGame() {
		if (mnGame == null) {
			mnGame = new JMenu("Game");
			mnGame.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 17));
			mnGame.add(getItExit());
		}
		return mnGame;
	}
	private JMenu getMnHelp() {
		if (mnHelp == null) {
			mnHelp = new JMenu("Help");
			mnHelp.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 17));
			mnHelp.add(getItContents());
			mnHelp.add(getSeparator());
			mnHelp.add(getItAbout());
		}
		return mnHelp;
	}
	private JMenuItem getItExit() {
		if (itExit == null) {
			itExit = new JMenuItem("Exit");
			itExit.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 17));
			itExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(currentPane == null) {
						dispose();
						player.stop();
					} else 
						closingApp();
				}
			});
		}
		return itExit;
	}
	private JMenuItem getItPreferences() {
		if (itPreferences == null) {
			itPreferences = new JMenuItem("Preferences");
			itPreferences.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 17));
			itPreferences.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openSettings();
				}
			});
		}
		return itPreferences;
	}
	public void openSettings() {
		SettingsMenu frame = new SettingsMenu(this);
		frame.setLocationRelativeTo(this);
		frame.setVisible(true);
	}
	private JMenuItem getItContents() {
		if (itContents == null) {
			itContents = new JMenuItem("Help Contents");
			itContents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			itContents.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 17));
		}
		return itContents;
	}
	private JMenuItem getItAbout() {
		if (itAbout == null) {
			itAbout = new JMenuItem("About");
			itAbout.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 17));
			itAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showAbout();
				}
			});
		}
		return itAbout;
	}
	private void showAbout() {
		String msg = localizer.getLocateText("about.msg");
		String title = localizer.getLocateText("about.title");
		JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}
	private JPanel getPnHomeBt() {
		if (pnHomeBt == null) {
			pnHomeBt = new JPanel();
			pnHomeBt.setOpaque(false);
			pnHomeBt.setLayout(new BorderLayout(0, 0));
			pnHomeBt.add(getPnHomeBtn(), BorderLayout.EAST);
		}
		return pnHomeBt;
	}
	private JPanel getPnHomeBtn() {
		if (pnHomeBtn == null) {
			pnHomeBtn = new JPanel();
			pnHomeBtn.setOpaque(false);
			pnHomeBtn.setLayout(new GridLayout(0, 2, 2, 0));
			
		}
		return pnHomeBtn;
	}
	
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}
}