package com.ceshi.ha.m1card;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.widget.Toast;

import com.ceshi.ha.BuildConfig;

import java.io.IOException;

import static android.R.attr.action;

/**
 * Created by wy on 2017/8/3.
 */

public class ReadM1 extends Activity {

    private MifareClassic mfc;
    Context mContext;
    Intent intent;

    private NfcAdapter mAdapter;
    private String[][] techList;
    private IntentFilter[] intentFilters;
    private PendingIntent pendingIntent;
    private Tag tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNFC();
    }

    /**
     * 定义程序可以兼容的nfc协议，例子为nfca和nfcv；
     * 在Intent filters里声明你想要处理的Intent，一个tag被检测到时先检查前台发布系统，
     * 如果前台Activity符合Intent filter的要求，那么前台的Activity的将处理此Intent。
     * 如果不符合，前台发布系统将Intent转到Intent发布系统。如果指定了null的Intent filters，
     * 当任意tag被检测到时，你将收到TAG_DISCOVERED intent。因此请注意你应该只处理你想要的Intent。
     */
    public void setNFC() {
        //获取nfc适配器
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        techList = new String[][]{
                new String[]{android.nfc.tech.NfcV.class.getName()},
                new String[]{android.nfc.tech.NfcA.class.getName()}};
        intentFilters = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED),};
        //创建一个 PendingIntent 对象, 这样Android系统就能在一个tag被检测到时定位到这个对象
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    //重写onNewIntent方法
    @Override
    protected void onNewIntent(Intent intent) {
        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        super.onNewIntent(intent);
    }

    @Override
    protected void onPause() {
        mAdapter.disableForegroundDispatch(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //使用前台发布系统
        mAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, techList);
    }

    /**
     * 读取m1卡数据
     */
    void read(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        boolean auth = false;
        MifareClassic mifareClassic = MifareClassic.get(tag);
        try {
            String nfcInfo = "";
            mifareClassic.connect();
            int sectorCount = mifareClassic.getSectorCount();//获取m1卡扇区数，一般m1卡扇区数为１６
            byte bytes[] = new byte[1024 * 8];
            int count = 0;
            //0扇区为m1卡id,不可更改，从１扇区读取
            for (int i = 1; i < sectorCount; i++) {
                auth = mifareClassic.authenticateSectorWithKeyA(i, MifareClassic.KEY_DEFAULT);//用默认密码验证i块，正确可读数据
                if (auth) {
                    //每个扇区0-2块存储数据,3块为控制块
                    for (int j = 0; j < 3; j++) {
                        byte[] data = mifareClassic.readBlock(i * 4 + j);
                        for (int k = 0; k < data.length; k++) {
                            bytes[count++] = data[k];
                        }
                    }
                }
            }
            nfcInfo += new String(bytes, "UTF-8");//转换为字符串
            if (nfcInfo == null || nfcInfo.equals("")) {
                Toast.makeText(this, "读取失败", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "数据:" + nfcInfo, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入数据
     */
    void write(Intent intent) {
        Tag tagFormIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        MifareClassic mifareClassic = MifareClassic.get(tagFormIntent);
        //验证1块，验证正确可写入
        try {
            if (mifareClassic.authenticateSectorWithKeyA(1, MifareClassic.KEY_DEFAULT)) {
                mifareClassic.writeBlock(4, "1234".getBytes("UTF-8"));//数据写入1扇区４块中,写入数据必须是16字节,自己补全数据
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    void readBlock() {
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            mfc = MifareClassic.get(tagFromIntent);
            if (mfc != null) {
                Toast.makeText(mContext, "检测到卡片,读卡中。。。", Toast.LENGTH_SHORT).show();
                try {
                    mfc.connect();
                    boolean auth = false;
                    auth = mfc.authenticateSectorWithKeyA(15, "passwo".getBytes());//验证密码
                    if (auth) {
//                        card_number_edittext.setText(new String(mfc.readBlock(60)));//读取M1卡的第60块即15扇区第0块
//                        password_edittext.requestFocus();
                    }
                } catch (Exception e) {
                    if (BuildConfig.DEBUG) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    void writeBlock() {
        try {
            mfc.connect();
            boolean auth = false;
            short sectorAddress = 1;
            auth = mfc.authenticateSectorWithKeyA(sectorAddress, MifareClassic.KEY_DEFAULT);
            if (auth) {
                // the last block of the sector is used for KeyA and KeyB cannot be overwritted
                mfc.writeBlock(4, "1383838438000000".getBytes());//必须为16字节不够自己补0
                mfc.close();
                Toast.makeText(mContext, "写入成功", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                mfc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
