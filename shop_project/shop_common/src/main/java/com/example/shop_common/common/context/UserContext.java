package com.example.shop_common.common.context;

import com.example.shop_common.common.constant.NormalConstant;
import com.example.shop_common.utils.DataUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


/**
 * @author duo.tao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {

    private int userId;
    private String userName;
    private Integer tenantId;
    /**
     * 此次访问token
     */
    private String token = StringUtils.EMPTY;

    @JsonIgnore
    public String getUserFlag() {
        return userId + NormalConstant.SPLIT + tenantId;
    }

    @JsonIgnore
    public String getUserFlagMore() {
        return userName + NormalConstant.SPLIT + userId + NormalConstant.SPLIT + tenantId;
    }

    @JsonIgnore
    public void genContext(Integer userId,String userName,Integer tenantId,String token) {
        if (DataUtil.isNull(userId)) {return;}
        this.setUserId(userId);
        this.setUserName(userName);
        this.setTenantId(tenantId);
        this.setToken(token);
    }
}
