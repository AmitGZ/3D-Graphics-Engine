
import java.awt.FlowLayout;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Panel panel;

	MainFrame() {
		// creating frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setResizable(false);

		// creating main panel
		panel = new Panel();
		add(panel);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		panel.start();
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}