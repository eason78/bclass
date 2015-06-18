package danmu.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
















import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;






public class RequestMessage {
	private static Engine engine = Engine.getInstance();
	private static ArrayList<Message> onScreen = new ArrayList<Message>();
	public static void removeMessage(int i){
		onScreen.remove(i);
	}
	
	public ArrayList<Message> getMessage(){
		return onScreen;
	}
	

	public void requestMessage(String code,int limitNum)  {
		if (engine.isRun()) {
			try {
				
				
				URL url = new URL("http://172.18.33.10:3000/get_bullets?bcode="+code+"&limit="+limitNum);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);

			    conn.setDoInput(true);

			    conn.setUseCaches(false);

			    conn.setRequestMethod("GET");
			   
			    conn.connect();
			    
				InputStream in = conn.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
				String buf = null;
				StringBuffer stringBuffer = new StringBuffer();
				while ((buf = bufferedReader.readLine()) != null) {
					stringBuffer.append(buf);
				}
				String result = stringBuffer.toString();
				
				JSONArray array;
				try {
					array = new JSONArray(result);
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						
						String la = object.getString("texts");
						String iso = new String(la.getBytes("GBK"),"ISO-8859-1"); 
						String text =new String(iso.getBytes("ISO-8859-1"),"UTF-8").toString();
						Message msg = new Message(text,
								object.getInt("fontSize"),
								object.getInt("fontColor"),
								object.getInt("important"));
						
						onScreen.add(msg);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				in.close();
				bufferedReader.close();
				conn.disconnect();
	            
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public String requestCode()  {
		String code = null;
		if (engine.isRun()) {
			try {
				URL url = new URL("http://172.18.33.10:3000/init");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);

			    conn.setDoInput(true);

			    conn.setUseCaches(false);

			    conn.setRequestMethod("GET");
			    conn.setRequestProperty("Content-type",
			    		"application/json");

			    conn.connect();
				InputStream in = conn.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
				String buf = null;
				StringBuffer stringBuffer = new StringBuffer();
				while ((buf = bufferedReader.readLine()) != null) {
					stringBuffer.append(buf);
				}
				String result = stringBuffer.toString();
				
				JSONObject obj;
				try {
					obj = new JSONObject(result);
					code = obj.getString("code");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				in.close();
				bufferedReader.close();
				conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return code;
	}
	public void sendInform(String information,String code) throws JSONException  {
		if (engine.isRun()) {
			HttpClient client = new DefaultHttpClient();

			try {
				String url = "http://172.18.33.10:3000/send_message";
		        HttpPost post = new HttpPost(url);
	            JSONObject obj = new JSONObject();
	            obj.put("bcode",code);
	            obj.put("message",information);
	            
				
				StringEntity entity = new StringEntity(obj.toString(), HTTP.UTF_8);
				
				post.addHeader("Accept","application/json");
			    post.addHeader("Content-Type", "application/json");
				post.setEntity(entity);
				
                HttpParams httpParams = client.getParams();
				
                HttpResponse response = client.execute(post); 
                if(response.getStatusLine().getStatusCode() == 200){  
                	
                    HttpEntity rentity = response.getEntity();  
                   
                }  
			} catch (Exception e) {  
	            //throw new RuntimeException(e);  
	        }finally {
	            client.getConnectionManager().shutdown();
	        }
		}
	}
}
