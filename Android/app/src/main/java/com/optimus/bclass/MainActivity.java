package com.optimus.bclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class MainActivity extends Activity {
    protected EditText keyIn = null;
    private static Handler handlerClick = null;
    private static Handler handlerRequest = null;
    public static final int SUCCEED = 1;
    public static final int FAIL = 0;
    public Thread requestThread = null;
    public TextView infoTextView = null;
    public Button inClassButton = null;
    public Button sendButton = null;
    public TextView welText = null;
    public LinearLayout keyInLayout = null;
    public boolean runRequestThread = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keyIn = (EditText) findViewById(R.id.keyInText);
        infoTextView = (TextView) findViewById(R.id.infoText);
        infoTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        inClassButton = (Button) findViewById(R.id.inClassButton);
        sendButton = (Button) findViewById(R.id.sendButton);
        welText = (TextView) findViewById(R.id.welcomeText);
        keyInLayout = (LinearLayout) findViewById(R.id.keyInLayout);
        final String path = "http://172.18.33.10:3000/get_messages?key=";
        handlerClick = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SUCCEED:
                        inClassButton.setText("退出教室");
                        keyInLayout.setVisibility(View.GONE);
                        welText.setVisibility(View.VISIBLE);
                        sendButton.setVisibility(View.VISIBLE);
                        runRequestThread = true;
                        if (!requestThread.isAlive())
                            requestThread.start();
                        //Toast.makeText(MainActivity.this, "send message succeed", Toast.LENGTH_SHORT).show();
                        break;
                    case FAIL:
                        Toast.makeText(MainActivity.this, "send message fail", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        handlerRequest = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SUCCEED:
                        if (!msg.obj.equals("")) {
                            try {
                                List<HashMap<String,Object>> list = Analysis((String)(msg.obj));
                                infoTextView.setText((String)(list.get(0).get("text")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //Toast.makeText(MainActivity.this, "send message succeed", Toast.LENGTH_SHORT).show();
                        break;
                    case FAIL:
                        Toast.makeText(MainActivity.this, "send message fail", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        requestThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    String value = URLEncoder.encode(keyIn.getText().toString(), "UTF-8");
                    URL url = new URL(path + value);
                    while (isAlive() && runRequestThread) {
                        sleep(30000);
                        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                        byte[] data = new byte[1024];
                        int len;
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        int code = con.getResponseCode();
                        System.out.println(code);
                        Message message = new Message();
                        if (code == 200) {
                            InputStream inStream = con.getInputStream();
                            while ((len = inStream.read(data)) != -1) {
                                outStream.write(data, 0, len);
                            }
                            inStream.close();
                            message.what = SUCCEED;
                            message.obj = new String(outStream.toByteArray());
                            handlerRequest.sendMessage(message);
                        } else {
                            message.what = FAIL;
                            handlerRequest.sendMessage(message);
                        }
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                   // Toast.makeText(MainActivity.this, "unknown network fail", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "unknown network fail", Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        };

        inClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Button) v).getText().equals("进入教室")) {
                    final String key = keyIn.getText().toString();
                    if (!key.equals("")) {
                        new Thread() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                try {
                                    String value = URLEncoder.encode(key, "UTF-8");
                                    URL url = new URL(path + value);
                                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                    con.setRequestMethod("GET");
                                    int code = con.getResponseCode();
                                    System.out.println(code);
                                    Message message = new Message();
                                    if (code == 200) {
                                        message.what = SUCCEED;
                                        handlerClick.sendMessage(message);
                                    } else {
                                        message.what = FAIL;
                                        handlerClick.sendMessage(message);
                                    }
                                } catch (ClientProtocolException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    //Toast.makeText(MainActivity.this, "unknown network fail", Toast.LENGTH_SHORT).show();
                                }
                                Looper.loop();
                            }
                        }.start();
                    }
                } else {
                    ((Button) v).setText("进入教室");
                    runRequestThread = false;
                    sendButton.setVisibility(View.GONE);
                    welText.setVisibility(View.GONE);
                    keyInLayout.setVisibility(View.VISIBLE);
                    if (SendDanmuActivity.sendDanmuActivity != null)
                        SendDanmuActivity.sendDanmuActivity.finish();
                }
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SendDanmuActivity.class);
                intent.putExtra("key", keyIn.getText().toString());
                startActivity(intent);
                onPause();
            }
        });
    }
    private static ArrayList<HashMap<String, Object>> Analysis(String jsonStr)
            throws JSONException {
        /******************* 解析 ***********************/
        JSONArray jsonArray = null;
        // 初始化list数组对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            // 初始化map数组对象
            HashMap<String, Object> map = new HashMap<>();
            map.put("createAt", jsonObject.getString("_id"));
            map.put("text", jsonObject.getString("texts"));
            list.add(map);
        }
        return list;
    }
}
