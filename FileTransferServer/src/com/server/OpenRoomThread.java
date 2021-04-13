package com.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import vo.server.Protocol;

public class OpenRoomThread extends Thread{
	FTServer server = null;
	ThreadHandler handler = null;
	public OpenRoomThread(FTServer ser, ThreadHandler hand) {
		this.server = ser;
		this.handler = hand;
		
	}
	
	@Override
	public void run() {
		
		
	}
	//200번 채팅방 생성
	public void createRoom(String p_id, String roomName, List<String> userList) {//스트링 리스트로 받아와야 함.
		List<ThreadHandler> user = new Vector<ThreadHandler>();
		user.add(server.onlineUser.get(p_id));
		System.out.println("유저리스트의 사이즈  : "+userList.size());
		System.out.println("요거 찍어보기  "+p_id+"  "+userList);
		for(String id:userList) {
			//id haeri1127  --초대된 아이디들이 id에 넣어짐
			//id test100
			//id abcd123
			// .....
			user.add(server.onlineUser.get(id)); 
			//onlineUser<String, ThreadHandler> 안에 있는 키(id)를 통해 get해오면
			//ThreadHandler 값이 나오겠지? 그거를 user<ThreadHandler>에 넣기
			//user라는건 초대된 아이디들의 스레드를 모아둔 리스트
		}
		server.chatRoom.put(roomName, user); //Map<roomName, user(List<ThreadHandler>)>
		//<바나나 우유방,haeri1127&abcd123>  <초콜릿 방, qwer123&...>
		
		
		try {//200#p_id#roomName
			handler.oos.writeObject(Protocol.createRoom1
									+Protocol.seperator+p_id
									+Protocol.seperator+roomName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//210번 그룹채팅방 생성
	public void createRoomN() {
		
		
	}
	//220번 채팅방 퇴장
	public void closeRoom() {
		
		
	}
}
