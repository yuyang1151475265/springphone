import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author： CatalpaFlat
 * @Descrition:
 * @Date: Create in 10:08 2017/11/8
 * @Modified BY：
 */
@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath:spring-mvc.xml","classpath:applicationContext.xml","classpath:spring-mybatis.xml","classpath:spring-redis.xml"})
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Logger log  = Logger.getLogger(TestRedis.class.getName());

    @Test
    public void test(){
        redisTemplate.opsForValue().set("chen", "陈梓平");
        log.info("value："+redisTemplate.opsForValue().get("chen"));
    }
}