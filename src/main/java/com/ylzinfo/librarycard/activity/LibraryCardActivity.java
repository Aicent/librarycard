package com.ylzinfo.librarycard.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ylzinfo.basiclib.base.BaseActivity;
import com.ylzinfo.librarycard.bean.LibraryCardUser;
import com.ylzinfo.librarycard.bean.QueryLibraryCardInfoBean;
import com.ylzinfo.librarycard.contract.LibraryCardContract;
import com.ylzinfo.librarycard.presenter.LibraryCardPresenter;
import com.ylzinfoi.librarycard.R;
import com.ylzinfo.librarycard.cache.TokenCache;

public class LibraryCardActivity extends BaseActivity<LibraryCardPresenter> implements LibraryCardContract.View {


    private static final String USER_KEY = "library_card_key";
    private LinearLayout ll_no_data;
    private LinearLayout ll_library_card_content;
    private TextView tv_library_card_name;
    private TextView tv_library_card_no;
    private TextView tv_library_card_idno;
    private TextView tv_library_card_phone;
    private TextView tv_library_card_unbind;
    private TextView tv_library_card_add;
    private LibraryCardUser libraryCardUser;

    public static void start(Activity activity, LibraryCardUser cardUser) {
        Intent intent = new Intent(activity, LibraryCardActivity.class);
        intent.putExtra(USER_KEY,cardUser);
        activity.startActivity(intent);
    }

    @Override
    public LibraryCardPresenter initPresenter() {
        return new LibraryCardPresenter();
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_library_card;
    }

    @Override
    public void initSuperData() {
        libraryCardUser = (LibraryCardUser) getIntent().getSerializableExtra(USER_KEY);
        TokenCache.saveToken(LibraryCardActivity.this.getApplicationContext(), libraryCardUser.getToken());
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        initView();
        initRV();
    }

    private void initView() {
        ll_no_data = findViewById(R.id.ll_no_data);
        tv_library_card_name = findViewById(R.id.tv_library_card_name);
        tv_library_card_no = findViewById(R.id.tv_library_card_no);
        tv_library_card_idno = findViewById(R.id.tv_library_card_idno);
        tv_library_card_phone = findViewById(R.id.tv_library_card_phone);
        tv_library_card_unbind = findViewById(R.id.tv_library_card_unbind);
        ll_library_card_content = findViewById(R.id.ll_library_card_content);
        tv_library_card_add = findViewById(R.id.tv_library_card_add);
        tv_library_card_add = findViewById(R.id.tv_library_card_add);
       TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("我的借书证");

    }

    @Override
    public void initListener() {
        tv_library_card_unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCheckDialog();
            }
        });
        tv_library_card_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LibraryCardBindActivity.start(LibraryCardActivity.this,libraryCardUser);
            }
        });

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showCheckDialog() {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("是否确认解除绑定").negativeText("取消").positiveText("确认")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mPresenter.unbind(libraryCardUser.getToken(),libraryCardUser);
                    }
                })
                .build().show();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getData(libraryCardUser.getToken(),libraryCardUser);
    }

    private void initRV() {

    }

    @Override
    public void getDataSuccess(QueryLibraryCardInfoBean.ResultBodyBean bean) {
        ll_no_data.setVisibility(View.GONE);
        ll_library_card_content.setVisibility(View.VISIBLE);
        tv_library_card_name.setText("姓名："+bean.getRealName());
        tv_library_card_idno.setText("身份证号："+bean.getIdCard());
        tv_library_card_no.setText("借书证："+bean.getLibraryCardNo());
    }

    @Override
    public void getNoData() {
        ll_no_data.setVisibility(View.VISIBLE);
        ll_library_card_content.setVisibility(View.GONE);
    }
}
