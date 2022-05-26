package com.example.shop_common.common.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shop_common.common.mybatis.ExtBaseMapper;
import com.example.shop_common.entity.BaseEntity;
import com.example.shop_common.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * 基础的crud服务.
 *
 * @author TIM(JT)
 * @param <D> the generic type
 * @param <T> the generic type
 * @date 2017-08-02 18
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
}
