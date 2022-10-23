package first;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EAccount {

	private String Id;
	private String password;
	private String name;
	
	
	public EAccount() {
		
	}
	
	private boolean read(String id, String password) {  //데이터파일에서 정보 읽어오기
		boolean found = false;
		try {
			File file = new File("data/account");
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext() && !found) {
				this.Id = scanner.next();
				this.password= scanner.next();
				this.name = scanner.next();
				
				if(this.Id.compareTo(id)==0 && this.password.compareTo(password)==0) {
					found = true;
				}
			}
			
			scanner.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return found;
	}
	public VAccount getAccount(String id, String password) {
		VAccount vAccount = null;
		//file read
		boolean found = this.read(id,password);
		if(found) {
			vAccount = new VAccount();
			vAccount.setId(this.Id);
			vAccount.setPassword(this.password);
			vAccount.setName(this.name);		
	}
		return vAccount;
	}
	
}
