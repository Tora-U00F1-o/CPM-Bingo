package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ProcessTxtFieldEnter implements ActionListener{

	private JButton button;
	
	public ProcessTxtFieldEnter(JButton button) {
		this.button = button;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		button.requestFocus();
	}

}
