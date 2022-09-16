package logic.rewards;

import java.util.ArrayList;
import java.util.List;

import gui.utils.Localizer;
import utils.fileUtils.FileUtil;

public class Catalog {

	public final static String SPANISH_ID = "es";
	public final static String ENGLISH_ID = "en";
	
	public final static int CODE_PARSE_POS = 0;
	public final static int DENOM_PARSE_POS = 1;
	public final static int PRICE_PARSE_POS = 2;
	
	private Localizer localizer;
	private List<Reward> rewardList;
	
	public Catalog(Localizer localizer) {
		rewardList = new ArrayList<Reward>();
		this.localizer = localizer;
		parseData();
	}
	
	public void translate() {
		rewardList.clear();
		parseData();
	}
	
	public Reward[] getRewards() {
		return (Reward[]) rewardList.toArray(new Reward[rewardList.size()]);
	}
	
	public Reward getRewardByCode(String code) {
		for(Reward r: rewardList)
			if(r.getCode().equals(code))
				return r;
		return null;
	}
	
	private void parseData() {
		String fileName = localizer.getLocateText("rew.catalog.fileData");
		List<String[]> data = FileUtil.loadFile(fileName, "Catalog.parseData");
		
		if(data.size() == 0)
			throw new RuntimeException("[Fichero de premios vacio]");
		
		for(String[] line: data) {
			String code = line[CODE_PARSE_POS];
			String denomination = line[DENOM_PARSE_POS];
			char price = line[PRICE_PARSE_POS].charAt(0);
			rewardList.add(new Reward(code, denomination, price, 0));
		}
	}
}
