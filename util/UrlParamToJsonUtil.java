package util;

import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import util.constants.Symbol;

/**
 * @author zqw
 * @date 2022/8/26
 */
public class UrlParamToJsonUtil {
    public static JSONObject getJson(String paramStr) {

        if (StringUtil.isEmpty(paramStr) || !paramStr.contains(Symbol.QUESTION)) {
            return new JSONObject();
        }
        paramStr = paramStr.substring(paramStr.indexOf(Symbol.QUESTION) + 1);
        String[] params = paramStr.split(Symbol.AND);
        int limitR = 2;
        JSONObject obj = new JSONObject();
        for (String s : params) {
            String[] param = s.split(Symbol.EQUAL);
            if (param.length >= limitR) {
                String key = param[0];
                StringBuilder value = new StringBuilder(param[1]);
                for (int j = limitR; j < param.length; j++) {
                    value.append(Symbol.EQUAL).append(param[j]);
                }
                obj.put(key, value.toString());
            }
        }
        return obj;
    }

    public static void main(String[] args) {
        System.out.println(getJson("https://www.youtube.com/watch?v=u3Xy5D4HQxo&ab_channel=BigBangMusic"));
    }
}
