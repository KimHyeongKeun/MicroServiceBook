package first;

import java.util.Scanner;

public class SLogin {

	private EAccount eAccount;
	
	public SLogin() {
		this.eAccount = new EAccount(); //EAccount로 넘어갈 수 있는 eAccount
	}
	
	public VAccount login(String id, String password) {
		
		VAccount vAccount = this.eAccount.getAccount(id,password); //eAccount로 부터 id,password가져오기
		
		return vAccount;
		
	}
	
	

}
