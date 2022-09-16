package events.rewards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import gui.rewards.PnReward;
import logic.rewards.Reward;

public class ProcessRemoveReward implements ActionListener {
	private PnReward pnReward;
	
	public ProcessRemoveReward(PnReward pnReward) {
		this.pnReward = pnReward;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bt = (JButton) e.getSource();
		Reward r = pnReward.getCatalog().getRewardByCode(bt.getActionCommand());
		
		removeReward(bt, r);
	}
	
	private void removeReward(JButton button, Reward reward){
		pnReward.getSelector().remove(reward);
		
		pnReward.removeReward(button);
	}

}
