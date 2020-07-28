<#if comType = '1'>
package ${basePackage?substring(0, (basePackage)?last_index_of("."))}.commapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T>, InsertUseTimeMapper<T>, UpdateUseTimeMapper<T> {

}
</#if>