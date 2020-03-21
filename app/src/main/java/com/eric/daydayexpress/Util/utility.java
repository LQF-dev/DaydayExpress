package com.eric.daydayexpress.Util;

import com.eric.daydayexpress.Gson.logistics;
import com.google.gson.Gson;

public class utility {
     static public logistics handleJsonFromKdNiao(String respond){
       return new Gson().fromJson(respond,logistics.class);

    }
}
