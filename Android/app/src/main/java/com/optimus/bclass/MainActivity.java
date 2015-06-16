package com.optimus.bclass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class MainActivity extends Activity {
    protected EditText keyIn = null;
    private static Handler handler = null;
    public static final int SUCCEED = 1;
    public static final int FAIL = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keyIn = (EditText) findViewById(R.id.keyInText);
        final String path = "http://172.18.33.10:3000/get_messages?key=";
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SUCCEED :
                        Toast.makeText(MainActivity.this, "send message succeed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, SendDanmuActivity.class);
                        intent.putExtra("key", keyIn.getText().toString());
                        startActivity(intent);
                        break;
                    case FAIL:
                        Toast.makeText(MainActivity.this, "send message fail", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String key = keyIn.getText().toString();
                if (!key.equals("")) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                String value = URLEncoder.encode(key, "UTF-8");
                                URL url = new URL(path + value);
                                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                                con.setRequestMethod("GET");
                                int code = con.getResponseCode();
                                System.out.println(code);
                                Message message = new Message();
                                if (code == 200) {
                                    message.what = SUCCEED;
                                    handler.sendMessage(message);
                                } else {
                                    message.what = FAIL;
                                    handler.sendMessage(message);
                                }
                            } catch (ClientProtocolException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            }
        });
    }

}
