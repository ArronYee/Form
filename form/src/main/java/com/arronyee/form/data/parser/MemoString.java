package com.arronyee.form.data.parser;


/**
 * Created by yeying on 2018/9/21.
 * 字符串类型的解析器（多行）
 */

public class MemoString extends DataParser {
    public int start;
    public int end;
    public static final String TAG = "memo";

    {
        type = TAG;
    }

    private MemoString() {
    }

    public MemoString(String typeStr) {
        try {
            String[] infos = typeStr.split(":");
            String[] textStringInfos = infos[1].split("/");
            setStart(Integer.parseInt(textStringInfos[0]));
            setEnd(Integer.parseInt(textStringInfos[1]));
        } catch (Exception e) {
            e.printStackTrace();
            setStart(0);
            setEnd(100);
        }
    }

    public boolean validate() {
//        if (TextUtils.isEmpty(value)) {
            if (required == 1) {
                validateError(type, lebel, name, lebel + "为必填项");
                return false;
            } else {
                return true;
            }
//        } else {
//            if (name.toLowerCase().contains("cardnum")) {
//                boolean result = RegexUtils.isIDCard18(value);
//                if (!result) {
//                    validateError(type, lebel, name, lebel + "校验错误");
//                }
//                return result;
//            } else if (name.toLowerCase().contains("phone")) {
//                boolean result = RegexUtils.isMobileSimple(value);
//                if (!result) {
//                    validateError(type, lebel, name, lebel + "校验错误");
//                }
//                return result;
//            } else {
//                if (value.length() < start || value.length() > end) {
//                    validateError(type, lebel, name, lebel + "长度为" + start + "-" + end);
//                }
//            }
//        }
//        return true;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

}
