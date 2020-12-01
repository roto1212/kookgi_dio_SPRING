package com.koreait.networkTest3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server4 extends Frame implements ActionListener, Runnable{
	JLabel chatArea;					// 채팅 내용이 출력될 영역
	JPanel panel;						// 대화 내용이 입력하는 텍스트 에어리어와 전송 버튼이 올라갈 패널
	JTextField insert;					// 채팅 입력창
	JButton button;						// 입력한 채팅 내용을 전송하는 버튼
	
	ServerSocket serverSocket;
	Socket socket;
	PrintWriter printWriter;
	Scanner scanClie;
	String msg ="";						// 서버와 클라이언트의 대화 내용을 저장했다가 대화 내용이 출력되는 텍스트 영역에 뿌려줄 때 사용할 변수
	
	public Server4() {
		setTitle("1:1 채팅프로그램 - 서버");
		setBounds(500, 50, 500, 700);
	
//		채팅창 만들기
		chatArea = new JLabel();
		chatArea.setOpaque(true);
		chatArea.setBackground(Color.orange);
		chatArea.setFont(new Font("D2Coding", Font.BOLD, 15));
		chatArea.setVerticalAlignment(JLabel.BOTTOM);
		add(chatArea);
		
		panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(500, 40));
		insert = new JTextField();
		panel.add(insert);
		button = new JButton("전송");
		panel.add(button, BorderLayout.EAST);
		add(panel, BorderLayout.SOUTH);
		
//		입력필드와 전송버튼에 ActionListener를 걸어준다.
		insert.addActionListener(this);
		button.addActionListener(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

//				서버 채팅창이 닫힐 때 서버에게 나간다고 알려준다. => 통신을 종료한다.
				int result = JOptionPane.showConfirmDialog(chatArea, "종료하시겠습니꺼?", "채팅 종료", JOptionPane.YES_NO_OPTION);
//				System.out.println(result);
				if (result == 0) {
//					서버 채팅 창이 닫힐 때 클라이언트에게 나간다고 알려준다.
					printWriter.write("서버가 종료되었습니다.\n");
					
					printWriter.write("/bye\n");
					printWriter.flush();
//					채팅에 사용한 모든 객체를 닫는다.
					if (serverSocket != null) {try {serverSocket.close();} catch (IOException e1) {e1.printStackTrace();}}			// 필수
					if (socket != null) {try {socket.close();} catch (IOException e1) {e1.printStackTrace();}}			// 필수
					if (printWriter != null) {try {printWriter.close();} catch (Exception e1) {e1.printStackTrace();}}
					if (scanClie != null) {try {scanClie.close();} catch (Exception e1) {e1.printStackTrace();}}

//					채팅창을 닫는다.
					System.exit(0);
				}
			}
			
		});
		setVisible(true);
	}
	public static void main(String[] args) {
		
		Server4 server = new Server4();
		
		try {
//		서버를 시작한다.
			server.serverSocket = new ServerSocket(10004);
			server.msg = "192.168.0.2 서버의 10004번 포트로 서버시작<br>";
			server.msg += "클라이언트가 접속하기를 기다립니다.\n";
			server.chatArea.setText("<html>" + server.msg + "</html>");
			
//			클라이언트가 접속하기 전에는 입력아레아와 전송 버튼을 비활성화 시킨다.
			server.insert.setEnabled(false);
			server.button.setEnabled(false);
//			클라이언트가 접속하기를 기다린다.
			server.socket = server.serverSocket.accept();
			server.msg = server.msg + server.socket + "클라이언트가 접속했습니다.";
			server.chatArea.setText("<html>" + server.msg + "</html>");
//			클라이언트가 접속하면 입력아레아와 전송 버튼을 활성화 시킨다. 입력아레아로 포커스를 이동시킨다.
			server.insert.setEnabled(true);
			server.button.setEnabled(true);
			server.insert.requestFocus();
//			클라이언트와 메시지를 주고 받기 위해서 데이터 전송에 사용할 객체를 생성한다.
			server.printWriter = new PrintWriter(server.socket.getOutputStream());
			server.scanClie = new Scanner(server.socket.getInputStream());
//			클라이언트에서 전송되는 메시지를 받는 스레드를 실행한다.

			Thread thread = new Thread(server);
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	메시지 받기
	@Override
	public void run() {
//		클라이언트와 통신이 유지되고 있는 동안 반복한다. => 통신 소켓이 null이 아닌동안 반복한다.
		while (socket != null) {
//			클라이언트에서 전송된 메시지를 받는다. 
			String str = "";
			try {  str = scanClie.nextLine().trim();  } catch (Exception e) {break;}
			
//			클라이언트에서 전송된 메시지를 클라이언트 채팅창에 표시한다.
			if (str.length() > 0) {
				msg = msg + "<br>client >> " + str;
				chatArea.setText("<html>" + msg + "</html>");
//				클라이언트에서 "/bye"를 전송받으면 채팅을 종료한다.
				if (str.toLowerCase().equals("/bye")) {
					break;
				}
			}
			try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
		}
//		클라이언트와 채팅이 종료되면 메시지를 입력할 수 없도록 텍스트 필드와 전송버튼을 비활성화시킨다.
		insert.setEnabled(false);
		button.setEnabled(false);
//		채팅에 사용한 모든 객체를 닫는다.
		if (serverSocket != null) {try {serverSocket.close();} catch (IOException e) {e.printStackTrace();}}			// 필수
		if (socket != null) {try {socket.close();} catch (IOException e) {e.printStackTrace();}}			// 필수
		if (printWriter != null) {try {printWriter.close();} catch (Exception e) {e.printStackTrace();}}
		if (scanClie != null) {try {scanClie.close();} catch (Exception e) {e.printStackTrace();}}

		
	}
//	메시지 보내기 텍스트필드와 전송 버튼에 ActionListener를 걸어서 클라이언트로 데이터를 전송한다.
	@Override
	public void actionPerformed(ActionEvent e) {
//		텍스트 필드에 입력된 메시지를 받는다.
		String str = insert.getText().trim();
//		텍스트필드에 데이타가 입력된 상태일 경우 메시지를 서버 채팅창에 표시하고 클라이언트로 전송한다.
		if (str.length() > 0) {
//			텍스트필드에 입력한 메시지를 서버 채팅창에 표시한다.
			msg = msg + "<br>server >> " + str;
			chatArea.setText("<html>" + msg + "</html>");
//			입력한 메시지를 클라이언트로 전송한다.
			if (printWriter != null) {
				printWriter.write(str + "\n");
				printWriter.flush();
			}
		}
//		클라이언트로 메시지를 전송했으면 다음 메시지를 입력받기 위해 
		insert.setText("");
		insert.requestFocus();
	}
}

