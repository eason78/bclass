package danmu.main;


import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GetMessageThread implements Runnable{

	private TransparentWindow transparentWindow = null;
	private Engine engine = null;
	private String code;
	private int limitNum;
	public GetMessageThread(TransparentWindow transparentWindow,String code_,int limit) {
		this.transparentWindow = transparentWindow;
		engine = Engine.getInstance();
		code = code_;
		limitNum = limit;
	}
	
	@Override
	public void run() {
		while(engine.isRun()) {
		RequestMessage request = new RequestMessage();
		request.requestMessage(code,limitNum);
		ArrayList<Message> onScreen = request.getMessage();
		if(engine.isRun()) {
        	for(int i=0;i<onScreen.size();i++){
        		Message message = onScreen.get(i);
        		int x = message.getX();
        		int y = message.getY();
        		int step = message.getStep();
        		if(x > step){
        			x -= step;
        			message.setX(x);
        			message.msg.setBounds(x,y,800,500);
        			Color fontColor = getColor(message.getFontColor());
        			int fontSize = getFontSize(message.getFontSize());
        			message.msg.setForeground(fontColor);
        			message.msg.setFont(new Font("ËÎÌו", Font.PLAIN, fontSize));
        			transparentWindow.add(message.msg);
        		}
        		else{
        			transparentWindow.remove(message.msg);
        			RequestMessage.removeMessage(i);
        		}
        		
        	}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			transparentWindow.repaint();
		}
		}
		
	}

	private int getFontSize(int fontSize) {
		int size = 14;
		switch (fontSize) {
		case 0:
			size = 14;
			break;
		case 1:
			size = 18;
			break;
		case 2:
			size = 24;
			break;
		case 3:
			size = 30;
		default:
			break;
		}
		return size;
	}

	private Color getColor(int colorFlag) {
		Color color = null;
		
		switch (colorFlag) {
		
		case 0:
			color = Color.RED;
			break;
		case 1:
			color = Color.BLUE;
			break;
		case 2:
			color = Color.GREEN;
			break;
		case 3:
			color = Color.BLACK;
			break;
		default:
			color = Color.BLACK;
			break;
		}
		
		return color;
	}

}
