package danmu.main;


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
		int step = 10;
		RequestMessage request = new RequestMessage();
		request.requestMessage(code,limitNum);
		ArrayList<Message> onScreen = request.getMessage();
		while(engine.isRun()) {
        	for(int i=0;i<onScreen.size();i++){
        		Message message = onScreen.get(i);
        		int x = message.getX();
        		int y = message.getY();
        		if(x > step){
        			x -= step;
        			message.setX(x);
        			message.msg.setBounds(x,y,800,500);
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
