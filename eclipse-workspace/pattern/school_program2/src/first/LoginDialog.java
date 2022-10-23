package first;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;

	private JTextField tfid;
	private JPasswordField tfPassword;
	private SLogin sLogin;
	
	
	public LoginDialog(Frame parent) {
		super(parent);
		this.setSize(200,200);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.blue);
		panel1.setSize(100,100);
		this.add(panel1);
		
		JPanel panel2 = new JPanel();
		panel1.setBackground(Color.red);
		panel2.setSize(100,100);
		this.add(panel2);
		
		LayoutManager layoutManager1 = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layoutManager1);
		
		this.setSize(200,200);
		this.setTitle("login");
		LayoutManager layoutManager = new FlowLayout();
		this.setLayout(layoutManager);
		
//		JLabel lbTitle = new JLabel("로그인");            
//		this.add(lbTitle);
   
		JLabel lbId=new JLabel("아이디");    
		this.add(lbId);
		
		this.tfid = new JTextField();  
		tfid.setColumns(10);
		this.add(tfid);
		
		JLabel lbPassword=new JLabel("비밀번호"); 
		this.add(lbPassword);
		
		this.tfPassword = new JPasswordField();   
		tfPassword.setColumns(10);
		this.add(tfPassword);
		
		JButton bt1 = new JButton("Login"); 
		this.add(bt1);
		
		ActionHandler actionHandler = new ActionHandler();
		bt1.addActionListener(actionHandler);
		
		this.sLogin = new SLogin();
	}
	
	 void login() {
		String id = this.tfid.getText();
		String password = new String(this.tfPassword.getPassword());
		VAccount vAccount = this.sLogin.login(id,password); 
		if (vAccount==null) {
			System.out.println("정보 오류입니다.");
			this.setSize(400,400);
			this.setVisible(true);
			JOptionPane.showMessageDialog(null, "정보가 다릅니다.");
		}
		else {
			System.out.println("올바른 정보입니다.");
			this.setSize(400,400);
			this.setVisible(true);
			JOptionPane.showMessageDialog(null, "환영합니다, kim님");

		}
	}
	
	 
	private class ActionHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			login();
			
		}
		
	}

}
