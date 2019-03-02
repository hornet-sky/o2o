package my.ssm.o2o.dao.cache;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**  
 * <p>缓存操作接口的Redis实现</p>
 * <p>Date: 2019年3月2日</p>
 * @author Wanghui    
 */  
@Repository
public class RedisDao extends CacheDao {
    @Autowired
    private JedisPool jedisPool;
    @Override
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Set<String> keys(String keyPrefix) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.keys(keyPrefix + "*"); 
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public String val(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }
    
    @Override
    public Long del(String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(keys);
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public String set(String key, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, val);
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public String set(String key, int seconds, String val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setex(key, seconds, val);
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }
}
