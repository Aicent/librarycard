package com.ylzinfo.librarycard.presenter;

import android.util.Log;
import android.widget.Toast;

import com.ylzinfo.basiclib.base.BasePresenter;
import com.ylzinfo.basiclib.utils.ToastUtil;
import com.ylzinfo.librarycard.bean.LCResult;
import com.ylzinfo.librarycard.bean.LibraryCardUser;
import com.ylzinfo.librarycard.bean.QueryLibraryCardInfoBean;
import com.ylzinfo.librarycard.model.LibraryCardModel;
import com.ylzinfo.ylzhttp.callback.ClassCallBack;
import com.ylzinfo.ylzhttp.callback.StringCallback;
import com.ylzinfo.ylzhttp.exception.MyException;
import com.ylzinfo.ylzhttp.request.RequestCall;
import com.ylzinfo.librarycard.contract.LibraryCardContract;

import okhttp3.Call;
import okhttp3.Request;

public class LibraryCardPresenter extends BasePresenter<LibraryCardContract.Model, LibraryCardContract.View> {

    @Override
    protected LibraryCardContract.Model initModel() {
        return new LibraryCardModel();
    }

    public void getData(String token, LibraryCardUser libraryCardUser) {
       RequestCall requestCall =  mModel.queryLibraryCardInfo(token,libraryCardUser);
       requestCall.enqueue(new ClassCallBack<QueryLibraryCardInfoBean>() {

           @Override
           public void onBefore(Request request, int id) {
               super.onBefore(request, id);
               mView.showLoading();
           }

           @Override
           public void onError(Call call, MyException e, int id) {
               Toast.makeText(mView.getActContext(),e.getMessage(),Toast.LENGTH_LONG).show();

           }

           @Override
           public void onResponse(QueryLibraryCardInfoBean response, int id) {
               if (response.getResultCode() == 1){
                   QueryLibraryCardInfoBean.ResultBodyBean bean = response.getResultBody();
                   mView.getDataSuccess(bean);
               }else {
                   Toast.makeText(mView.getActContext(),response.getResultMsg(),Toast.LENGTH_LONG).show();
                   mView.getNoData();
               }
           }

           @Override
           public void onAfter(int id) {
               super.onAfter(id);
               mView.dismissLoading();
           }
       });
    }


    public void unbind(final String token, final LibraryCardUser libraryCardUser) {
        RequestCall requestCall =   mModel.unBind(token,libraryCardUser);
        requestCall.enqueue(new ClassCallBack<LCResult>() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                mView.showLoading();
            }

            @Override
            public void onError(Call call, MyException e, int id) {
                Toast.makeText(mView.getActContext(),e.getMessage(),Toast.LENGTH_LONG).show();

            }


            @Override
            public void onResponse(LCResult result, int id) {
                mView.dismissLoading();
                if (result.getResultCode() == 1){
                    getData(token,libraryCardUser);
                }else {
                    Toast.makeText(mView.getActContext(),result.getResultMsg(),Toast.LENGTH_LONG).show();
                }
            }


        });
    }


}
