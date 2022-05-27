package com.example.shop_common.common.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.shop_common.common.mybatis.ExtBaseMapper;
import com.example.shop_common.entity.BaseEntity;
import com.example.shop_common.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础的crud服务.
 */
public abstract class CrudServiceImpl<D extends ExtBaseMapper<T>, T extends BaseEntity> implements CrudService<T> {
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;

	@Override
	public T get(Serializable id) {
		return dao.selectById(id);
	}

	@Override
	public List<T> getList(List<Serializable> idList) {
		return dao.selectList(new QueryWrapper<T>().in("id",idList));
	}

    /**
     * Upsert.
     *
     * @param entity the entity
     * @return the int
     */
    @Override
    public int upsert(T entity) {
        boolean isNewRecord = DataUtil.isNull(entity.getId());
        return isNewRecord ? dao.insert(entity) : dao.updateById(entity);
    }

	@Override
	public int insert(T entity) {
		return dao.insert(entity);
	}
	
	@Override
	public void batchInsert(List<T> entityList) {
		dao.batchInsert(entityList);
	}

    @Override
    public int update(T entity) {
        return dao.updateById(entity);
    }


	@Override
	public int delete(String id) {
		return dao.deleteById(id);
	}


	/**
	 * Find the one by condition.
	 *
	 * @param queryWrapper the queryWrapper
	 * @return the t
	 */
	@Override
	public T findOneByCondition(Wrapper<T> queryWrapper){
		return dao.selectOne(queryWrapper);
	}

	/**
	 * Find the by condition.
	 *
	 * @param queryWrapper the queryWrapper
	 * @return the list
	 */
	@Override
	public List<T> findByCondition(Wrapper<T> queryWrapper){
		return dao.selectList(queryWrapper);
	}

	/**
	 * Delete by condition.
	 *
	 * @param queryWrapper the objects
	 * @return the int
	 */
	@Override
	public int deleteByCondition(Wrapper<T> queryWrapper){
		return dao.delete(queryWrapper);
	}

	/**
	 * Count by condition.
	 *
	 * @param queryWrapper the queryWrapper
	 * @return the long
	 */
	@Override
	public Integer countByCondition(Wrapper<T> queryWrapper){
		return dao.selectCount(queryWrapper);
	}

	/**
	 * selectPage
	 */
	@Override
	public IPage<T> selectPage(IPage<T> page, Wrapper<T> wrapper){
		return dao.selectPage(page,wrapper);
	}


	/**
	 * selectPage
	 */
	@Override
	public IPage<Map<String, Object>> selectMapsPage(IPage<T> page, Wrapper<T> wrapper){
		return dao.selectMapsPage(page,wrapper);
	}


}
