package my.ssm.o2o.dao.split;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**  
 * <p>在Dao层拦截</p>
 * <p>Date: 2019年2月8日</p>
 * @author Wanghui    
 */  
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MyBatisRequestInterceptor implements Interceptor {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        logger.debug("args={}", Arrays.toString(args));
        MappedStatement ms = (MappedStatement) args[0];
        //TODO 优化判断，使代码更加简洁。
        String lookupKey = DynamicDataSourceTypeHolder.DST_MASTER; //默认类型是master
        if(!TransactionSynchronizationManager.isActualTransactionActive()) { //非事务
            if(ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                //<selectKey>为自增主键获取主键值时（SELECT LAST_INSERT_ID() AS id）,使用master类型数据源。
                if(ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {//TODO 暂时还没碰到过这种情况
                    lookupKey = DynamicDataSourceTypeHolder.DST_MASTER;
                } else {
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(args[1]);
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                    if(sql.matches(REGEX)) {
                        //TODO 什么情况下在SqlCommandType.SELECT还包含更新语句？ <select>标签里好像可以写更新语句
                        lookupKey = DynamicDataSourceTypeHolder.DST_MASTER;
                    } else {
                        lookupKey = DynamicDataSourceTypeHolder.DST_SLAVE;
                    }
                }
            }
        }
        logger.debug("Dao方法[{}] use [{}] strategy, the sql command type is [{}]", 
                ms.getId(), lookupKey, ms.getSqlCommandType());
        DynamicDataSourceTypeHolder.setDataSourceType(lookupKey); //用于后面执行SQL时根据key选择数据源
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if(target instanceof Executor) { 
            logger.debug("由Plugin包装{}", target);
            return Plugin.wrap(target, this); //包装需要拦截的对象
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
