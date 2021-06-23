package renderer;

import javax.swing.JFrame;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public Frame() {
		super("Window");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(0, 0, 1300, 1000);
		setLocationRelativeTo(null);
	}
}