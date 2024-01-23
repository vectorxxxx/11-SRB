package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.DictMapper;
import xyz.funnyboy.srb.core.pojo.entity.Dict;
import xyz.funnyboy.srb.core.service.DictService;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService
{

}
