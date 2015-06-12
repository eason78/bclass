import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class InterfaceThread extends JWindow implements ActionListener, Runnable {

	private static final long serialVersionUID = 1L;
	private JButton btn     = new JButton("暂停");
    private int     step    = 8;
    private boolean running = true;
    private CommentList comments = null;;

    public  InterfaceThread(CommentList newcomments) {
    	comments = newcomments;
        this.setLayout(null);

        this.setSize(1500, 800);
        setBackground (new Color (0, 0, 0, 0));
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        start();
    }

    public void actionPerformed(ActionEvent e) {
        if (running) {
            running = false;
            btn.setText("启动");
        } else {
            running = true;
            btn.setText("暂停");
            start();
        }
    }

    private void start() {
        new Thread(this).start();
    }

    public void run() {
        while (running) {
        	
        	for(;comments.getNew_number() > 0; comments.decNew_number()){
        		int comsize = CommentList.getOnScreenComments().size();
        		JLabel tmplabel = CommentList.getOnScreenComments().get(comsize-comments.getNew_number());
//        		System.out.println("我会显示在弹幕上"+tmplabel.getText()+" "+comments.getNew_number() +" "+comsize);
            	int y = (int) (Math.random()*400);
            	tmplabel.setBounds(1300, y, 500, 500);
            	tmplabel.setFont(new java.awt.Font("黑体", Font.BOLD, 33));
                add(tmplabel);
        	}
        	
        	
        	for(int i = 0; i< CommentList.getOnScreenComments().size();i++){
        		JLabel tmpLabel = CommentList.getOnScreenComments().get(i);
        		int x = tmpLabel.getLocation().x;
        		int y = tmpLabel.getLocation().y;
        		if(x > step){
        			x -= step;
        			tmpLabel.setBounds(x,y,800,500);
        		}
        		else{
        			remove(tmpLabel);
        			comments.deleteFirst();
        		}
        		
        	}
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
    
    
}