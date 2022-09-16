package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import gui.MainWindow;
import gui.utils.Localizer;

public class ProcessBackHome implements ActionListener{

	private MainWindow window;
	private JDialog dialog;
	private Localizer localizer;

	private boolean makeConfirmation;
	private boolean makeTransition;
	
	public ProcessBackHome(MainWindow window, JDialog dialog, boolean makeTransition) {
		this.window = window;
		this.dialog = dialog;
		this.makeTransition = makeTransition;
	}
	
	
	public ProcessBackHome(MainWindow window, JDialog dialog, boolean makeTransition, boolean makeConfirmation, Localizer localizer) {
		this(window, dialog, makeTransition);
		
		this.localizer = localizer;
		this.makeConfirmation = makeConfirmation;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(makeConfirmation) {
			String msg = localizer.getLocateText("pbh.msg");
			String title = localizer.getLocateText("pbh.title");
			
			int choose = JOptionPane.showConfirmDialog(window, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(JOptionPane.YES_OPTION != choose) 
				return;
			
		}
		if(dialog != null)
			dialog.dispose();
		
		window.backHome(makeTransition);
	}
}
