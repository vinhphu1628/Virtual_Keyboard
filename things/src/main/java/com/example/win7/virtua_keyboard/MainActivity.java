package com.example.win7.virtua_keyboard;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.BaseInputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener{

    private ImageButton back;
    private ImageButton imageWifiState;

    private TextView time, date, wifi_status, ip_address;
    private Handler mTime = new Handler();
    private int level = 0;
    private Context context = this;
    private Handler mWifi = new Handler();
    private WifiManager wifi;

    private boolean togglePassword = false;

    private EditText editNumber;

    private Button btnNum0;
    private Button btnNum1;
    private Button btnNum2;
    private Button btnNum3;
    private Button btnNum4;
    private Button btnNum5;
    private Button btnNum6;
    private Button btnNum7;
    private Button btnNum8;
    private Button btnNum9;

    private Button btnEnter;
    private Button btnDelete;
    private Button btnClear;
    private Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mWifi.post(mWifiRunnable);
        mTime.post(mTimeRunnable);
        initWidget();
        editNumber.setShowSoftInputOnFocus(false);
        editNumber.requestFocus();
        setEventClickViews();
    }

    private Runnable mTimeRunnable = new Runnable() {
        @Override
        public void run() {
            date = findViewById(R.id.date);
            time = findViewById(R.id.time);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date.setText(format.format(new Date()));
            format = new SimpleDateFormat("hh:mm");
            time.setText(format.format(new Date()));
            mTime.postDelayed(mTimeRunnable, 1000);
        }
    };

    private Runnable mWifiRunnable = new Runnable() {
        @Override
        public void run() {
            wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            int numberOfLevels = 5;
            WifiInfo wifiInfo = wifi.getConnectionInfo();
            level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
            imageWifiState = findViewById(R.id.imageWifiState);
            if(wifi.isWifiEnabled() == false) level = -1;
            switch(level){
                case 0:
                    imageWifiState.setImageResource(R.drawable.ic_signal_wifi_0_bar_black_48dp);
                    break;
                case 1:
                    imageWifiState.setImageResource(R.drawable.ic_signal_wifi_1_bar_black_48dp);
                    break;
                case 2:
                    imageWifiState.setImageResource(R.drawable.ic_signal_wifi_2_bar_black_48dp);
                    break;
                case 3:
                    imageWifiState.setImageResource(R.drawable.ic_signal_wifi_3_bar_black_48dp);
                    break;
                case 4:
                    imageWifiState.setImageResource(R.drawable.ic_signal_wifi_4_bar_black_48dp);
                    break;
                case -1:
                    imageWifiState.setImageResource(R.drawable.ic_signal_wifi_off_bar_black_48dp);
                    break;
            }
            ip_address = findViewById(R.id.ip_address);
            if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
                int ipAddress = wifiInfo.getIpAddress();
                String ipString = Formatter.formatIpAddress(ipAddress);
                ip_address.setText(ipString);
            } else ip_address.setText("No connection");
            mWifi.postDelayed(mWifiRunnable,1000);
        }
    };

    public void initWidget(){
        back = findViewById(R.id.back);
        editNumber = findViewById(R.id.editNumber);
        btnNum0 = findViewById(R.id.btnNum0);
        btnNum1 = findViewById(R.id.btnNum1);
        btnNum2 = findViewById(R.id.btnNum2);
        btnNum3 = findViewById(R.id.btnNum3);
        btnNum4 = findViewById(R.id.btnNum4);
        btnNum5 = findViewById(R.id.btnNum5);
        btnNum6 = findViewById(R.id.btnNum6);
        btnNum7 = findViewById(R.id.btnNum7);
        btnNum8 = findViewById(R.id.btnNum8);
        btnNum9 = findViewById(R.id.btnNum9);
        btnClear = findViewById(R.id.btnClear);
        btnDelete = findViewById(R.id.btnDelete);
        btnShow = findViewById(R.id.btnShow);
        btnEnter = findViewById(R.id.btnEnter);
    }

    public void setEventClickViews(){
        btnNum0.setOnClickListener(this);
        btnNum1.setOnClickListener(this);
        btnNum2.setOnClickListener(this);
        btnNum3.setOnClickListener(this);
        btnNum4.setOnClickListener(this);
        btnNum5.setOnClickListener(this);
        btnNum6.setOnClickListener(this);
        btnNum7.setOnClickListener(this);
        btnNum8.setOnClickListener(this);
        btnNum9.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnEnter.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNum0:
                editNumber.append("0");
                break;
            case R.id.btnNum1:
                editNumber.append("1");
                break;
            case R.id.btnNum2:
                editNumber.append("2");
                break;
            case R.id.btnNum3:
                editNumber.append("3");
                break;
            case R.id.btnNum4:
                editNumber.append("4");
                break;
            case R.id.btnNum5:
                editNumber.append("5");
                break;
            case R.id.btnNum6:
                editNumber.append("6");
                break;
            case R.id.btnNum7:
                editNumber.append("7");
                break;
            case R.id.btnNum8:
                editNumber.append("8");
                break;
            case R.id.btnNum9:
                editNumber.append("9");
                break;
            case R.id.btnClear:
                editNumber.setText("");
                break;
            case R.id.btnDelete:
                editNumber.requestFocus();
                BaseInputConnection textFieldInputConnection = new BaseInputConnection(editNumber, true);
                textFieldInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                break;
            case R.id.btnShow:
                if(togglePassword) editNumber.setTransformationMethod(new PasswordTransformationMethod());
                else editNumber.setTransformationMethod(null);
                editNumber.setSelection(editNumber.length());
                togglePassword = !togglePassword;
                break;
            case R.id.btnEnter:

                break;
            case R.id.back:
                finish();
                System.exit(0);
                break;
        }
    }

}
