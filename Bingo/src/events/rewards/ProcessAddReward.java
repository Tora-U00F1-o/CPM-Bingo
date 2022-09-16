package events.rewards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import gui.MainWindow;
import gui.rewards.PnReward;
import gui.utils.Localizer;
import logic.rewards.Reward;
import utils.exceptions.UserException;

public class ProcessAddReward implements ActionListener{

	private MainWindow window;
	private PnReward pnReward;
	
	public ProcessAddReward(MainWindow window, PnReward pnReward) {
		this.window = window;
		this.pnReward = pnReward;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bt = (JButton) e.getSource();
		Reward r = pnReward.getCatalog().getRewardByCode(bt.getActionCommand());
		try {
			addReward(bt, r);
		} catch (UserException e1) {
			Localizer localizer = window.getLocalizer();
			String msg = localizer.getLocateText("par.msg");
			String title = localizer.getLocateText("par.title"); 
			JOptionPane.showMessageDialog(window, msg, title, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void addReward(JButton button, Reward reward) throws UserException {
		pnReward.getSelector().addReward(reward);
		
		pnReward.addRewardToBasket(reward);
		
	}

}
