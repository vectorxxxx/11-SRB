package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.BorrowerMapper;
import xyz.funnyboy.srb.core.pojo.entity.Borrower;
import xyz.funnyboy.srb.core.service.BorrowerService;

/**
 * <p>
 * 借款人 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class BorrowerServiceImpl extends ServiceImpl<BorrowerMapper, Borrower> implements BorrowerService
{

}
