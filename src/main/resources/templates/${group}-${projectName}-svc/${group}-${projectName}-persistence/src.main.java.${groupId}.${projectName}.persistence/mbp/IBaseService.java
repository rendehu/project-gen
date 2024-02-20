package ${groupId}.${projectName}.persistence.mbp;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;

/**
 * @author ${author}
 * @since 2022-12-07 11:34:15
 */
public interface IBaseService<T> extends IService<T> {

    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    int insertBatch(Collection<T> list);


    /**
     * @param existId
     * @param idFunction
     * @param value
     * @param columnFunction
     * @param <D>
     * @param <E>
     */
    <E, D> boolean hasRepeat(Long existId, SFunction<T, D> idFunction, E value, SFunction<T, E> columnFunction);



}
