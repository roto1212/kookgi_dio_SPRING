package com.koreait.networkTest2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client2 {
	public static void main(String[] args) {
		
	Socket socket = null;
	PrintWriter  printWriter = null;
	Scanner scanSer = null;				// 서버에서 전송되는 데이터를 읽는 스캐너
	Scanner scanKey = null;				// 키보드로 서버로 전송할 데이터를 입력받는 스캐너

	
	try {
		System.out.println("192.168.0.2 서버의 10004번 포트로 접속 시도");
		socket = new Socket("192.168.0.2", 10004);
		System.out.println("서버에 접속 성공:" + socket);
		
		printWriter = new PrintWriter(socket.getOutputStream());
		scanSer = new Scanner(socket.getInputStream());
		scanKey = new Scanner(System.in);
		
		System.out.println(scanSer.nextLine());
		
//		키보드로 입력한 메시지 또는 서버에서 전송받은 메시지가 "BYE" 면 통신을 끝낸다.
		String msg = "";
		do {
//			서버로 전송할 메시지를 키보드로 입력받는다.
			System.out.print("client >> ");
			msg = scanKey.nextLine().trim();
//			키보드로 입력한 메시지를 서버로 전송한다.
			printWriter.write(msg + "\n");
			printWriter.flush();
//			키보드로 입력한 메시지가 "BYE"이면 => do while 반복을 탈출한다.
			if (msg.toUpperCase().equals("BYE")) {
				break;
			}
			
//			서버에서 전송받은 메시지를 출력한다. 
			msg = scanSer .nextLine().trim();
			System.out.println("server >>" + msg);
//			서버에서 전송받은 메시지가 "BYE"면 통신을 끝낸다 => do ~ while을 중지한다. => while()에서 조건을 만족하지 않게한다.
		} while (!msg.toUpperCase().equals("BYE"));

	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	}
}
