package danmu.main;

import java.util.Random;

import javax.swing.JLabel;

public class Message {

	public JLabel msg;
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	
	public void setY(int y) {
		this.y = y;
	}

	public Message(String str) {
		super();
		this.msg = new JLabel(str);
		x = 1300;
		y = (int) (Math.random()*400);
	}

	public String getMsg() {
		return msg.getText();
	}

	public void setMsg(String msg) {
		this.msg.setText(msg);
	};
	
	
}
