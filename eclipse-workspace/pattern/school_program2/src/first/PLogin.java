package first;

import java.util.Scanner;

public class PLogin {

	private SLogin sLogin;
	public PLogin() {
		this.sLogin = new SLogin();

	}
	
	public VAccount login() {
		
		System.out.print("아이디를 입력하시오.");
		Scanner scanner = new Scanner(System.in);
		String id = scanner.next();
		System.out.print("비밀번호를 입력하세오.");
		String password = scanner.next();

		
		VAccount vAccount = this.sLogin.login(id,password); //eAccount에 ID를 가져오라는 것이다.
		

		scanner.close();

		return vAccount;
	}

}
