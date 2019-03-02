package my.ssm.o2o.dao.cache;

import java.util.Set;

/**  
 * <p>缓存操作接口</p>
 * <p>Date: 2019年3月1日</p>
 * @author Wanghui    
 */  
public abstract class CacheDao {
    /**  
     * <p>判断指定键是否存在</p>  
     * @param key 键
     * @return  true 存在，false 不存在
     */  
    public abstract boolean exists(String key);
    /**  
     * <p>获取指定前缀的所有键</p>  
     * @param keyPrefix 前缀
     * @return  键集合
     */  
    public abstract Set<String> keys(String keyPrefix);
    /**  
     * <p>获取指定键的值</p>  
     * @param key 键
     * @return  值
     */  
    public abstract String val(String key);
    /**  
     * <p>删除指定键值</p>  
     * @param key 键
     * @return  删除的键值条数
     */  
    public abstract Long del(String key);
    /**  
     * <p>批量删除指定键值</p>  
     * @param key 若干键
     * @return  删除的键值条数
     */  
    public abstract Long del(String... key);
    /**  
     * <p>存储键值</p>  
     * @param key 键
     * @param val 值
     * @return  执行结果，例如OK  
     */  
    public abstract String set(String key, String val);
    /**  
     * <p>存储键值并设定过期时间</p>  
     * @param key 键
     * @param seconds 过期时间
     * @param val 值
     * @return  执行结果，例如OK
     */  
    public abstract String set(String key, int seconds, String val);
}
