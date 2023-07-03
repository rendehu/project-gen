package com.iflytek.medical.persistence.mbp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author rende.hu
 * @since 2022-12-07 11:23:44
 */
public interface IBaseMapper<T> extends BaseMapper<T> {


    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") Collection<T> list);
}
