package com.ylzinfo.librarycard.cache;

import android.content.Context;
import android.text.TextUtils;

import com.ylzinfo.basiclib.utils.storage.SharedPreferencesUtil;

/**
 * Created by zhangShengZhong on 2019/3/1.
 * describe:
 */

public class TokenCache {

    /** TOKEN*/
    private static final String TOKEN_KEY = "token_key";
    public static void saveToken(Context context,String token) {
        SharedPreferencesUtil.getInstance(context).setString(TOKEN_KEY,token);
    }
    public static String getToken(Context context) {
        return SharedPreferencesUtil.getInstance(context).getString(TOKEN_KEY);
    }

    public static boolean isExistToken(Context context) {
        return !TextUtils.isEmpty(getToken(context));
    }


    public static void remove(Context context) {
        SharedPreferencesUtil.getInstance(context).remove(TOKEN_KEY);
    }
}
