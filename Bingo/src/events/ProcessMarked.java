package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import gui.game.PnBingo;
import gui.utils.FactoryImage;
import logic.game.Box;
import utils.exceptions.UserException;

public class ProcessMarked implements ActionListener {

	PnBingo pnBingo;
	
	public ProcessMarked(PnBingo pnBingo) {
		this.pnBingo = pnBingo;
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		int position = Integer.parseInt(arg0.getActionCommand());
		Box box = pnBingo.getBingo().getBoard().getBox(position);
		
		try {
			pnBingo.getBingo().mark(box.getRow(), box.getColumn());
		} catch (UserException e) {
			return;
		}

		JButton button = ((JButton) arg0.getSource());

		button.setEnabled(true);
		
		ImageIcon icon = FactoryImage.getImageBox(box);
		FactoryImage.setAdaptedImgButton(button, icon.getImage());
	}

	
}
