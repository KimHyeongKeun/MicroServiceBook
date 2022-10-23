package schoolprogram_2;

import java.io.IOException;

public class SLogin {
	
	private EAccount eAccount;
	private VLogin vLogin;
	
	public SLogin() throws IOException {
		this.eAccount = new EAccount();
	}

	public VLogin login(String id) throws IOException {
		
	VLogin vLogin = this.eAccount.getLogin(id); //eAccount에서 ld를 찾아서 VLogin에게 담아주기
	//id하고 password줄테니 login info줘라
	
	if(vLogin!=vLogin) {
		return null;
	} 
	 else
		return vLogin;
	}
}
