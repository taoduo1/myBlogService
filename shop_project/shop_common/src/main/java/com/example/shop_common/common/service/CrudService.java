package com.example.shop_common.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;


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
	 */
	void delete(Serializable id);

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
    T findOneByWrapper(QueryWrapper<T> queryWrapper);

    /**
     * Find the by condition.
     *
     * @param queryWrapper the queryWrapper
     * @return the list
     */
    List<T> findByCondition(QueryWrapper<T> queryWrapper);

	/**
	 * Find Count by condition.
	 *
	 * @param queryWrapper the queryWrapper
	 * @return the list
	 */
	int selectCount(QueryWrapper<T> queryWrapper);

    /**
     * Delete by condition.
     *
     * @param queryWrapper the objects
     * @return the int
     */
    int deleteByCondition(QueryWrapper<T> queryWrapper);

    /**
     * Count by condition.
     *
     * @param queryWrapper the queryWrapper
     * @return the long
     */
	Integer countByCondition(QueryWrapper<T> queryWrapper);


	IPage<T> selectPage(IPage<T> page,QueryWrapper<T> wrapper);


}
