package danmu.main;

import javax.swing.JWindow;

public class TransparentWindow extends JWindow{
	private static final long serialVersionUID = 1L;
	private Engine engine;
	
	public TransparentWindow() {

        this.setSize(300, 400);
//        this.setBackground (new Color (0, 0, 0, 0));
        this.setAlwaysOnTop(false);
        this.setVisible(true);
        
        engine = Engine.getInstance();
	}
	
	public void startDanmu() {
		engine.start();;
		new Thread(new GetMessageThread(this)).start();
	}
	
	public void stopDanmu() {
		engine.stop();
		this.dispose();
	}
}
