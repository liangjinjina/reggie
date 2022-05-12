package com.itwj.reggie.common_class;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

//通用返回结果类，服务器端相应的数据最终都会封装成此对象
@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    public static <T> R<T> success(T object) {//静态方法 成功就内部创建R对象
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {//静态方法 失败就内部创建R对象
        R<T> r = new R<T>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
