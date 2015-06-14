package danmu.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RequestMessage {
	private static Engine engine = Engine.getInstance();
	private static final int READ_TIMEOUT = 5 * 1000;
	private static final int CONNECTION_TIMEOUT = 5 * 1000;
	
	private static HttpURLConnection conn = null;
	private static InputStream in = null;
	private static BufferedReader bufferedReader = null;
	
	public static void init() {
		URL url;
		try {
//			URL url = new URL("http://172.18.202.118:3000/get_bullets");
			url = new URL("http://localhost:8080/Server4Login/message.do");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(CONNECTION_TIMEOUT);
//			conn.setReadTimeout(READ_TIMEOUT);
			conn.setRequestProperty("Connection", "Keep-Alive");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Message> requestMessage() throws JSONException {
		ArrayList<Message> messages = new ArrayList<Message>();
		if (engine.isRun()) {
			try {
				init();
				conn.connect();
				in = conn.getInputStream();
				System.out.println("get inputStream");
				bufferedReader = new BufferedReader(new InputStreamReader(in));
				String buf = null;
				StringBuffer stringBuffer = new StringBuffer();
				while ((buf = bufferedReader.readLine()) != null) {
					stringBuffer.append(buf);
				}
				String result = stringBuffer.toString();
				if (result.equals("")) {
					return messages;
				}
				System.out.println(result);
				JSONArray array = new JSONArray(result);
				for (int i = 0; i < array.length(); i++) {
					JSONObject object = array.getJSONObject(i);
//					Message msg = new Message(object.getString("texts"));
					Message msg = new Message(object.getString("msg"));
					messages.add(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				stopRequest();
			}
		}
		return messages;
	}
	
	public static void stopRequest() {
		if (conn != null) {
			conn.disconnect();
			conn = null;
		}
		try {
			if (in != null) {
				in.close();
				in = null;
			}
			if (bufferedReader != null) {
				bufferedReader.close();
				bufferedReader = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
