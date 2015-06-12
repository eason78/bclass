import java.util.ArrayList;

import javax.swing.JLabel;


public class CommentList {
	private static ArrayList<JLabel> onScreenComments = new ArrayList<JLabel>();
	int new_number;
	
	public static ArrayList<JLabel> getOnScreenComments() {
		return onScreenComments;
	}
	public static void setOnScreenComments(ArrayList<JLabel> onScreenComments) {
		CommentList.onScreenComments = onScreenComments;
	}
	public static void addOnScreenComments(String s){
		onScreenComments.add(new JLabel(s));
	}
	public void deleteFirst(){
		onScreenComments.remove(0);
	}
	public int getNew_number() {
		return new_number;
	}
	public void setNew_number(int new_number) {
		this.new_number = new_number;
	}
	public void addNew_number(){
		this.new_number ++;
	}
	public void decNew_number(){
		this.new_number --;
	}
	public CommentList(ArrayList<JLabel> out_comments,int out_n) {
		onScreenComments = out_comments;
		new_number = out_n;
	}
}
