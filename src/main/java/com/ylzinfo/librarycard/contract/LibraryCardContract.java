package com.ylzinfo.librarycard.contract;

import com.ylzinfo.basiclib.base.IModel;
import com.ylzinfo.basiclib.base.IView;
import com.ylzinfo.librarycard.bean.LibraryCardUser;
import com.ylzinfo.librarycard.bean.QueryLibraryCardInfoBean;
import com.ylzinfo.ylzhttp.request.RequestCall;

public interface LibraryCardContract {
    public interface Model extends IModel {
        RequestCall queryLibraryCardInfo(String token, LibraryCardUser libraryCardUser);

        RequestCall unBind(String token, LibraryCardUser libraryCardUser);
    }

    public interface View extends IView {
        void getDataSuccess(QueryLibraryCardInfoBean.ResultBodyBean bean);

        void getNoData();

    }
}
