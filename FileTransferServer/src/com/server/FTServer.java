package com.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class FTServer implements Runnable{

	ServerSocket server = null; //서버소켓
	Socket client = null;       //클라이언트 연결 소켓
	ThreadHandler handler = null;
	public static FTSView view = null;
	// 접속중인 유저
	Map<String, ThreadHandler> onlineUser = null;
	List<String> offlineUser = null;
	Map<String, List<ThreadHandler>> chatRoom = null;
	//생성자
	public FTServer() {
		onlineUser = new Hashtable<String, ThreadHandler>();
		offlineUser = new Vector<String>();
		chatRoom = new Hashtable<String, List<ThreadHandler>>();
	}
	public static void main(String[] args) {
		FTServer ftser = new FTServer();
		Thread th = new Thread(ftser);
		th.start();
		view = new FTSView();
		view.jta_log.append("서버 가동중");
	}

	@Override
	public void run() {
		//온라인유저 목록을 관리해줘야함. 그래야 온라인 유저만 dtm으로 보낼 수 있음. 근데 그 값을 어디서 관리할 것이냐가 문제임. 
		boolean isStop = false;
		try {
			server = new ServerSocket(3000);
			while(!isStop) {
				client = server.accept();
				System.out.println(client+"연결 성공");
				handler = new ThreadHandler(this);
				handler.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
