package com.example.shop_common.common.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.shop_common.common.constant.NumberConstant;
import com.example.shop_common.entity.BaseEntity;
import com.example.shop_common.utils.DataUtil;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 基础mapper类，从mybatis-plus基础上进行拓展，方便后续或拓展，增加默认删除查询，is_deleted=0，后续增加默认tenantId查询
 * @param <T>
 */
public interface ExtBaseMapper<T extends BaseEntity> extends BaseMapper<T> {


    /**
     * 批量插入
     */
    default int batchInsert(List<T> entity) {
        if (DataUtil.isNullOrEmpty(entity)) return 0;
        entity.forEach(this::insert);
        return entity.size();
    }

    /**
     * 根据 ID 删除
     */
    @Override
    default int deleteById(Serializable id){
        T data = selectById(id);
        if (DataUtil.isNull(data)) return NumberConstant.ZERO;
        data.setIsDeleted(1);
        return updateById(data);
    }

    /**
     * 根据 columnMap 条件，删除记录
     */
    @Override
    default int deleteByMap(Map<String, Object> columnMap){
        columnMap.put("is_deleted",0);
        List<T> list = selectByMap(columnMap);
        list.forEach(o-> o.setIsDeleted(1));
        if (DataUtil.isNullOrEmpty(list)){
            return NumberConstant.ZERO;
        }
        list.forEach(o-> o.setIsDeleted(1));
        list.forEach(this::updateById);
        return list.size();
    }

    /**
     * 根据 entity 条件，删除记录
     */
    @Override
    default int delete(Wrapper<T> wrapper){
        QueryWrapper<T> queryWrapper= (QueryWrapper<T>) wrapper;
        queryWrapper.eq("is_deleted",0);
        List<T> list = selectList(queryWrapper);
        if (DataUtil.isNullOrEmpty(list)){
            return NumberConstant.ZERO;
        }
        list.forEach(o-> o.setIsDeleted(1));
        list.forEach(this::updateById);
        return list.size();
    }

    /**
     * 删除（根据ID 批量删除）
     */
    @Override
    default int deleteBatchIds(Collection<? extends Serializable> idList){
        List<T> list = selectBatchIds(idList);
        if (DataUtil.isNullOrEmpty(list)){
            return NumberConstant.ZERO;
        }
        list.forEach(o-> o.setIsDeleted(1));
        list.forEach(this::updateById);
        return list.size();
    }

    /**
     * 根据 whereEntity 条件，更新记录
     * Params:
     * entity – 实体对象 (set 条件值,可以为 null)
     * updateWrapper – 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     */
    default int update(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper){
        QueryWrapper<T> queryWrapper= (QueryWrapper<T>) updateWrapper;
        queryWrapper.eq("is_deleted",0);
        return update(entity,queryWrapper);
    }

    /**
     * 批量更新
     */
    default int batchUpdate(List<T> entity) {
        if (DataUtil.isNullOrEmpty(entity)) return 0;
        entity.forEach(this::updateById);
        return entity.size();
    }

    /**
     * 查询（根据 columnMap 条件）
     * @param columnMap 表字段 map 对象
     */
    default List<T> selectByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap){
        columnMap.put("is_deleted",0);
        return selectByMap(columnMap);
    }

    /**
     * 根据 entity 条件，查询一条记录
     */
    default T selectOne(@Param(Constants.WRAPPER) Wrapper<T> wrapper){
        QueryWrapper<T> queryWrapper= (QueryWrapper<T>) wrapper;
        queryWrapper.eq("is_deleted",0);
        return selectOne(queryWrapper);
    }


    @Override
    default Integer selectCount(Wrapper<T> wrapper){
        QueryWrapper<T> queryWrapper= (QueryWrapper<T>) wrapper;
        queryWrapper.eq("is_deleted",0);
        return selectCount(queryWrapper);
    }

    @Override
    default List<T> selectList(Wrapper<T> wrapper){
        QueryWrapper<T> queryWrapper= (QueryWrapper<T>) wrapper;
        queryWrapper.eq("is_deleted",0);
        return selectList(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param wrapper 实体对象封装操作类（可以为 null）
     */
    default List<Map<String, Object>> selectMaps(@Param(Constants.WRAPPER) Wrapper<T> wrapper){
        QueryWrapper<T> queryWrapper= (QueryWrapper<T>) wrapper;
        queryWrapper.eq("is_deleted",0);
        return selectMaps(queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录
     * <p>注意： 只返回第一个字段的值</p>
     *
     * @param wrapper 实体对象封装操作类（可以为 null）
     */
    default List<Object> selectObjs(@Param(Constants.WRAPPER) Wrapper<T> wrapper){
        QueryWrapper<T> queryWrapper= (QueryWrapper<T>) wrapper;
        queryWrapper.eq("is_deleted",0);
        return selectObjs(queryWrapper);
    }

    /**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
     * @param wrapper 实体对象封装操作类（可以为 null）
     */
    default IPage<T> selectPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> wrapper){
        QueryWrapper<T> queryWrapper= (QueryWrapper<T>) wrapper;
        queryWrapper.eq("is_deleted",0);
        return selectPage(page,queryWrapper);
    }

    /**
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     *
     * @param page         分页查询条件
     * @param wrapper 实体对象封装操作类
     */
    default IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> wrapper){
        QueryWrapper<T> queryWrapper= (QueryWrapper<T>) wrapper;
        queryWrapper.eq("is_deleted",0);
        return selectMapsPage(page,queryWrapper);
    }



}
