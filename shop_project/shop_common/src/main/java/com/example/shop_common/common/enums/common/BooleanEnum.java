package com.example.shop_common.common.enums.common;

import com.example.shop_common.common.enums.IIndex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Company 上海歆成
 * @Description:
 * @Author staton
 * @Date 2020/7/24 16:02
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum BooleanEnum implements IIndex {

    TRUE(1, "1","是"),
    FALSE(0, "0","否");

    private int code;
    private String strCode;
    private String desc;

    public static String getByCode(Integer code) {
        String ret = null;
        for (BooleanEnum enumItem : BooleanEnum.values()) {
            if (enumItem.getIndex() == code) {
                ret = enumItem.desc;
            }
        }
        return ret;
    }

    public static BooleanEnum getByDesc(String desc) {
        BooleanEnum ret = null;
        for (BooleanEnum enumItem : BooleanEnum.values()) {
            if (enumItem.getDesc().equals(desc)) {
                ret = enumItem;
            }
        }
        return ret;
    }

    @Override
    public int getIndex() {
        return code;
    }


}
