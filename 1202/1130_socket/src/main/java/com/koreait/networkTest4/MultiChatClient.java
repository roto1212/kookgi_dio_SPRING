package com.koreait.networkTest4;

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

public class MultiChatClient extends Frame implements ActionListener, Runnable{
//	JTextArea chatArea; 				// 채팅 내용이 출력될 영역
	JLabel chatArea; 				// 채팅 내용이 출력될 영역
	JPanel panel;						// 대화 내용이 입력하는 텍스트 에어리어와 전송 버튼이 올라갈 패널
	JTextField insert;					// 채팅 입력창
	JButton button;						// 입력한 채팅 내용을 전송하는 버튼
	
	Socket socket;
	PrintWriter printWriter;
	Scanner scanServ;
	String msg ="";						// 서버와 클라이언트의 대화 내용을 저장했다가 대화 내용이 출력되는 텍스트 영역에 뿌려줄 때 사용할 변수
	
	public MultiChatClient() {
		setTitle("1:1 채팅프로그램 - 클라이언트");
		setBounds(1000, 50, 500, 700);
	
//		채팅창 만들기
//		chatArea = new JTextArea();
//		chatArea.setEditable(false);
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
		
		insert.addActionListener(this);
		button.addActionListener(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
//				클라이언트 채팅창이 닫힐 때 서버에게 나간다고 알려준다. => 통신을 종료한다.
				int result = JOptionPane.showConfirmDialog(chatArea, "종료하시겠습니꺼?", "채팅 종료", JOptionPane.YES_NO_OPTION);
//				System.out.println(result);
				if (result == 0) {
//					클라이언트 채팅 창이 닫힐 때 서버에게 나간다고 알려준다.
					printWriter.write("클라이이언트가 나갔습니다.\n");
					printWriter.write("/bye\n");
					printWriter.flush();
//					채팅에 사용한 모든 객체를 닫는다.
					if (socket != null) {try {socket.close();} catch (IOException e1) {e1.printStackTrace();}}			// 필수
					if (printWriter != null) {try {printWriter.close();} catch (Exception e1) {e1.printStackTrace();}}
					if (scanServ != null) {try {scanServ.close();} catch (Exception e1) {e1.printStackTrace();}}

//					채팅창을 닫는다.
					System.exit(0);
				}
				
			}
			
		});
		setVisible(true);
	}
	public static void main(String[] args) {
		
		MultiChatClient client = new MultiChatClient();
		
		try {
//		서버에 접속한다.
			client.socket = new Socket("192.168.0.2", 10004);
			client.msg = "192.168.0.2 서버의 10004번 포트로 접속 시도<br>";
			client.msg = client.msg + client.socket + "접속성공<br> 닉네임을 입력하세요.";
			client.chatArea.setText("<html>" + client.msg + "</html>");
//			서버에 접속하면 입력창에 포커스를 이동시킨다.
			client.insert.requestFocus();
			
//			서버와와 메시지를 주고 받기 위해서 데이터 전송에 사용할 객체를 생성한다.
			client.printWriter = new PrintWriter(client.socket.getOutputStream());
			client.scanServ = new Scanner(client.socket.getInputStream());

//			서버에서 전송되는 메시지를 받는 스레드를 실행한다.
			Thread thread = new Thread(client);
			thread.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	서버에서 언제 메시지를 보내올지 모르기 때문에 통신이 종료될 때까지 반복하며 서버에서 전송되는 메시즈를 스레드로 받는다.
	@Override
	public void run() {
//		서버와 통신이 유지되고 있는 동안 반복한다. => 통신 소켓이 null이 아닌동안 반복한다.
		while (socket != null) {
//			서버에서 전송된 메시지를 받는다. 
			String str = "";
			try {
				str = scanServ.nextLine().trim();
			} catch (Exception e) {break;}
//			서버에서 전송된 메시지를 클라이언트 채팅창에 표시한다.
			if (str.length() > 0) {
				msg = msg + "<br>" + str;
				chatArea.setText("<html>" + msg + "</html>");
//				서버에서 "/bye"를 전송받으면 채팅을 종료한다.
				if (str.toLowerCase().equals("/bye")) {
					break;
				}
			}
			try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
		}
//		서버와 채팅이 종료되면 메시지를 입력할 수 없도록 텍스트 필드와 전송버튼을 비활성화시킨다.
		insert.setEnabled(false);
		button.setEnabled(false);
//		채팅에 사용한 모든 객체를 닫는다.
		if (socket != null) {try {socket.close();} catch (IOException e) {e.printStackTrace();}}			// 필수
		if (printWriter != null) {try {printWriter.close();} catch (Exception e) {e.printStackTrace();}}
		if (scanServ != null) {try {scanServ.close();} catch (Exception e) {e.printStackTrace();}}
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
//		텍스트 필드에 입력된 메시지를 받는다.
		String str = insert.getText().trim();
//		텍스트필드에 데이타가 입력된 상태일 경우 메시지를 클라이언트 채팅창에 표시하고 서버로 전송한다.
		if (str.length() > 0) {
//		텍스트필드에 입력한 메시지를 클라이언트 채팅창에 표시한다.
//			msg = msg + "<br>" + str;
//			chatArea.setText("<html>" + msg + "</html>");
//		입력한 메시지를 서버로 전송한다.
			if (printWriter != null) {
				printWriter.write(str + "\n");
				printWriter.flush();
			}
		}
//		서버로 메시지를 전송했으면 다음 메시지를 입력받기 위해 입력필드의 내용을 지우고 커서를 위치시킨다. 
		insert.setText("");
		insert.requestFocus();

		
	}
}

