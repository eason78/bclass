package danmu.main;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JWindow;

public class TransparentWindow extends JWindow{
	
	private Engine engine;
	
	public TransparentWindow() {
		this.setLayout(null);
        this.setSize(1500, 800);
        setBackground (new Color (0, 0, 0, 0));
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        
        engine = Engine.getInstance();
	}
	
	public void startDanmu() {
		engine.setRun(true);
		new Thread(new GetMessageThread(this)).start();
	}
	
	public void stopDanmu() {
		engine.setRun(false);
		this.dispose();
	}
}
