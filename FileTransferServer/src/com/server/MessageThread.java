package com.server;

import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Vector;

import vo.server.Protocol;

public class MessageThread extends Thread{
	FTServer server = null;
	ThreadHandler handler = null;
	
	public MessageThread(ThreadHandler hand, FTServer ser) {
		this.server = ser;
		this.handler = hand;
	}
	@Override
	public void run() {
		
		
		
	}
	//300번 메세지전송  //300#p_id#roomName#msg
	public void sendMessage(String p_id, String roomName, String msg) {
		try {
			List<ThreadHandler> room = new Vector<ThreadHandler>();
			room.addAll(server.chatRoom.get(roomName));
			for(ThreadHandler user:room) {
				user.oos.writeObject(Protocol.sendMessage
						+Protocol.seperator+p_id
						+Protocol.seperator+roomName
						+Protocol.seperator+msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//310번 이모티콘 전송
	public void sendEmoticon() {
		
	}
	//320번 파일 전송
	public void sendFile() {
		
	}
	private void send(String msg) {
		try {
			handler.oos.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
