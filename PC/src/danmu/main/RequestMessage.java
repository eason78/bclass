package danmu.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;



public class RequestMessage {
	private static Engine engine = Engine.getInstance();
	private static final int READ_TIMEOUT = 5 * 1000;
	private static final int CONNECTION_TIMEOUT = 5 * 1000;
	private static ArrayList<Message> onScreen = new ArrayList<Message>();
	public static void removeMessage(int i){
		onScreen.remove(i);
	}
	
	public ArrayList<Message> getMessage(){
		return onScreen;
	}
	
	public void requestMessage() {
//		if (engine.isRun()) {
//			try {
//				URL url = new URL("http://localhost:8080/Server4Login/message.do");
//				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//				conn.setRequestMethod("GET");
////				conn.setConnectTimeout(CONNECTION_TIMEOUT);
////				conn.setReadTimeout(READ_TIMEOUT);
////				conn.setRequestProperty("Connection", "Keep-Alive");
//				InputStream in = conn.getInputStream();
//				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//				String buf = null;
//				StringBuffer stringBuffer = new StringBuffer();
//				while ((buf = bufferedReader.readLine()) != null) {
//					stringBuffer.append(buf);
//				}
//				String result = stringBuffer.toString();
//				
//				JSONArray array = new JSONArray(result);
//				for (int i = 0; i < array.length(); i++) {
//					JSONObject object = array.getJSONObject(i);
//					Message msg = new Message(object.getString("msg"));
//					messages.add(msg);
//				}
//				in.close();
//				bufferedReader.close();
//				conn.disconnect();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
			
		onScreen.add(new Message("abc"));
		onScreen.add(new Message("hello world"));
	}
}
