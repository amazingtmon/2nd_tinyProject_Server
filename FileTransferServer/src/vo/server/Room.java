package vo.server;


import java.util.List;
import java.util.Vector;

import com.server.ThreadHandler;

public class Room {
	  List<ThreadHandler>  userList  = new Vector<ThreadHandler>();
	  List<String>  nameList  = new Vector<String>();
	  String title = null;//단톡방 이름
	  String state = null;//대기실, 참여중
	  int max=0;//최대정원수
	  int current;//현재 정원수	
	public Room() {}
	public Room(String title,int current) {
		this.title = title;
		this.current = current;
	}
	public Room(String title, String state, int current) {
		this.title = title;
		this.state = state;
		this.current = current;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
}