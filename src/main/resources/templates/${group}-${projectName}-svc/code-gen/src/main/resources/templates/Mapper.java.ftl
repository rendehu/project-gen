<#noparse>package ${package.Mapper};

import ${package.Entity}.${entity};
import ${package.Parent}.mbp.IBaseMapper;
<#if mapperAnnotation>
    import org.apache.ibatis.annotations.Mapper;
</#if>

/**
* <p>
    * ${table.comment!} Mapper 接口
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if mapperAnnotation>
@Mapper
</#if>
<#if kotlin>
interface ${table.mapperName} : IBaseMapper<${entity}>
<#else>
public interface ${table.mapperName} extends IBaseMapper<${entity}> {

}
</#if></#noparse>
