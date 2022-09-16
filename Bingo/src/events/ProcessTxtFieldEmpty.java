package events;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class ProcessTxtFieldEmpty implements FocusListener{

	private JLabel infoLbl;
	
	public ProcessTxtFieldEmpty(JLabel infoLbl) {
		this.infoLbl = infoLbl;
	}
	
	
	@Override
	public void focusGained(FocusEvent arg0) {
	}

	@Override
	public void focusLost(FocusEvent e) {
		infoLbl.setVisible(((JTextField) e.getSource()).getText().trim().isEmpty());
		
	}

	
	
	

}
