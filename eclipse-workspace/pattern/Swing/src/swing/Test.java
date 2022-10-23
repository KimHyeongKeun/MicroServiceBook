package swing;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Test {
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		JButton b1 = new JButton();
		b1.setBounds(100,100,100,40);
		
		frame.add(b1);
		frame.setSize(300,500);
		frame.setLayout(null);
		frame.setVisible(true);
		
	}

	
}
