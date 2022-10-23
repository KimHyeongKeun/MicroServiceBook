package first;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MainFrame extends JFrame {  //JFrame을 메인프레임에 확장해서 만들었다.
	private static final long serialVersionUID = 1L; //지금은 serialversionid만 있다. 

	public MainFrame() {
		
		this.setSize(400,600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		LayoutManager layoutManager = new BorderLayout();
		this.setLayout(layoutManager);
		
	
		
		SugangsincheongPanel sugangsincheongPanel = new SugangsincheongPanel();
		this.add(sugangsincheongPanel,BorderLayout.CENTER);
		
		LoginDialog loginDialog = new LoginDialog(this);
		loginDialog.setVisible(true);
	}

}
