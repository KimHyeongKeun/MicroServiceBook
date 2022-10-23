package schoolprogram_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EAccount {

	private String id;
	private String password;
	private String name;
	private String adress;
	
	public EAccount() throws IOException {
		
		String id= "aaaaa\n";
		String password = "bbbb\n";
		String name = "keun\n";
		String adress = "Suji\n";
		
		//파일 객체 생성
		FileWriter fileWriter = new FileWriter("/Users/gimhyeong-geun/Desktop/Login.txt");

		this.id=id;
		this.password=password;
		
		fileWriter.write(this.id);
		fileWriter.write(this.password);
		fileWriter.write(name);
		fileWriter.write(adress);
		fileWriter.close();
		
	}

	public VLogin getLogin(String id) throws IOException   {
		//file read

		BufferedReader reader = new BufferedReader(
			new FileReader("/Users/gimhyeong-geun/Desktop/Login.txt")
		);
		String str;
		while((str= reader.readLine())!=null) {
			System.out.print(str);
		}
		reader.close();
		
		VLogin vLogin = new VLogin(id);  // Account에 있는 값들을 vLogin에 담는다.

		return vLogin;
	}

}
