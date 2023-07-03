package com.iflytek.medical.persistence.mbp;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collection;

/**
 * @author rende.hu
 * @since 2022-12-07 11:35:29
 */
public class BaseServiceImpl<M extends IBaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    private static final int BATCH_SIZE = 500;

    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    @Override
    public int insertBatch(Collection<T> list) {
        int repeat = list.size() / BATCH_SIZE;
        int count = 0;
        for (int i = 0; i < repeat; i++) {
            Collection<T> subList = CollectionUtil.sub(list, BATCH_SIZE * i, BATCH_SIZE * (i + 1));
            count += this.baseMapper.insertBatch(subList);
        }
        if (list.size() % BATCH_SIZE > 0) {
            Collection<T> subList = CollectionUtil.sub(list, BATCH_SIZE * repeat, list.size());
            count += this.baseMapper.insertBatch(subList);
        }
        return count;
    }

    /**
     * 是否重复
     * @param existId
     * @param idFunction
     * @param value
     * @param columnFunction
     * @param <E>
     * @param <D>
     * @return
     */
    @Override
    public <E, D> boolean hasRepeat(Long existId, SFunction<T, D> idFunction, E value, SFunction<T, E> columnFunction) {
        return this.baseMapper.selectCount(new LambdaQueryWrapper<T>().eq(columnFunction, value)
                .ne(existId != null, idFunction, existId)
        ) > 0;

    }
}
