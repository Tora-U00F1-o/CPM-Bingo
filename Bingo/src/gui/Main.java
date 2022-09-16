package gui;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import gui.utils.Localizer;
import utils.log.Log;

public class Main {


	volatile static MainWindow window;
	
	/**
	 * Launch the application.
	 */	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
						public void uncaughtException(Thread t, Throwable e) {
							fatalErrorHandler(e);
						}
					});
					
					configLookAndFeel();
					
					window = new MainWindow();
					window.setVisible(true);
				} catch (Exception e) {	
					fatalErrorHandler(e);
				}
			}
		});

	}
	
	private static void fatalErrorHandler(Throwable e) {
		try {
			window.dispose();
			window.getPlayer().stop();
		} catch (Exception ex) { }
		Log.log(e);
		
		Localizer localizer = new Localizer();
		String msg = localizer.getLocateText("error.msg")+"\n >>"+e.getMessage();
		String title = localizer.getLocateText("error.title");
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	public static void configLookAndFeel() throws Exception {
    	UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	}
}
