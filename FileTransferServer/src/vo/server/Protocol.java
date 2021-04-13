package vo.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.server.LoginThread;

public class Protocol implements Serializable{
	//유저 아이디 저장 할 필요가 있을까? 
	public static Vector<LoginThread> users = null;
	public static Map<String, LoginThread> userList = new HashMap<String, LoginThread>();;
	//온라인 유저 리스트
	public static List<String> onlineUser = new Vector<String>();
	//오프라인 유저 리스트
	public static List<String> offlineUser = new Vector<String>();
	
	
	//유저 로그인 관련 프로토콜
	public static final String checkLogin = "100"; //로그인
	public static final String addUser = "110"; //회원가입
	public static final String showUser = "120"; //유저리스트
	//채팅방 생성 관련 프로토콜
	public static final String createRoom1 = "200"; //1:1 대화방 생성
	public static final String createRoomN = "210"; // 그룹대화방 생성
	public static final String closeRoom = "220"; // 대화방 퇴장
	
	//메세지 전송 관련 프로토콜
	public static final String sendMessage = "300"; //메세지 전송
	public static final String sendEmoticon = "310"; //이모티콘 전송
	public static final String sendFile = "320"; // 파일 전송
	
	//토큰 구분 값
	public static final String seperator= "#"; 
	
}
