package danmu.main;

import java.util.Random;

import javax.swing.JLabel;

public class Message {

	public JLabel msg;
	private int x;
	private int y;
	private int fontColor;
	private int fontSize;
	private int isImportant;
	
	public int getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(int isImportant) {
		this.isImportant = isImportant;
	}

	public int getFontColor() {
		return fontColor;
	}

	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

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

	public Message(String str, int fontSize, int fontColor, int isImportant) {
		super();
		this.msg = new JLabel(str);
		this.fontSize = fontSize;
		this.fontColor = fontColor;
		this.isImportant = isImportant;
		
		// ÍÂ²Û
		if (isImportant == 0) {
			y = (int) (Math.random()*400) + 400;
		} else {
			y = (int) (Math.random()*400);
		}
		x = 1300;
		
	}

	public String getMsg() {
		return msg.getText();
	}

	public void setMsg(String msg) {
		this.msg.setText(msg);
	};
	
	
}
