package first;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PMainFrame {

	private PLogin pLogin;
	
	public PMainFrame() {
		this.pLogin = new PLogin(); //이게 만들어 질 때 이미 화면이 만들어져야한다.
		
		
	}
	public void run() {
		//run이 시작되면 로그인이 시작되어야한다.
		
		
		VAccount vAccount = this.pLogin.login();
		if (vAccount==null) {
			System.out.println("정보 오류입니다.");
		}
		else {
			System.out.println("올바른 정보입니다.");

		}
	}

}
