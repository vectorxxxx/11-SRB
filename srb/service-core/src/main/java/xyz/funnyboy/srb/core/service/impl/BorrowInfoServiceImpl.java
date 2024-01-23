package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.BorrowInfoMapper;
import xyz.funnyboy.srb.core.pojo.entity.BorrowInfo;
import xyz.funnyboy.srb.core.service.BorrowInfoService;

/**
 * <p>
 * 借款信息表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class BorrowInfoServiceImpl extends ServiceImpl<BorrowInfoMapper, BorrowInfo> implements BorrowInfoService
{

}
