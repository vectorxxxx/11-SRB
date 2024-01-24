package xyz.funnyboy.srb.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.funnyboy.srb.core.mapper.DictMapper;
import xyz.funnyboy.srb.core.pojo.entity.Dict;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis测试类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-23 22:51:09
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemplateTest
{
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private DictMapper dictMapper;

    @Test
    public void saveDict() {
        final Dict dict = dictMapper.selectById(1);
        // 向数据库中存储string类型的键值对, 过期时间5分钟
        redisTemplate
                .opsForValue()
                .set("dict", dict, 5, TimeUnit.MINUTES);
    }

    @Test
    public void getDict() {
        Dict dict = (Dict) redisTemplate
                .opsForValue()
                .get("dict");
        System.out.println(dict);
    }
}
