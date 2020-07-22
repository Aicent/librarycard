package com.ylzinfo.librarycard.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.ylzinfo.basiclib.base.BaseActivity;
import com.ylzinfo.basiclib.base.BasePresenter;
import com.ylzinfo.librarycard.api.ApiParameter;
import com.ylzinfo.librarycard.bean.LCResult;
import com.ylzinfo.librarycard.bean.LibraryCardUser;
import com.ylzinfo.librarycard.bean.ScanCodeReultBean;
import com.ylzinfo.librarycard.config.Config;
import com.ylzinfo.ylzhttp.YlzHttp;
import com.ylzinfo.ylzhttp.callback.ClassCallBack;
import com.ylzinfo.ylzhttp.callback.StringCallback;
import com.ylzinfo.ylzhttp.exception.MyException;
import com.ylzinfo.ylzhttp.request.RequestCall;
import com.ylzinfoi.librarycard.R;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

public class LibraryCardBindActivity extends BaseActivity {

    private static final String USER_KEY = "library_card_key";
    private static final int SCAN_DEFAULT_REQUEST_CODE = 10;
    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 11;
    private LibraryCardUser libraryCardUser;
    private TextView tv_library_card_bind_name;
    private TextView tv_library_card_bind_idno;
    private TextView tv_library_card_bind_phone;
    private TextView et_library_card_bind_no;
    private TextView tv_library_card_bind;
    private ImageView iv_saoma;


    public static void start(Activity activity, LibraryCardUser cardUser) {
        Intent intent = new Intent(activity, LibraryCardBindActivity.class);
        intent.putExtra(USER_KEY, cardUser);
        activity.startActivity(intent);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_library_card_bind;
    }

    @Override
    public void initSuperData() {
        libraryCardUser = (LibraryCardUser) getIntent().getSerializableExtra(USER_KEY);
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        tv_library_card_bind_name = findViewById(R.id.tv_library_card_bind_name);
        tv_library_card_bind_idno = findViewById(R.id.tv_library_card_bind_idno);
        tv_library_card_bind_phone = findViewById(R.id.tv_library_card_bind_phone);
        et_library_card_bind_no = findViewById(R.id.et_library_card_bind_no);
        tv_library_card_bind = findViewById(R.id.tv_library_card_bind);
        iv_saoma = findViewById(R.id.iv_saoma);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("我的借书证");
    }

    @Override
    public void initListener() {
        tv_library_card_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind();
            }
        });

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        iv_saoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCaramePermission();
                          }
        });
    }

    private void checkCaramePermission() {
        if (ContextCompat.checkSelfPermission(LibraryCardBindActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(LibraryCardBindActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
            //有权限，直接扫码
            startActivityForResult(new Intent(LibraryCardBindActivity.this,LCScanActivity.class),SCAN_DEFAULT_REQUEST_CODE);

        } else {
            // 申请权限
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(LibraryCardBindActivity.this,
                    new String[]{Manifest.permission.CAMERA, 				Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_TAKE_PHOTO_PERMISSION);
        }
    }


    @Override
    public void initData() {
        tv_library_card_bind_name.setText(libraryCardUser.getUsername());
        tv_library_card_bind_idno.setText(libraryCardUser.getIdno());
        tv_library_card_bind_phone.setText(libraryCardUser.getPhone());
    }

    private void bind() {
        String no = et_library_card_bind_no.getText().toString().trim();
        if (TextUtils.isEmpty(no)) {
            Toast.makeText(LibraryCardBindActivity.this, "请先输入借书证号码", Toast.LENGTH_LONG).show();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("libraryCardNo", no);
        map.put("username", libraryCardUser.getUsername());
        map.put("idno", libraryCardUser.getIdno());
        map.put("phone", libraryCardUser.getPhone());
        final RequestCall requestCall = YlzHttp.postJson()
                .setUrl(Config.BASE_URL + "/api/bindLibraryCard")
                .setContent(map)
                .build();
        requestCall.enqueue(new ClassCallBack<LCResult>() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showLoading();
            }

            @Override
            public void onError(Call call, MyException e, int id) {
                Toast.makeText(LibraryCardBindActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onResponse(LCResult response, int id) {
                if (response.getResultCode() == 1) {
                    showBindSuccessDialog();
                } else {
                    Toast.makeText(LibraryCardBindActivity.this, response.getResultMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissLoading();
            }
        });
    }

    private void showBindSuccessDialog() {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("您已绑定成功").positiveText("确认")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .build().show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SCAN_DEFAULT_REQUEST_CODE && data != null) {
            String result = data.getStringExtra(LCScanActivity.SCAN_RESULT);
            et_library_card_bind_no.setText(result);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以扫码
                startActivityForResult(new Intent(LibraryCardBindActivity.this,LCScanActivity.class),SCAN_DEFAULT_REQUEST_CODE);
            } else {
                Toast.makeText(this, "未开启相机权限，无法扫描二维码", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




}
