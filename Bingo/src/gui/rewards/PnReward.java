package gui.rewards;

import javax.swing.JPanel;

import gui.MainWindow;
import gui.utils.BingoPanel;
import gui.utils.FactoryImage;
import gui.utils.Localizer;
import logic.game.Bingo;
import logic.rewards.Catalog;
import logic.rewards.Reward;
import logic.rewards.RewardSelector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.border.TitledBorder;

import events.rewards.ProcessAddReward;
import events.rewards.ProcessRemoveReward;

import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PnReward extends JPanel implements BingoPanel {
	
	private static final long serialVersionUID = 1L;
	
	private MainWindow window;
	private Localizer localizer;
	private Bingo bingo;
	private int [] score;
	private RewardSelector selector;
	private Catalog catalog;
	
	private ProcessAddReward addReward;
	private ProcessRemoveReward rmvReward;
	
	private JScrollPane spReward;
	private JPanel pnRewardContainer;
	private JPanel pnUtils;
	private JPanel pnScore;
	private JLabel lblNLines;
	private JLabel lblNBingo;
	private JPanel pnBasket;
	private JScrollPane spBasket;
	private JPanel pnBasketContainer;
	
	private JPanel pnBtnNavigation;
	private JButton btnContinue;
	private JButton btnExit;
	/**
	 * Create the panel.
	 */
	public PnReward(MainWindow window, Localizer localizer, Bingo bingo) {
		setBackground(new Color(51, 204, 255));
		this.window = window;
		this.localizer = localizer;
		this.bingo = bingo;
		score = bingo.getScore();
		
		selector = new RewardSelector(bingo.getScore(), localizer);
		catalog = new Catalog(localizer);
		
		addReward = new ProcessAddReward(window, this);
		rmvReward = new ProcessRemoveReward(this);
		
		setLayout(new BorderLayout(5, 0));
		add(getSpReward(), BorderLayout.CENTER);
		add(getPnUtils(), BorderLayout.EAST);
		
	}
	public void setDefaultConfig(Bingo bingo) {
		this.bingo = bingo;
		score = bingo.getScore();
		selector.inicialize(score);

		translate();
		
		getPnBasketContainer().removeAll();
		showRewards();
		setRewardIcons();
		
		updateScore();
	}
	public void translate() {
		catalog.translate();
		selector.translate();
		
		window.setTitle(localizer.getLocateText("rew.title"));
		((TitledBorder) getSpReward().getBorder()).setTitle(localizer.getLocateText("rew.catalog.title"));
		((TitledBorder) getPnScore().getBorder()).setTitle(localizer.getLocateText("rew.pnScoreTitle"));
		((TitledBorder) getPnBasket().getBorder()).setTitle(localizer.getLocateText("rew.pnBasketTitle"));
		getBtnContinue().setText(localizer.getLocateText("global.btnContinue"));
		getBtnContinue().setMnemonic(localizer.getLocateChar("global.btnContinue.mn"));
		getBtnExit().setText(localizer.getLocateText("global.btnExit"));
		getBtnExit().setMnemonic(localizer.getLocateChar("global.btnExit.mn"));
		translateRewards();
	}
	private void translateRewards() {
		for(Component c: getPnRewardContainer().getComponents()) {
			JButton b = (JButton) c;
			Reward r = catalog.getRewardByCode(b.getActionCommand());
			b.setText(getInfoReward(r));
			String msg = localizer.getLocateText("rew.btnReward.tt");
			b.setToolTipText( String.format(msg, r.getDenomination()));
		}
	}
	public RewardSelector getSelector() {
		return selector;
	}
	public Catalog getCatalog() {
		return catalog;
	}
	private void showRewards() {
		getPnRewardContainer().removeAll();
		for(int pos = 0; pos < catalog.getRewards().length; pos++)
			getPnRewardContainer().add(newRewardButton(pos));
	}
	private JButton newRewardButton(int pos) {
		Reward a = catalog.getRewards()[pos];
		JButton button = new JButton(getInfoReward(a));
		button.setFont(new Font("Tahoma", Font.PLAIN, 17));
		button.setBackground(Color.white);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		String msg = localizer.getLocateText("rew.btnReward.tt");
		button.setToolTipText( String.format(msg, a.getDenomination()));
		
		button.addActionListener(addReward);
		button.setActionCommand(a.getCode());
		
		return button;
	}
	private void setRewardIcons() {
		int pos = 0;
		for(Component c: getPnRewardContainer().getComponents()) {
			JButton button = (JButton) c;
			try {
				ImageIcon icon = FactoryImage.getImageReward(catalog.getRewards()[pos]);
				FactoryImage.setAdaptedImgButton(button, icon.getImage());
				
			} catch (IllegalArgumentException e) {
				button.setIcon(FactoryImage.getImageReward(catalog.getRewards()[pos]));
			}
			pos++;
		}
	}
	
	private JScrollPane getSpReward() {
		if (spReward == null) {
			spReward = new JScrollPane();
			spReward.setForeground(new Color(0, 0, 153));
			spReward.setBorder(new TitledBorder(null, "Catalog", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 32), null));
			((TitledBorder) spReward.getBorder()).setTitleColor(new Color(0, 0, 153));
			spReward.setOpaque(false);
			spReward.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			spReward.getVerticalScrollBar().setUnitIncrement(15);
			spReward.setViewportView(getPnRewardContainer());
		}
		return spReward;
	}
	private JPanel getPnRewardContainer() {
		if (pnRewardContainer == null) {
			pnRewardContainer = new JPanel();
			pnRewardContainer.setBackground(new Color(51, 204, 255));
			pnRewardContainer.setLayout(new GridLayout(0, 3, 3, 3));
		}
		return pnRewardContainer;
	}
	private JPanel getPnUtils() {
		if (pnUtils == null) {
			pnUtils = new JPanel();
			pnUtils.setOpaque(false);
			pnUtils.setLayout(new BorderLayout(0, 0));
			pnUtils.add(getPnScore(), BorderLayout.NORTH);
			pnUtils.add(getPnBasket());
			pnUtils.add(getPnBtnNavigation(), BorderLayout.SOUTH);
		}
		return pnUtils;
	}
	private JPanel getPnScore() { 
		if (pnScore == null) {
			pnScore = new JPanel();
			pnScore.setOpaque(false);
			pnScore.setBorder(new TitledBorder(null, "Catalog", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 22), null));
			((TitledBorder) pnScore.getBorder()).setTitleColor(new Color(0, 0, 153));
			pnScore.setLayout(new GridLayout(0, 1, 0, 0));
			pnScore.add(getLblNLines());
			pnScore.add(getLblNBingo());
		}
		return pnScore;
	}
	private void updateScore() {
		String txt;
		int nLines = score[Bingo.POS_N_LINES];
		switch(nLines) {
		case 0:
			txt = localizer.getLocateText("rew.scrLine0");
			break;
		case 1:
			txt = localizer.getLocateText("rew.scrLine");
			txt = String.format(txt, nLines);
			break;
		default:
			txt = localizer.getLocateText("rew.scrLines");
			txt = String.format(txt, nLines);
			break;
		}
		getLblNLines().setText(txt);
		
		int nBingo = score[Bingo.POS_N_BINGO];
		switch(nBingo) {
		case 0:
			txt = localizer.getLocateText("rew.scrBingo0");
			break;
		default:
			txt = localizer.getLocateText("rew.scrBingo");
			txt = String.format(txt, nBingo);
			break;
		}
		getLblNBingo().setText(txt);
	}
	private JLabel getLblNLines() {
		if (lblNLines == null) {
			lblNLines = new JLabel("N Lines");
			lblNLines.setBorder(new EmptyBorder(0, 15, 0, 0));
			lblNLines.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 21));
		}
		return lblNLines;
	}
	private JLabel getLblNBingo() {
		if (lblNBingo == null) {
			lblNBingo = new JLabel("N Bingo");
			lblNBingo.setBorder(new EmptyBorder(0, 15, 0, 0));
			lblNBingo.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 21));
		}
		return lblNBingo;
	}
	private JPanel getPnBasket() {
		if (pnBasket == null) {
			pnBasket = new JPanel();
			pnBasket.setOpaque(false);
			pnBasket.setBorder(new TitledBorder(null, "Catalog", TitledBorder.LEADING, TitledBorder.TOP, new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 22), null));
			((TitledBorder) pnBasket.getBorder()).setTitleColor(new Color(0, 0, 153));
			pnBasket.setLayout(new BorderLayout(0, 0));
			pnBasket.add(getSpBasket());
		}
		return pnBasket;
	}
	private JScrollPane getSpBasket() {
		if (spBasket == null) {
			spBasket = new JScrollPane();
			spBasket.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			spBasket.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			spBasket.setViewportView(getPnBasketContainer());
		}
		return spBasket;
	}
	private JPanel getPnBasketContainer() {
		if (pnBasketContainer == null) {
			pnBasketContainer = new JPanel();
			pnBasketContainer.setBackground(new Color(224, 255, 255));
			pnBasketContainer.setBorder(null);
			pnBasketContainer.setLayout(new GridLayout(10, 1, 0, 0));
		}
		return pnBasketContainer;
	}
	
	public void addRewardToBasket(Reward reward) {
		JPanel p = getNewArticle(reward);
		getPnBasketContainer().add(p);
		getPnBasketContainer().validate();
		
		updateScore();
	}
	private JPanel getNewArticle(Reward reward) {
		JPanel pnar = new JPanel();
		pnar.setBorder(new EmptyBorder(0, 0, 0, 20));
		pnar.setLayout(new BorderLayout(7, 0));
		pnar.add(getLblName(reward), BorderLayout.CENTER);
		pnar.add(getBtnDelete(reward), BorderLayout.EAST);
		pnar.setOpaque(false);
		
		return pnar;
	}
	private JLabel getLblName(Reward reward) {
		JLabel lblName = new JLabel(" ● " +getInfoReward(reward));
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 17));
		return lblName;
	}
	private JButton getBtnDelete(Reward reward) {
		JButton btndelete = new JButton("X");
		btndelete.setToolTipText(localizer.getLocateText("rew.btnDelete.tt"));
		btndelete.setFont(new Font("Tahoma", Font.BOLD, 17));
		btndelete.setBackground(new Color(220, 20, 60));
		btndelete.setForeground(Color.WHITE);
		btndelete.addActionListener(rmvReward);
		btndelete.setActionCommand(reward.getCode());
		return btndelete;
	}
	public void removeReward(JButton button) {
		for(Component comp: getPnBasketContainer().getComponents()) {
			JPanel pn = (JPanel) comp;
			for(Component btn: pn.getComponents())
				if(btn instanceof JButton)
					if(((JButton) btn).equals(button)) {
						getPnBasketContainer().remove(comp);
						break;
					}
		}
		getPnBasketContainer().validate();
		getPnBasketContainer().repaint();
		updateScore();
	}
	private JPanel getPnBtnNavigation() {
		if (pnBtnNavigation == null) {
			pnBtnNavigation = new JPanel();
			pnBtnNavigation.setOpaque(false);
			pnBtnNavigation.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnBtnNavigation.add(getBtnContinue());
			pnBtnNavigation.add(getBtnExit());
		}
		return pnBtnNavigation;
	}
	private JButton getBtnContinue() {
		if (btnContinue == null) {
			btnContinue = new JButton("continue");
			btnContinue.setForeground(new Color(255, 255, 255));
			btnContinue.setBackground(new Color(0, 153, 0));
			btnContinue.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.BOLD, 27));
			btnContinue.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(confirmRewards() != JOptionPane.YES_OPTION)
						return;
					selector.printTicket();
					if(bingo.hasmagicNumber())
						window.showPnNumMag();
					else
						window.backHome(true);
				}
			});
		}
		return btnContinue;
	}
	private int confirmRewards() {
		String msg = localizer.getLocateText("rew.confirmMsg");
		String title = localizer.getLocateText("rew.confirmTitle");
		
		return JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("exit");
			btnExit.setForeground(new Color(255, 255, 255));
			btnExit.setBackground(new Color(255, 0, 0));
			btnExit.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.BOLD, 27));
			btnExit.addActionListener(window.getProcessExit());
		}
		return btnExit;
	}
	
	private String getInfoReward(Reward reward) {
		String info = reward.toString();
		info = info.substring(0, info.length()-1);
		String priceType = reward.getPrice() == Reward.LINE_CHAR ?
				localizer.getLocateText("rew.line"): localizer.getLocateText("rew.bingo");
		return (info +priceType);
	}
}
