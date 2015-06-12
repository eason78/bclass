import java.util.ArrayList;

import javax.swing.JLabel;


public class MainThread {
	private static CommentList commonList = new CommentList(new ArrayList<JLabel>(), 0);
	public static void main(String[] args) {
		new InterfaceThread(commonList);
		new ServerThread(commonList);
	}
}
