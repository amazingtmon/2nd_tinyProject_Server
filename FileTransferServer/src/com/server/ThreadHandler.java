package com.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import vo.server.Protocol;


public class ThreadHandler extends Thread{
	FTServer server = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;

	public ThreadHandler(FTServer ser) {
		this.server = ser;
		try {
			ois = new ObjectInputStream(ser.client.getInputStream());
			oos = new ObjectOutputStream(ser.client.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		boolean isStop = false;
		if(ois==null || server.client==null) { //무한루프 방지
			isStop = true;
		}
		try {
			run_start://while문같은 반복문 전체를 빠져 나가도록 처리할 때
				while(!isStop) {
					String msg = ois.readObject().toString();//메세지 받기
					server.view.jta_log.append(msg);
					StringTokenizer st = new StringTokenizer(msg, "#");
					switch(st.nextToken()) {// 프로토콜넘버 기준으로 실행구분
					case Protocol.checkLogin:{ //100#p_id#p_pw
						LoginThread logth = new LoginThread(this, server);
						logth.checkLogin(st.nextToken(), st.nextToken());
						
					}break;
					case Protocol.addUser:{ //110#p_id#p_pw#p_name
						LoginThread logth = new LoginThread(this, server);
						logth.addUser(st.nextToken(), st.nextToken(), st.nextToken());
					}break;
					case Protocol.showUser:{//120번
						
					}break;
					case Protocol.createRoom1:{ // 200#p_id#userList#roomName
						OpenRoomThread open = new OpenRoomThread(server, this);
						List<String> userList = new Vector<String>();
						String p_id = st.nextToken();
						String test_userList = st.nextToken();
						String roomName = st.nextToken();
						
						if(test_userList.contains("[")||test_userList.contains("]")) {
							String one_test_userList = test_userList.replaceAll("\\p{Punct}", "");
							if(one_test_userList.contains(" ")) {
								String[] one = one_test_userList.split(" ");
								System.out.println("one: "+one.length);
								for(int i=0;i<one.length;i++) {
									String result = one[i];
									userList.add(result);
								}
							}else {
								userList.add(one_test_userList);
							}
						}
						//System.out.println("userList 사이즈 .. "+userList.size());
						//System.out.println("userList  : "+userList);
						open.createRoom(p_id, roomName, userList);
						break;
					}
					case Protocol.createRoomN:{ //210번
						
					}break;
					case Protocol.closeRoom:{ //220번
						
					}break;
					case Protocol.sendMessage:{//300#p_id#roomName#msg
						MessageThread mes = new MessageThread(this, server);
						mes.sendMessage(st.nextToken(), st.nextToken(), st.nextToken());
					}break;
					case Protocol.sendEmoticon:{ //310번
						
					}break;
					case Protocol.sendFile:{ //320번
						
					}break;
					}
					
				}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
	}

}
