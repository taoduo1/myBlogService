package com.example.shop_common.common.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shop_common.common.constant.NumberConstant;
import com.example.shop_common.entity.BaseEntity;
import com.example.shop_common.utils.DataUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 扩展原有BaseMapper，提供编写语句的简单性。
 * <p>
 * findByCondition("f1", "v1", "f2", 2, "f3", Operator.beginWith, "333");
 * findByCondition("aaa", "123", "bbb", 22);
 * findByCondition("aaa", "123", "bbb", Operator.geaterThan, 22);
 * findByCondition("f1", Operator.notEqual, "v1", "f2", 2, "f3", Operator.beginWith, "333");
 * findByCondition("aaa", Operator.in, Arrays.asList("x", "d", "s"), "bbb", 22);
 * findByCondition("aaa", Operator.in, Arrays.asList("x", "d", "s"), "bbb", 22, SqlPartEnum.ORDERBY, "id");
 * findByCondition("aaa", Operator.in, Arrays.asList("x", "d", "s"), "bbb", 22, SqlPartEnum.ORDERBY, "id", Sort.ASC);
 * findByCondition("aaa", Operator.in, Arrays.asList("x", "d", "s"), "bbb", 22, SqlPartEnum.ORDERBY, "id", "name");
 * findByCondition("aaa", Operator.in, Arrays.asList("x", "d", "s"), "bbb", 22, SqlPartEnum.ORDERBY, "id", "name", Sort.DESC);
 *
 * @param <T> the generic type
 * @author duo.tao
 */
public interface ExtBaseMapper<T extends BaseEntity> extends BaseMapper<T> {


    default int batchInsert(List<T> entity) {
        if (DataUtil.isNullOrEmpty(entity)) return 0;
        entity.forEach(this::insert);
        return entity.size();
    }

    @Override
    default int deleteById(Serializable id){
        T data = selectById(id);
        if (DataUtil.isNull(data)) return NumberConstant.ZERO;
        data.setIsDeleted(1);
        return NumberConstant.ONE;
    }
}
