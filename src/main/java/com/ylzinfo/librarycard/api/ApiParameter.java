package com.ylzinfo.librarycard.api;

import com.ylzinfo.ylzhttp.utils.GsonUtils;
import com.ylzinfo.librarycard.cache.TokenCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangShengZhong on 2019/3/1.
 * describe:
 */

public class ApiParameter {


    /**
     * 将请求参数再次封装，并加密
     * @param map 参数
     * @return
     */
    public static Map<String, Object> getParameter(String token,Map<String, Object> map){

        // 添加公共token
        map.put("accessToken", token);

        String p = GsonUtils.getInstance().getGson().toJson(map);


        Map<String, Object> parameter = new HashMap<>();
        parameter.put("accessId","1000000000");

        // 对请求参数进行加密
        String secretValue = CryptoUtils.getInstance().encrypt(p);
        parameter.put("secretValue", secretValue);
        return parameter;
    }


}
