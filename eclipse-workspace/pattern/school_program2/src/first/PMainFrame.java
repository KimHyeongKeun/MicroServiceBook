package first;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


//public JFrame() {
//	
//	private SugangsincheongPanel sugangsincheongPanel;
//	
//}  이렇게 만들어서 우리가 add라는 함수를 사용할 수 있고 여기에 private  SugangsincheongPanel sugangsincheongPane을 달아서 사용한다.

public class PMainFrame extends JFrame {  //JFrame을 메인프레임에 확장해서 만들었다.
	private static final long serialVersionUID = 1L; //지금은 serialversionid만 있다. 

	private PAccountPanel accountPanel;
	private PSugangsincheongPanel sugangsincheongPanel;
	private String name;
	private VAccount vAccount;
	
	public void setVAccount(VAccount vAccount) {
		this.vAccount = vAccount;
	}
	
	public PMainFrame() {
		
		//attribute
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(size.width/2-this.getWidth(),20);
		this.setSize(800,800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		PLoginDialog loginDialog = new PLoginDialog(this); //부모가 누군지를 알 정도만 한다. 다이얼로그만 특수한 경우다.
		loginDialog.setVisible(true);
		
		
		LayoutManager layoutManager = new BorderLayout();
		this.setLayout(layoutManager);
		
		
		//login account에서는 value account를 가져올 수 있어야한다. 로그인을 시작했을 때 value object만 전달 할 수 있어야한다 .
		this.accountPanel = new PAccountPanel(this.vAccount);
		this.add(this.accountPanel,BorderLayout.NORTH);
		//component
		this.sugangsincheongPanel = new PSugangsincheongPanel();
		this.add(sugangsincheongPanel,BorderLayout.CENTER);
		
		
	}

}
