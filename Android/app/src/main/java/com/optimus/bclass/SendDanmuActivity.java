package com.optimus.bclass;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SendDanmuActivity extends Activity {
    List<HashMap<String, Object>> danmus;
    protected ListView chatListView = null;
    protected Button chatSendButton = null;
    protected Button chatBackButton = null;
    protected EditText editText = null;
    protected ImageButton addButton = null;
    protected LinearLayout setSizelinearLayout = null;
    protected InputMethodManager inputMethodManager = null;
    protected RadioGroup colorGroup = null;
    protected TextView sampleSizeColor = null;
    protected RadioGroup sizeGroup = null;
    protected CheckBox setImportantBox = null;
    public static Activity sendDanmuActivity = null;
    public Intent intent;
    public int color = 0;
    public int size = 3;
    public int important = 0;
    // protected TextView chatListText = null;
    // protected LayoutInflater layoutInflater = null;
//    protected PopupWindow popupWindow = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_danmu);
        sendDanmuActivity = this;
        intent = getIntent();
        danmus = new ArrayList<>();
        // layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        chatListView = (ListView) findViewById(R.id.chat_list);
        chatSendButton = (Button) findViewById(R.id.chat_bottom_sendbutton);
        chatBackButton = (Button) findViewById(R.id.chat_back_button);
        addButton = (ImageButton) findViewById(R.id.chat_bottom_add);
        setSizelinearLayout = (LinearLayout) findViewById(R.id.set_size_color);
        editText = (EditText) findViewById(R.id.chat_bottom_edittext);
        setImportantBox = (CheckBox) findViewById(R.id.setImport);
        //chatListText = (TextView) (layoutInflater.inflate(R.layout.chat_listitem,null).findViewById(R.id.chatlist_text));
        sampleSizeColor = (TextView) findViewById(R.id.sample_size_color);
        colorGroup = (RadioGroup) findViewById(R.id.colorGroup);
        sizeGroup = (RadioGroup) findViewById(R.id.sizeGroup);
//        popupWindow = new PopupWindow((((LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.set_size_color, null, false)), ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        final String key = intent.getStringExtra("key");
        final String url = "http://172.18.33.10:3000/shoot";
        final ListViewAdapter listAdapter = new ListViewAdapter(this, danmus);
        chatListView.setAdapter(listAdapter);
        chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Editable danmu = editText.getText();
                if (danmu != null && !danmu.toString().equals("")) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("text", danmu.toString());
                    map.put("color", sampleSizeColor.getTextColors());
                    map.put("size", sampleSizeColor.getTextSize());
                    danmus.add(map);
                    listAdapter.notifyDataSetChanged();
                    editText.setText("");
                    new Thread() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            HttpPost post = new HttpPost(url);
                            post.addHeader("Content-Type", "application/json");
                            try {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("key", key);
                                jsonObject.put("frontsize", color);
                                jsonObject.put("frontcolor", size);
                                jsonObject.put("danmu", danmu.toString());
                                jsonObject.put("important",important);
                                StringEntity entity = new StringEntity(jsonObject.toString(), HTTP.UTF_8);
                                post.setEntity(entity);
                                HttpClient client = new DefaultHttpClient();
                                HttpParams httpParams = client.getParams();
//                                HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
//                                HttpConnectionParams.setSoTimeout(httpParams, 5000);

                                HttpResponse httpResponse = client.execute(post);
                                if (httpResponse.getStatusLine().getStatusCode() != 200)
                                    //Toast.makeText(SendDanmuActivity.this, "send message succeed", Toast.LENGTH_SHORT).show();
                                //else
                                    Toast.makeText(SendDanmuActivity.this, "错误的口令或网络错误", Toast.LENGTH_SHORT).show();
                            } catch (JSONException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (ClientProtocolException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(SendDanmuActivity.this, "unknown network fail", Toast.LENGTH_SHORT).show();
                            }
                            Looper.loop();
                        }
                    }.start();
                }
            }
        });
        chatBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(SendDanmuActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        addButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                if (event.getAction() == MotionEvent.ACTION_UP)
                    if (setSizelinearLayout.getVisibility() == View.GONE)
                        setSizelinearLayout.setVisibility(View.VISIBLE);
                    else
                        setSizelinearLayout.setVisibility(View.GONE);
                return false;
            }
        });
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    if (setSizelinearLayout.getVisibility() != View.GONE)
                        setSizelinearLayout.setVisibility(View.GONE);
                return false;
            }
        });
        chatListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    setSizelinearLayout.setVisibility(View.GONE);
                }
                return onTouchEvent(event);
            }
        });
        colorGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.redRadioButton) {
                    sampleSizeColor.setTextColor(Color.RED);
                    color = 0;
                } else if (checkedId == R.id.blueRadioButton) {
                    sampleSizeColor.setTextColor(Color.BLUE);
                    color = 1;
                } else if (checkedId == R.id.greenRadioButton) {
                    sampleSizeColor.setTextColor(Color.GREEN);
                    color = 2;
                } else if (checkedId == R.id.blackRadioButton) {
                    sampleSizeColor.setTextColor(Color.BLACK);
                    color = 3;
                }
            }
        });
        sizeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.sRadioButton) {
                    sampleSizeColor.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8);
                    size = 0;
                } else if (checkedId == R.id.mRadioButton) {
                    sampleSizeColor.setTextSize(TypedValue.COMPLEX_UNIT_PT, 9);
                    size = 1;
                } else if (checkedId == R.id.lRadioButton) {
                    sampleSizeColor.setTextSize(TypedValue.COMPLEX_UNIT_PT, 10);
                    size = 2;
                } else if (checkedId == R.id.xlRadioButton) {
                    sampleSizeColor.setTextSize(TypedValue.COMPLEX_UNIT_PT, 11);
                    size = 3;
                }
            }
        });
        setImportantBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    important = 1;
                else
                    important = 0;
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            intent.setClass(SendDanmuActivity.this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
//                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                        InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
//        return super.onTouchEvent(event);
//    }

 }
