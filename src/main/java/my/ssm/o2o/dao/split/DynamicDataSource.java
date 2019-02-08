package my.ssm.o2o.dao.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**  
 * <p>内部包含多个数据源</p>
 * <p>Date: 2019年2月8日</p>
 * @author Wanghui    
 */  
public class DynamicDataSource extends AbstractRoutingDataSource {

    //模板方法determineCurrentLookupKey受AbstractRoutingDataSource.determineTargetDataSource方法的驱动
    //AbstractRoutingDataSource.determineTargetDataSource受AbstractRoutingDataSource.getConnection方法的驱动
    //等到执行SQL需要数据库连接的时候，会借助MyBatis拦截器事先准备好的key选择对应的数据源，然后从数据源里获取连接。
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceTypeHolder.getDataSourceType();
    }
    /* 实现多数据源需要以下三个关键类：
       org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
       org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
       org.apache.ibatis.plugin.Interceptor
     */
}
