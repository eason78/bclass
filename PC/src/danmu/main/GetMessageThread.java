package danmu.main;

import java.util.ArrayList;

import javax.swing.JLabel;

import org.json.JSONException;

public class GetMessageThread implements Runnable{

	private TransparentWindow transparentWindow = null;
	private Engine engine = null;
	
	public GetMessageThread(TransparentWindow transparentWindow) {
		this.transparentWindow = transparentWindow;
		engine = Engine.getInstance();
	}
	
	@Override
	public void run() {
		while(engine.isRun()) {
			ArrayList<Message> messages = null;
			try {
				messages = RequestMessage.requestMessage();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (Message msg : messages) {
				System.out.println("msg: " + msg.getMsg());
				
				JLabel tmplabel = new JLabel(msg.getMsg());
            	int y = (int) (Math.random()*100);
            	tmplabel.setBounds(100, y, 100, 100);
//            	tmplabel.setFont(new java.awt.Font("����", Font.BOLD, 33));
                transparentWindow.add(tmplabel);
			}
			transparentWindow.repaint();
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
