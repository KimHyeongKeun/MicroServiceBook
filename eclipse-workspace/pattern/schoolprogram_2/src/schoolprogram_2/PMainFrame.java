package schoolprogram_2;

import java.io.IOException;

public class PMainFrame {

	//구성요소 
	private PLogin pLogin;
	
	public PMainFrame() throws IOException {
		this.pLogin = new PLogin();   //pLogin화면 만들기
	}

	public void show() throws IOException {
		this.pLogin.show(); //화면에 로그인 보여주기
		
	}
	

}
