package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.LendItemMapper;
import xyz.funnyboy.srb.core.pojo.entity.LendItem;
import xyz.funnyboy.srb.core.service.LendItemService;

/**
 * <p>
 * 标的出借记录表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class LendItemServiceImpl extends ServiceImpl<LendItemMapper, LendItem> implements LendItemService
{

}
