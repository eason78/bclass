package danmu.main;


import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GetMessageThread implements Runnable{

	private TransparentWindow transparentWindow = null;
	private Engine engine = null;
	
	public GetMessageThread(TransparentWindow transparentWindow) {
		this.transparentWindow = transparentWindow;
		engine = Engine.getInstance();
	}
	
	@Override
	public void run() {
		int step = 10;
		RequestMessage request = new RequestMessage();
		request.requestMessage();
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
