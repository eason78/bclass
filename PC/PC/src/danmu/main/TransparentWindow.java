package danmu.main;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JWindow;

public class TransparentWindow extends JWindow{
	private Thread th;
	private Engine engine;
	private String code;
	private int limit;
	public TransparentWindow(String code_,int limit_) {
		this.setLayout(null);
        this.setSize(1500, 800);
        setBackground (new Color (0, 0, 0, 0));
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.code = code_;
        this.limit = limit_;
        engine = Engine.getInstance();
	}
	
	public void startDanmu() {
		engine.setRun(true);
		th = new Thread(new GetMessageThread(this,code,limit));
		th.start();
		
	}
	
	public void stopDanmu() {
		engine.setRun(false);
		this.dispose();
	}
}
