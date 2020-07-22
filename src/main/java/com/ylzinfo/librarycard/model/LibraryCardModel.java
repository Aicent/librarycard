package com.ylzinfo.librarycard.model;

import com.ylzinfo.librarycard.api.ApiParameter;
import com.ylzinfo.librarycard.bean.LibraryCardUser;
import com.ylzinfo.librarycard.config.Config;
import com.ylzinfo.librarycard.contract.LibraryCardContract;
import com.ylzinfo.ylzhttp.YlzHttp;
import com.ylzinfo.ylzhttp.request.RequestCall;

import java.util.HashMap;

public class LibraryCardModel implements LibraryCardContract.Model {
    @Override
    public RequestCall queryLibraryCardInfo(String token, LibraryCardUser libraryCardUser) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username",libraryCardUser.getUsername());
        map.put("idno",libraryCardUser.getIdno());
        map.put("phone",libraryCardUser.getPhone());

        return YlzHttp.postJson()
                .setUrl(Config.BASE_URL+"/api/queryLibraryCardInfo")
                .setContent(map)
                .build();
    }

    @Override
    public RequestCall unBind(String token, LibraryCardUser libraryCardUser) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username",libraryCardUser.getUsername());
        map.put("idno",libraryCardUser.getIdno());
        map.put("phone",libraryCardUser.getPhone());

        return YlzHttp.postJson()
                .setUrl(Config.BASE_URL+"/api/unBindLibraryCard")
                .setContent(map)
                .build();
    }
}
