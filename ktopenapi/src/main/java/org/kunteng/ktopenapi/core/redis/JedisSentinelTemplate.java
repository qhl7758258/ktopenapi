package org.kunteng.ktopenapi.core.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.TimeoutUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis实现
 * @author hlqian
 *
 */
public class JedisSentinelTemplate {
    private static Logger logger = LoggerFactory.getLogger(JedisSentinelTemplate.class);
    private Pool<Jedis> jedisPool;

    public void setJedisPool(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }

    public <T> T execute(JedisAction<T> jedisAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = jedisPool.getResource();
            return jedisAction.action(jedis);
        } catch (JedisConnectionException e) {
            logger.error("connection error", e);
            broken = true;
            throw e;
        } finally {
            closeResource(jedis, broken);
        }
    }

    public void execute(JedisActionNoResult jedisAction) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = jedisPool.getResource();
            jedisAction.action(jedis);
        } catch (JedisConnectionException e) {
            logger.error("connection error", e);
            broken = true;
            throw e;
        } finally {
            closeResource(jedis, broken);
        }
    }

    protected void closeResource(Jedis jedis, boolean connectionBroken) {
        if (jedis != null) {
            try {
                if (connectionBroken) {
                    jedisPool.returnBrokenResource(jedis);
                } else {
                    jedisPool.returnResource(jedis);
                }
            } catch (Exception e) {
                logger.error("Error happen when return jedis to pool, try to close it directly.", e);
                closeJedis(jedis);
            }
        }
    }

    public static void closeJedis(Jedis jedis) {
        if ((jedis != null) && jedis.isConnected()) {
            try {
                try {
                    jedis.quit();
                } catch (Exception e) {
                }
                jedis.disconnect();
            } catch (Exception e) {
            }
        }
    }

    public String get(final String key) {
        return execute(new JedisAction<String>() {

            @Override
            public String action(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    public void set(final String key, final String value) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.set(key, value);
            }
        });
    }

    public void setex(final String key, final String value, final int seconds) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.setex(key, seconds, value);
            }
        });
    }

    public void setex(final String key, final String value, final long timeout, final TimeUnit unit) {
        Long seconds = TimeoutUtils.toSeconds(timeout, unit);
        setex(key, value, seconds.intValue());
    }

    /**
     * 如果key还不存在则进行设置，返回true，否则返回false.
     */
    public Boolean setnx(final String key, final String value) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.setnx(key, value) == 1 ? true : false;
            }
        });
    }

    public Boolean del(final String... keys) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.del(keys) == 1 ? true : false;
            }
        });
    }

    public String hget(final String key, final String field) {
        return execute(new JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.hget(key, field);
            }
        });
    }

    public void hset(final String key, final String field, final String value) {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {
                jedis.hset(key, field, value);
            }
        });
    }

    public void hmset(final String key, final Map<String, String> map) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.hmset(key, map);
            }
        });
    }

    public Map<String, String> hgetAll(final String key) {
        return execute(new JedisAction<Map<String, String>>() {
            @Override
            public Map<String, String> action(Jedis jedis) {
                return jedis.hgetAll(key);
            }
        });
    }

    public void lpush(final String key, final String... values) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.lpush(key, values);
            }
        });
    }

    public void rpush(final String key, final String... values) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.rpush(key, values);
            }
        });
    }

    public String rpop(final String key) {
        return execute(new JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.rpop(key);
            }
        });
    }

    public Long llen(final String key) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.llen(key);
            }
        });
    }

    public String ltrim(final String key, final Long start, final Long end) {
        return execute(new JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.ltrim(key, start, end);
            }
        });
    }

    public Boolean zadd(final String key, final String member, final double score) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.zadd(key, score, member) == 1 ? true : false;
            }
        });
    }

    public Boolean zrem(final String key, final String member) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.zrem(key, member) == 1 ? true : false;
            }
        });
    }

    public Boolean zrem(final String key, final String... member) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.zrem(key, member) == 1 ? true : false;
            }
        });
    }

    public Long zremrangeByRank(final String key, final Long start, final Long end) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.zremrangeByRank(key, start, end);
            }
        });
    }

    public Long zcard(final String key) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.zcard(key);
            }
        });
    }

    public Set<String> zrange(final String key, final Long start, final Long end) {
        return execute(new JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.zrange(key, start, end);
            }
        });
    }

    public interface JedisAction<T> {
        T action(Jedis jedis);
    }

    public interface JedisActionNoResult {
        void action(Jedis jedis);
    }
}
