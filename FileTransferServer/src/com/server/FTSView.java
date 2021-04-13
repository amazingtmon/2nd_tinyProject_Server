package com.server;


import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FTSView {
	JFrame jf = new JFrame("서버가동중");
	JTextArea jta_log = new JTextArea();
	JScrollPane jsp_log = new JScrollPane(jta_log);
	
	
	public FTSView() {
		initDisplay();
		System.out.println("뷰"+this);
	}
	public void initDisplay() {
		jf.add("Center", jsp_log);
		jf.setSize(300, 300);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
}
