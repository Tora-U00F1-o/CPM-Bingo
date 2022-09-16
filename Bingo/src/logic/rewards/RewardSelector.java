package logic.rewards;

import java.util.ArrayList;
import java.util.List;

import gui.utils.Localizer;
import logic.game.Bingo;
import utils.CodeUtil;
import utils.exceptions.UserException;

public class RewardSelector {

	private final static int DEF_UDS_TO_ADD = 1;
	private List<Reward> rewardsList;
	private int[] score;
	
	private Localizer localizer;
	
	public RewardSelector(int [] score, Localizer localizer) {
		rewardsList = new ArrayList<Reward>();
		this.score = score;
		
		this.localizer = localizer;
	}
	
	public void translate() {
		List<Reward> rewardsListCopy = rewardsList;
		for(Reward r: rewardsList) {
			int scoreType = r.getPrice() == Reward.LINE_CHAR ? 
					Bingo.POS_N_LINES : Bingo.POS_N_BINGO;
			score[scoreType] += r.getUds();
		}
		rewardsList.clear();
			
		for(Reward r: rewardsListCopy) {
			for(int i= 0; i<r.getUds(); i++) {
				try {
					addReward(r);
				} catch (UserException e) {	}
			}
		}
	}
	
	public void addReward(Reward chosenReward) throws UserException {
		int scoreType = chosenReward.getPrice() == Reward.LINE_CHAR ? 
				Bingo.POS_N_LINES : Bingo.POS_N_BINGO;
			
		if(score[scoreType] < DEF_UDS_TO_ADD) //If can NOT get it
			throw new UserException();
		
		Reward rewardToAdd = null;
		for(Reward rewardInList: rewardsList)
			if(rewardInList.equals(chosenReward))
				rewardToAdd = rewardInList;
		
		if(rewardToAdd!=null) {
			int udsTotales = rewardToAdd.getUds() + DEF_UDS_TO_ADD;
			rewardToAdd.setUds(udsTotales);
			score[scoreType] -= DEF_UDS_TO_ADD;
			return;
		}
		
		rewardsList.add(new Reward(chosenReward, DEF_UDS_TO_ADD));
		score[scoreType] -= DEF_UDS_TO_ADD;
	}
	
	public void remove(Reward chosenReward) {
		Reward rewardToRmv = null;
		
		for(Reward rewardInList: rewardsList)
			if(rewardInList.equals(chosenReward))
				rewardToRmv = rewardInList;
		
		if (rewardToRmv != null){
			int totalUnidades = rewardToRmv.getUds() - DEF_UDS_TO_ADD;
			if (totalUnidades>0) 
				rewardToRmv.setUds(totalUnidades);
			else
				rewardsList.remove(rewardToRmv);	
		}
		int scoreType = chosenReward.getPrice() == Reward.LINE_CHAR ? 
				Bingo.POS_N_LINES : Bingo.POS_N_BINGO;
		score[scoreType] += DEF_UDS_TO_ADD;
	}

	public int getUnidades(Reward premiodeCatalogo) {
		Reward premio = null;
		for(Reward premioEnInventario: rewardsList)
			if(premioEnInventario.equals(premiodeCatalogo))
				premio = premioEnInventario;
		
		return (premio == null) ? 0 : premio.getUds();
		
	}
	
	public Reward[] getRewards() {
		return (Reward[]) rewardsList.toArray(new Reward[rewardsList.size()]);
	}
	
	public boolean isInventarioVacio() {
		return rewardsList.size() == 0;
	}
	
	public String toString() {
		if(isInventarioVacio())
			return "No has elegido ningun regalo";
		
		StringBuffer buffer = new StringBuffer();
		for(Reward p: rewardsList)
			buffer.append(p.toString()+"\n");
		return buffer.toString();
	}

	public void printTicket() {
		String n = "\n";
		StringBuffer buffer = new StringBuffer();
		buffer.append("_____________________________________"+n);
		buffer.append(" El Corte Asturiano"+n);
		buffer.append("_____________________________________"+n);
		buffer.append(" * "+localizer.getLocateDate() +" : " +localizer.getLocateTime() +n);
		buffer.append(" * "+"Terminal n"+CodeUtil.getRandomNumber(1, 10) +n);
		buffer.append("-------------------------------------"+n);
		// cabecera lista
		buffer.append(localizer.getLocateText("rew.ticket.head")+n);
		buffer.append("-------------------------------------"+n);
		String msg = String.format("   Id    Uds  %s ", localizer.getLocateText("rew.ticket.list.reward"));
		buffer.append(msg +n);
		buffer.append("-------------------------------------"+n);
		for(Reward r: rewardsList) {
			String patron = " - %s  x%d   %s";
			msg = String.format(patron,r.getCode(), r.getUds(), r.getDenomination());
			buffer.append(msg+n);
		}
		buffer.append("-------------------------------------"+n);
		buffer.append(localizer.getLocateText("rew.ticket.goodbyeMsg")+n);
		buffer.append("_____________________________________");
		
		System.out.println(buffer.toString());
	}
	
	
	public void inicialize(int [] score) {
		rewardsList.clear();
		this.score = score;	
	}
}