package com.example.shop_common.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * @author duo.tao
 * @Description: 基础的crud接口
 * @date 2022-06-13 23:14
 */
public interface CrudService<T> {

	/**
     * 获取bean.
     *
     * @param id the id
     * @return the t
     */
	T get(Serializable id);

	/**
	 * 批量获取bean
	 */
	List<T> getList(List<Serializable> idList);
	
	/**
     * 更新.
     *
     * @param entity the entity
     * @return the int
     */
	int insert(T entity);

	/**
	 * 更新.
	 *
	 * @param entity the entity
	 * @return the int
	 */
	int save(T entity);
	
	/**
	 * 批量更新
	 */
	void batchInsert(List<T> entityList);

	/**
     * 删除.
     *
     * @param id the id
     * @return the int
     */
	int delete(String id);

    /**
     * Upsert.
     *
     * @param entity the entity
     * @return the int
     */
    int upsert(T entity);

    /**
     * Update.
     *
     * @param entity the entity
     * @return the int
     */
    int update(T entity);

    /**
     * Find the one by condition.
     *
     * @param queryWrapper the queryWrapper
     * @return the t
     */
    T findOneByCondition(QueryWrapper<T> queryWrapper);

    /**
     * Find the by condition.
     *
     * @param queryWrapper the queryWrapper
     * @return the list
     */
    List<T> findByCondition(Wrapper<T> queryWrapper);

	/**
	 * Find Count by condition.
	 *
	 * @param queryWrapper the queryWrapper
	 * @return the list
	 */
	int selectCount(Wrapper<T> queryWrapper);

    /**
     * Delete by condition.
     *
     * @param queryWrapper the objects
     * @return the int
     */
    int deleteByCondition(Wrapper<T> queryWrapper);

    /**
     * Count by condition.
     *
     * @param queryWrapper the queryWrapper
     * @return the long
     */
	Integer countByCondition(Wrapper<T> queryWrapper);


	IPage<T> selectPage(IPage<T> page,Wrapper<T> wrapper);

	IPage<Map<String, Object>> selectMapsPage(IPage<T> page, Wrapper<T> wrapper);
}
