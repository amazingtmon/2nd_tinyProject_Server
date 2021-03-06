package com.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import vo.server.Protocol;
import vo.server.UserVO;

public class LoginThread extends Thread{
	ThreadHandler handler = null;
	FTServer server = null; //사용자 시팔때문에 주소번지 잡아줘야함 
	//생성자
	public LoginThread(ThreadHandler hand, FTServer ser) {
		this.handler = hand;
		this.server = ser;
	}
	@Override
	public void run() {
		try {

			handler.oos.writeObject(Protocol.onlineUser);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//100번 로그인체크/////////////////////////////
	public void checkLogin(String p_id, String p_pw) {
		try {
			FTSDao ftsDao = new FTSDao();
			String result = ftsDao.loginCheck(p_id, p_pw);
			//result값은 difid  or   difpw   or  로그인 성공
			if(p_id.equals(result)) {
				boolean seccess = true;
				Iterator<String> keys = server.onlineUser.keySet().iterator();
				while(keys.hasNext()) {
					if(result.equals(keys.next())) {
						String overlap = "overlap";
						handler.oos.writeObject(Protocol.checkLogin+Protocol.seperator+overlap);//중복메세지
						seccess = false;
						break;
					}
					
				}
				if(seccess) {//기존사용자가 없을때(최초접속일때), 중복로그인이 아닐때 실행됨
					System.out.println("이거 제대로 실행됨? ");
					handler.oos.writeObject(Protocol.checkLogin+Protocol.seperator+result);//정상로그인
					server.onlineUser.put(result, handler);
					showUser(server.onlineUser);
				}
			}
			else { //그외 모든 경우 로그인 실패했을때
				handler.oos.writeObject(Protocol.checkLogin+Protocol.seperator+result);//로그인실패메세지
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//110번 회원가입/////////////////////////////
	public void addUser(String p_id ,String p_pw, String p_name) {
		try {
			FTSDao ftsDao = new FTSDao();
			String msg = ftsDao.addUser(p_id,p_pw,p_name);
			//110#내용
			handler.oos.writeObject(Protocol.addUser
									+Protocol.seperator+"성공"); 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//120번 유저리스트/////////////////////////////////////
	//전체 접속자한테 유저리스트를 보내기, 온라인 오프라인 구분하기
	public void showUser(Map<String, ThreadHandler> user) {
		try {
			List<String> onlineUser = new Vector<String>();
			for(String p_id:user.keySet()) {
				onlineUser.add(p_id);
			}
			MyBatisServerDao serDao = new MyBatisServerDao();
			server.offlineUser = serDao.showUser(onlineUser);
			for(String key:server.onlineUser.keySet()) {
				server.onlineUser.get(key).oos.writeObject(Protocol.showUser
						+Protocol.seperator+onlineUser
						+Protocol.seperator+server.offlineUser);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//130번 접속 종료
	public void logout(String p_id) {
		try {
			server.onlineUser.remove(p_id);
			showUser(server.onlineUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void broadCasting(String msg) {//날 제외한 전체방송
		synchronized(this) {
			for(String key:server.onlineUser.keySet()) {
				try {
					server.onlineUser.get(key).oos.writeObject(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void send(String msg) {
		try {
			handler.oos.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
