package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Swing  {
 public static void main(String[] args) {
	 
	 JFrame frame = new JFrame();
	 frame.setLocation(300,200);
	 frame.setSize(600,400);
	 
	 JPanel panel = new JPanel();
	 frame.add(panel);
	 JButton b = new JButton("1");
	 JTextField text = new JTextField(10);
	 
//	 b.setBounds(40,40,40,40);
	 text.setBounds(120,10,200,30);
	 b.setBounds(10,10,10,10);
//	 panel.add(b);
	 panel.add(text);
	 panel.add(b);
	 text.getText();
	 frame.setVisible(true);
	 
	 
 }

} 