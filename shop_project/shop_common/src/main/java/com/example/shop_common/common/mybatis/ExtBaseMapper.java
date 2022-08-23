package com.example.shop_common.common.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shop_common.entity.base.BaseEntity;


/**
 * @author duo.tao
 * @Description: 基础mapper类，从mybatis-plus基础上进行拓展，方便后续或拓展，增加默认删除查询，is_deleted=0，后续增加默认tenantId查询
 * @date 2022-06-13 23:14
 */
public interface ExtBaseMapper<T extends BaseEntity> extends BaseMapper<T> {



    /**
     * 批量保存
     *//*
    default int batchSave(List<T> entity) {
        if (DataUtil.isNullOrEmpty(entity)) return 0;
        entity.forEach(this::save);
        return entity.size();
    }

    *//**
     * 根据 ID 删除
     *//*
    @Override
    default int deleteById(Serializable id) {
        T data = selectById(id);
        if (DataUtil.isNull(data)) return NumberConstant.ZERO;
        data.setIsDeleted(1);
        return this.save(data);
    }

    *//**
     * 根据 columnMap 条件，删除记录
     *//*
    @Override
    default int deleteByMap(Map<String, Object> columnMap) {
        columnMap.put("is_deleted", 0);
        List<T> list = selectListByMap(columnMap);
        list.forEach(o -> o.setIsDeleted(1));
        if (DataUtil.isNullOrEmpty(list)) {
            return NumberConstant.ZERO;
        }
        list.forEach(o -> o.setIsDeleted(1));
        list.forEach(this::save);
        return list.size();
    }

    *//**
     * 根据 entity 条件，删除记录
     *//*
    @Override
    default int delete(Wrapper<T> wrapper) {
        QueryWrapper<T> queryWrapper =  new QueryWrapper();
        queryWrapper.eq("is_deleted", 0);
        List<T> list = selectListByWrapper(queryWrapper);
        if (DataUtil.isNullOrEmpty(list)) {
            return NumberConstant.ZERO;
        }
        list.forEach(o -> o.setIsDeleted(1));
        list.forEach(this::updateById);
        return list.size();
    }

    *//**
     * 删除（根据ID 批量删除）
     *//*
    @Override
    default int deleteBatchIds(Collection<? extends Serializable> idList) {
        List<T> list = selectBatchIds(idList);
        if (DataUtil.isNullOrEmpty(list)) {
            return NumberConstant.ZERO;
        }
        list.forEach(o -> o.setIsDeleted(1));
        list.forEach(this::updateById);
        return list.size();
    }

    *//**
     * 根据 whereEntity 条件，更新记录
     * Params:
     * entity – 实体对象 (set 条件值,可以为 null)
     * updateWrapper – 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     *//*
    @Override
    default int update(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper) {
        QueryWrapper<T> queryWrapper = (QueryWrapper<T>) updateWrapper;
        queryWrapper.eq("is_deleted", 0);
        return update(entity, queryWrapper);
    }

    *//**
     * 批量更新
     *//*
    default int batchUpdate(List<T> entity) {
        if (DataUtil.isNullOrEmpty(entity)) return 0;
        entity.forEach(this::updateById);
        return entity.size();
    }

    *//**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     *//*
    default List<T> selectListByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap) {
        columnMap.put("is_deleted", 0);
        return selectByMap(columnMap);
    }

    *//**
     * 根据 entity 条件，查询一条记录
     *//*
    default T selectOneByWrapper(QueryWrapper<T> wrapper) {
        wrapper.eq("is_deleted", 0);
        return selectOne(wrapper);
    }


    default Integer selectCountByWrapper(Wrapper<T> wrapper) {
        QueryWrapper<T> queryWrapper =  new QueryWrapper();
        queryWrapper.eq("is_deleted", 0);
        return selectCount(queryWrapper);
    }

    default List<T> selectListByWrapper(Wrapper<T> wrapper) {
        QueryWrapper<T> queryWrapper =  new QueryWrapper();
        queryWrapper.eq("is_deleted", 0);
        return selectList(queryWrapper);
    }

    *//**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param wrapper 实体对象封装操作类（可以为 null）
     *//*
    default List<Map<String, Object>> selectMapsByWrapper(@Param(Constants.WRAPPER) Wrapper<T> wrapper) {
        QueryWrapper<T> queryWrapper =  new QueryWrapper();
        queryWrapper.eq("is_deleted", 0);
        return selectMaps(queryWrapper);
    }

    *//**
     * 根据 Wrapper 条件，查询全部记录
     * <p>注意： 只返回第一个字段的值</p>
     *
     * @param wrapper 实体对象封装操作类（可以为 null）
     *//*
    default List<Object> selectObjsWrapper(@Param(Constants.WRAPPER) Wrapper<T> wrapper) {
        QueryWrapper<T> queryWrapper =  new QueryWrapper();
        queryWrapper.eq("is_deleted", 0);
        return selectObjs(queryWrapper);
    }

    *//**
     * 根据 entity 条件，查询全部记录（并翻页）
     *
     * @param page    分页查询条件（可以为 RowBounds.DEFAULT）
     * @param wrapper 实体对象封装操作类（可以为 null）
     *//*
    default IPage<T> selectPageByWrapper(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> wrapper) {
        QueryWrapper<T> queryWrapper =  new QueryWrapper();
        queryWrapper.eq("is_deleted", 0);
        return selectPage(page, queryWrapper);
    }

    *//**
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     *
     * @param page    分页查询条件
     * @param wrapper 实体对象封装操作类
     *//*
    default IPage<Map<String, Object>> selectMapsPageByWrapper(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> wrapper) {
        QueryWrapper<T> queryWrapper =  new QueryWrapper();
        queryWrapper.eq("is_deleted", 0);
        return selectMapsPage(page, queryWrapper);
    }*/


}
