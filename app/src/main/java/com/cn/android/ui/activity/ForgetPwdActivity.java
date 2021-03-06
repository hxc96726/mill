package com.cn.android.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cn.android.R;
import com.cn.android.common.MyActivity;
import com.cn.android.network.Constant;
import com.cn.android.network.ServerUrl;
import com.cn.android.presenter.PublicInterfaceePresenetr;
import com.cn.android.presenter.view.PublicInterfaceView;
import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.view.CountdownView;
import com.hjq.widget.view.PasswordEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPwdActivity extends MyActivity implements PublicInterfaceView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.prompt1)
    TextView prompt1;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.edit_yzm)
    EditText editYzm;
    @BindView(R.id.cv_password_forget_countdown)
    CountdownView cvPasswordForgetCountdown;
    @BindView(R.id.prompt2)
    TextView prompt2;
    @BindView(R.id.new_pwd)
    PasswordEditText newPwd;
    @BindView(R.id.again_new_pwd)
    PasswordEditText againNewPwd;
    PublicInterfaceePresenetr presenetr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initView() {
        presenetr = new PublicInterfaceePresenetr(this);

    }
    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        //保存
        if (isCheck()) {
            showLoading();
            presenetr.getPostTokenRequest(getActivity(), ServerUrl.updateFirstPayWord, Constant.updateFirstPayWord);
        }
    }
    private boolean isCheck() {
        if (TextUtils.isEmpty(editPhone.getText().toString().trim())) {
            ToastUtils.show("请输入手机号");
            return false;
        }
        if (TextUtils.isEmpty(editYzm.getText().toString().trim())) {
            ToastUtils.show("请输入验证码");
            return false;
        }
        if (TextUtils.isEmpty(newPwd.getText().toString().trim())) {
            ToastUtils.show("请输入新密码");
            return false;
        }
        if (TextUtils.isEmpty(againNewPwd.getText().toString().trim())) {
            ToastUtils.show("请再次输入新密码");
            return false;
        }
        if (!againNewPwd.getText().toString().trim().equals(newPwd.getText().toString().trim())) {
            ToastUtils.show("两次新密码输入不一致");
            return false;
        }
        return true;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public Map<String, Object> setPublicInterfaceData(int tag) {
        Map<String, Object> map = new HashMap<>();
        map.put("authorization", UserBean().getToken());
        map.put("userphone", editPhone.getText().toString().trim());
        map.put("smscode", editYzm.getText().toString().trim());
        map.put("pay_password", newPwd.getText().toString().trim());
        return map;
    }

    @Override
    public void onPublicInterfaceSuccess(String data, int tag) {
        showComplete();
        finish();
    }

    @Override
    public void onPublicInterfaceError(String error, int tag) {
        showComplete();
        ToastUtils.show(error);
    }
}
