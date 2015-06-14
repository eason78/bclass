package danmu.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends JFrame implements ActionListener {

	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;

	private JButton registerBtn;
	private JButton startBtn;
	private JButton pauseBtn;

	private TransparentWindow transparentWindow = null;

	private void initUI() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

		JLabel title = new JLabel("»¶Ó­À´µ½B¿ÎÌÃ");

		JLabel courseIdText = new JLabel("¿Î³ÌID:");
		JLabel courseId = new JLabel("ÎÞ");

		registerBtn = new JButton("×¢²á¿Î³Ì");
		startBtn = new JButton("¿ªÆôµ¯Ä»");
		pauseBtn = new JButton("ÔÝÍ£µ¯Ä»");

		registerBtn.addActionListener(this);
		startBtn.addActionListener(this);
		pauseBtn.addActionListener(this);
		pauseBtn.setEnabled(false);

		contentPanel.add(title);
		contentPanel.add(courseIdText);
		contentPanel.add(registerBtn);
		contentPanel.add(startBtn);
		contentPanel.add(pauseBtn);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		this.setLocation((screenWidth - WIDTH) / 2, (screenHeight - HEIGHT) / 2);

		this.setContentPane(contentPanel);
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);

	}

	public Main() {
		initUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == registerBtn) {

		} else if (e.getSource() == startBtn) {
			if (transparentWindow == null) {
				startBtn.setEnabled(false);
				pauseBtn.setEnabled(true);
				transparentWindow = new TransparentWindow();
				transparentWindow.startDanmu();
			}
		} else if (e.getSource() == pauseBtn) {
			if (transparentWindow != null) {
				transparentWindow.stopDanmu();
				transparentWindow = null;
				startBtn.setEnabled(true);
				pauseBtn.setEnabled(false);
			}
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
