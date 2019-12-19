package com.ceshi.ha.views;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.ceshi.ha.R;

/**
 * 密码对话框
 */

public class PassWordDialog extends Dialog {

    Context context;
    View myView;
    LinearLayout ll_sendMessageForReset;

    TextView tv_dialog_title, tv_panduan, tv_title_pwd, tv_matchFalse, tv_colse_dialog;
    Button bt_colse_dialog, bt_send_captcha, bt_commit;

    private EditText[] mArray;
    private EditText EditText_Pwd1, EditText_Pwd2, EditText_Pwd3, EditText_Pwd4, EditText_Pwd5, EditText_Pwd6,
            EditText_Pwd7, EditText_Pwd8, EditText_Pwd9, EditText_Pwd10, EditText_Pwd11, EditText_Pwd12, ed_captcha;

    public PassWordDialog(Context context) {
        super(context);
    }

    public PassWordDialog(Context context, int width, int height, View layout, int style) {
        super(context, style);
        setContentView(layout);
        this.context = context;
        this.myView = layout;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        initView();
        buttonClick();
        setView();
    }

    private void setView() {
        for (int i = 0; i < mArray.length; i++) {
            final int j = i;
            mArray[j].addTextChangedListener(new TextWatcher() {
                private CharSequence temp;
                private int sStart;
                private int sEnd;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    temp = s;
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    tv_matchFalse.setText("");
                }

                @Override
                public void afterTextChanged(Editable s) {
                    sStart = mArray[j].getSelectionStart();
                    sEnd = mArray[j].getSelectionEnd();
                    if (temp.length() == 1 && (j == 0 || j == 1 || j == 2 || j == 3 || j == 4 || j == 5 ||
                            j == 6 || j == 7 || j == 8 || j == 9 || j == 10)) {
                        mArray[j + 1].setFocusable(true);
                        mArray[j + 1].setFocusableInTouchMode(true);
                        mArray[j + 1].requestFocus();
                    }
                    if (temp.length() > 1) {
                        s.delete(sStart - 1, sEnd);
                        int tSelection = sStart;
                        mArray[j].setText(s);
                        mArray[j].setSelection(tSelection);
                    }
                }
            });
            mArray[j].setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                        if (j > 0) {
                            mArray[j].setText("");
                            mArray[j - 1].requestFocus();
                        } else {
                            mArray[j].requestFocus();
                        }
                    }
                    return false;
                }
            });
        }
    }

    private void buttonClick() {
        tv_colse_dialog.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        bt_send_captcha.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String captcha = ed_captcha.getText().toString();
                ToastUtils.showShort("密码是:" + captcha, Toast.LENGTH_LONG);
            }
        });
        bt_commit.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = getPassWord(0, 6);
                String repeat = getPassWord(6, 12);
                if (first.equals(repeat)) {
                    Log.d("123", "密码相同");
                } else {
                    tv_matchFalse.setText("两次输入不一致，请重新输入");
                }
            }
        });
    }

    private String getPassWord(int start, int end) {
        StringBuilder a = new StringBuilder();
        for (int i = start; i < end; i++) {
            a.append(mArray[i].getText().toString());
        }
        return a.toString();
    }

    private void initView() {

        tv_dialog_title = (TextView) myView.findViewById(R.id.tv_dialog_title);
        tv_panduan = (TextView) myView.findViewById(R.id.tv_panduan);
        tv_title_pwd = (TextView) myView.findViewById(R.id.tv_title_pwd);
        tv_matchFalse = (TextView) myView.findViewById(R.id.tv_matchFalse);
        tv_colse_dialog = (TextView) myView.findViewById(R.id.tv_colse_dialog);

        bt_colse_dialog = (Button) myView.findViewById(R.id.bt_colse_dialog);
        bt_send_captcha = (Button) myView.findViewById(R.id.bt_send_captcha);
        bt_commit = (Button) myView.findViewById(R.id.bt_commit);

        ll_sendMessageForReset = (LinearLayout) myView.findViewById(R.id.ll_sendMessageForReset);

        EditText_Pwd1 = (EditText) myView.findViewById(R.id.EditText_Pwd1);
        EditText_Pwd2 = (EditText) myView.findViewById(R.id.EditText_Pwd2);
        EditText_Pwd3 = (EditText) myView.findViewById(R.id.EditText_Pwd3);
        EditText_Pwd4 = (EditText) myView.findViewById(R.id.EditText_Pwd4);
        EditText_Pwd5 = (EditText) myView.findViewById(R.id.EditText_Pwd5);
        EditText_Pwd6 = (EditText) myView.findViewById(R.id.EditText_Pwd6);
        EditText_Pwd7 = (EditText) myView.findViewById(R.id.EditText_Pwd7);
        EditText_Pwd8 = (EditText) myView.findViewById(R.id.EditText_Pwd8);
        EditText_Pwd9 = (EditText) myView.findViewById(R.id.EditText_Pwd9);
        EditText_Pwd10 = (EditText) myView.findViewById(R.id.EditText_Pwd10);
        EditText_Pwd11 = (EditText) myView.findViewById(R.id.EditText_Pwd11);
        EditText_Pwd12 = (EditText) myView.findViewById(R.id.EditText_Pwd12);

        mArray = new EditText[]{EditText_Pwd1, EditText_Pwd2, EditText_Pwd3, EditText_Pwd4, EditText_Pwd5, EditText_Pwd6,
                EditText_Pwd7, EditText_Pwd8, EditText_Pwd9, EditText_Pwd10, EditText_Pwd11, EditText_Pwd12};
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
