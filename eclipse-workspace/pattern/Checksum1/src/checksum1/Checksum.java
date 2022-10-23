package checksum1;


import java.util.Scanner;

public class Checksum {
	

	public void  a() {

		int poly;
		int temp;
		String polyString;
		String dataString;
		String intzero = null;
		int data;
		int binaryzero;
		char[] temp1 = new char[10000000];
		char[] temp2 = new char[10000000];
		char[] zeroarray = new char[100];
		char[] zerotemp = new char[100];
		char[] polyarray = new char[100];
		char[] dataarray = new char[100];
		char[] wholedataarray = new char[100000];
	//	String[] zeroarray = new String[100];
		char[] zeroa = new char[100];
	//	int[] polyarray = new int[100];
		int[] polya = new int[100];
	//	int[] dataarray = new int[100];
		int zero = 0;
		int[] adata = new int[100];
		int share;
		int num=0;
		int caldata;
		int[] a = new int[100];
		int[] temp_array = new int[100];
		int[] temp_realarray = new int[100];
		int count=0;
		String binary;
		int count1=0;
		int wholelength;
		
		
		Scanner sc =new Scanner(System.in);
		
		System.out.print("다항코드를 입력하시오: ");
		polyString=sc.next();	
		poly= Integer.parseInt(polyString,2); //다항코드
		
		
		System.out.print("데이터를 입력하시오: ");
		dataString=sc.next();	
		data = Integer.parseInt(dataString,2); //데이터 
		
		System.out.print("\n");
		System.out.print("데이터에 추가되는 0의 개수:");  //추가되는 0
		//추가되는 0구하기
		for(int i=0; i<polyString.length()-1;i++) {
			zeroa[i]='0';	
			System.out.print(zeroa[i]);
		}
		System.out.print("\n");
		System.out.print("\n");

		System.out.print("입력된 다항코드:"); 
		for(int v=0; v<polyString.length();v++) {  //다항코드 배열화
			polyarray[v] = polyString.charAt(v);
			System.out.print((polyarray[v]));  //문자열
			
		}
		
		System.out.print("\n");
		System.out.print("\n");
		System.out.print("입력된 데이터:"); 
		for(int i=0; i<dataString.length();i++) {  //데이터 배열화
			dataarray[i] = dataString.charAt(i);
			System.out.print((dataarray[i]));
		}
		
		System.out.print("\n");
		System.out.print("\n");
		wholelength = dataString.length()+polyString.length()-1;
		System.out.println("전체데이터의 길이:"+wholelength);
		System.out.print("\n");
		System.out.print("\n");
		System.out.print("전체 데이터:"); 
		
		for(int i=0; i<dataString.length()+polyString.length()-1;i++) {  //전체 데이터 배열화
			if(i<dataString.length()) {
			wholedataarray[i] = dataString.charAt(i);
			}
			else if(i>=dataString.length()) {
			wholedataarray[i] = '0';
			}
			count1++;
			
			System.out.print((wholedataarray[i]));
		}
		
		System.out.print("\n");
		System.out.print("\n");
		System.out.print("나머지0을 계산해야하는 경우:");
		
		//나눌 때 0으로 계산해야하는 경우
		for(int i=0; i<polyString.length();i++) {
			zerotemp[i]='0';	
			System.out.print((zerotemp[i]));
		}
		
		System.out.print("\n\n");
		
		System.out.print("나머지들");
		System.out.print("\n");

		
		
		for(int i=0; i<wholelength-polyString.length()+1;i++) {
			int flag=0;
			
			if(i==wholelength-polyString.length()) {
				System.out.print("\n");
				System.out.print("Checksum:");
				
			}
			
			for(int j=0+count; j<polyString.length()+count+1;j++) {
				  
				if(j==polyString.length()+count) {
					temp1[j] = wholedataarray[polyString.length()+count];
				}
				
				else {
				if(i==0) {   //맨 처음에만 data와 poly비교 
				//앞자리가 1인지 0인지 
				if(wholedataarray[0]=='1') {  //맨처음이 같으면 이것을 수행
					
				if(wholedataarray[j]==polyarray[j-count]) {
					temp1[j]='0';
					}
				else {
					temp1[j]='1';
					}
				 }
				else {
					if(wholedataarray[j]==zerotemp[j-count])
						temp1[j]='0';
					else
						temp1[j]='1';
					
					}
				}	
				
				else { //2번째 연산부터는 data와 temp(나머지)비교 
//					if(flag==1) {
						if(j==count) {      //j가 처음 시작점이면 
							if(temp1[count]=='1') {   //이번 검사하는 줄의 첫번째가 1이면 
								flag=1;
								if(polyarray[j-count]==temp1[j])
									temp1[j]='1';
								else {
									temp1[j]='1';
									}
								}
							else {
								if(temp1[j]==zerotemp[0]) {
									temp1[j]='0';
								}
								else {
									temp1[j]='1';
									}
								}	
						}

						else {
							if(temp1[i]=='1') {
								if(polyarray[j-count]==temp1[j])
									temp1[j]='0';
								else {
									temp1[j]='1';
									}	
								}
							else {
							if(temp1[j]==zerotemp[0]) {
								temp1[j]='0';
							}
							else {
								temp1[j]='1';
								}
							}
						}
					}
					
				}
				
			//	System.out.print(temp1[j]);
			}
			for(int k=1+count; k<polyString.length()+count+1;k++)
				System.out.print(temp1[k]);
			count++;
			System.out.print("\n");
		
			
		}
		
	
//		
//		for(int i=0;i<polyString.length();i++) {
//			
//			int binarynum = (int)temp1[i];
//			binary = String.valueOf(binarynum);
//			binary = Integer.toBinaryString(temp1[i]);
//			System.out.print(binary);
//		}
		
	}
		public static void main(String[] args) {
			
			Checksum cs = new Checksum();
			cs.a();
			
		}
	
		
		
	}


