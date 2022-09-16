package gui.transition;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import gui.utils.BingoPanel;
import gui.utils.Localizer;

import java.awt.GridLayout;
import java.awt.Color;

public class PnTransition extends JPanel implements BingoPanel {
	
	private static final long serialVersionUID = 1L;

	private Localizer localizer;
	
	private JLabel lblHead;

	/**
	 * Create the panel.
	 */
	public PnTransition(Localizer localizer) {
		this.localizer = localizer;
		
		setLayout(new GridLayout(0, 1, 0, 0));
		add(getLblHead());

	}
	
	public void setDefaultConfig() {
		translate();
	}
	public void translate() {
		getLblHead().setText(localizer.getLocateText("transition.head"));
	}

	private JLabel getLblHead() {
		if (lblHead == null) {
			lblHead = new JLabel("COME BACK SOON");
			lblHead.setForeground(new Color(255, 204, 0));
			lblHead.setHorizontalAlignment(SwingConstants.CENTER);
			lblHead.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.BOLD, 64));
		}
		return lblHead;
	}
}
