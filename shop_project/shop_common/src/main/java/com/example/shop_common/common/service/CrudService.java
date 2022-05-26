package com.example.shop_common.common.service;

import java.io.Serializable;
import java.util.List;

/**
 * 基础的crud接口.
 *
 * @author TIM(JT)
 * @param <T> the generic type
 * @date 2017-08-02 18
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
	 * @param idList
	 * @return
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
	 * 批量更新
	 * @param entityList
	 */
	void batchInsert(List<T> entityList);

    /**
     * 更新.
     *
     * @param entity the entity
     * @param includeColumns the include columns
     * @return the int
     */
    //  int updateSelective(T entity);
    int update(T entity, String... includeColumns);

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
     * @param objects the objects
     * @return the t
     */
    T findOneByCondition(Object... objects);

    /**
     * Find the by condition.
     *
     * @param objects the objects
     * @return the list
     */
    List<T> findByCondition(Object... objects);

    /**
     * Delete by condition.
     *
     * @param objects the objects
     * @return the int
     */
    int deleteByCondition(Object... objects);

    /**
     * Count by condition.
     *
     * @param objects the objects
     * @return the long
     */
    Long countByCondition(Object... objects);

    /**
     * Count by condition.
    *
    * @param objects the objects
    * @return the long
    */
    boolean existByCondition(Object... objects);
}
