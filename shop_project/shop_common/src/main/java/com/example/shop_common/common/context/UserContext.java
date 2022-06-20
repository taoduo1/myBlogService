package com.example.shop_common.common.context;

import com.example.shop_common.common.constant.NormalConstant;
import com.example.shop_common.utils.DataUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {

    private int userId;
    private String userName;
    private String tenantId;
    private String tenantName;
    private String token = StringUtils.EMPTY;// 此次访问token

    @JsonIgnore
    public String getUserFlag() {
        return userId + NormalConstant.SPLIT + tenantId;
    }

    @JsonIgnore
    public String getUserFlagMore() {
        return userName + NormalConstant.SPLIT + userId + NormalConstant.SPLIT + tenantId;
    }

    @JsonIgnore
    public void genFromSession(HttpSession session) {
        if (DataUtil.isNull(session.getAttribute("userId"))) return;
        this.setUserId((Integer) session.getAttribute("userId"));
        this.setUserName((String) session.getAttribute("staffName"));
        this.setTenantId((String) session.getAttribute("tenantId"));
        this.setTenantName((String) session.getAttribute("tenantName"));
    }
}
