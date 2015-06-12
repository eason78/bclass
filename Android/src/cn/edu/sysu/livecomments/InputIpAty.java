

package cn.edu.sysu.livecomments;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.content.IntentSender.SendIntentException;
import android.view.View.OnClickListener;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputIpAty extends ActionBarActivity {

	private static String IpAddress = new String();
	public static void setIpAddress(String editable) {
		IpAddress = editable;
	}

	private static int Port = 10000;
	private EditText comment = null;
	private EditText IPAdd = null;
	private Button send = null;
	private Socket socket = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inputaty);
		
		comment = (EditText) findViewById(R.id.comment);
		IPAdd = (EditText) findViewById(R.id.IPAdd);
		send = (Button) findViewById(R.id.send);

		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println(IPAdd.getText().toString());
				setIpAddress(IPAdd.getText().toString());
				System.out.println(comment.getText());
				sendMsg();
				comment.setText("");
			}
		});
	}

	
	public void sendMsg() {  
		  
        try {  
        	System.out.println(comment.getText());
            // 创建socket对象，指定服务器端地址和端口号  
            socket = new Socket(IpAddress, 10000);  
            System.out.println(comment.getText());
            // 获取 Client 端的输出流  
            PrintWriter out = new PrintWriter(new BufferedWriter(  
                    new OutputStreamWriter(socket.getOutputStream(),"UTF-8")), true);  
            System.out.println(comment.getText());
            // 填充信息  
            out.println(comment.getText());  
            System.out.println(comment.getText());
//            System.out.println("msg=" + comment.getText());  
            // 关闭  
  
        } catch (UnknownHostException e1) {  
            e1.printStackTrace();  
        } catch (IOException e1) {  
            e1.printStackTrace();  
        } finally {  
            try {  
                socket.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
  
    }  
	
}

