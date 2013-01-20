import javax.swing.JFrame;

public class main {
	public static void main(String[] args) {
		IHM gui = new IHM();
		gui.pack();
		gui.setVisible(true);
		gui.setSize(575, 650);
		gui.setResizable(false);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
