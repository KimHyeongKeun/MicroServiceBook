package schoolprogram_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PLogin {
	
	private SLogin sLogin;
	
	public PLogin() throws IOException {
		this.sLogin = new SLogin();
	}

	public void show() throws IOException {
			

			System.out.print("아이디 입력하세요.");
			
			Scanner scanner = new Scanner(System.in); //System.in 키보드 Scanner가 우리가 아스키 캐릭터들을 integer,float등으로 바꿔준 것이다.
			String id = scanner.next();
			
			//System.out.print("비밀번호를 입력하세요.");
			//String password = scanner.next();

		    EAccount EAccount = this.sLogin.login(id); //sLogin한테 아이디를 주고 로그인 할 수 있게 한다.
		    //VLogin은 value로그인        Value object를 받아서 다시 pLogin에게 전달
			scanner.close();
			
	}

}
