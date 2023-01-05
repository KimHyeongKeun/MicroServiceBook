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
			File file = new File("account/account");  // 파일 생성 
			Scanner scanner = new Scanner(file);  // 파일 읽기 
			while(scanner.hasNext() && !found) {  //hasnext는 boolean타입으로 반환된다. True or False로 반환된다.
				this.Id = scanner.next(); //next가 공백전까지 읽으니깐 id가 제일 처음        
				this.password= scanner.next(); //공백 다음이 password 
				this.name = scanner.next(); //마지막이 이름 
				
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
		boolean found = this.read(id,password);//id, pw 읽어들이기 
		if(found) {
			vAccount = new VAccount(); //found면 VAccount만들기 
			vAccount.setId(this.Id);  //setID하
			vAccount.setPassword(this.password);
			vAccount.setName(this.name);		
	}
		return vAccount;
	}
	
//	public VAccount getname(String name) {
//		VAccount vAccount = null;
// 
//			vAccount = new VAccount(); //found면 VAccount만들기 
//			vAccount.setName(this.name);		
//		
//		return vAccount;
//	}
	
}
