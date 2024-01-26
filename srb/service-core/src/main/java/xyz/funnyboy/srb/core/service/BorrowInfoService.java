package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.BorrowInfo;

import java.math.BigDecimal;

/**
 * <p>
 * 借款信息表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface BorrowInfoService extends IService<BorrowInfo>
{

    /**
     * 获取借款金额
     *
     * @param userId 用户 ID
     * @return {@link BigDecimal}
     */
    BigDecimal getBorrowAmount(Long userId);

    /**
     * 提交借款申请
     *
     * @param borrowInfo 借款信息
     * @param userId     用户 ID
     */
    void saveBorrowInfo(BorrowInfo borrowInfo, Long userId);

    /**
     * 获取借款申请审批状态
     *
     * @param userId 用户 ID
     * @return {@link Integer}
     */
    Integer getBorrowInfoStatus(Long userId);
}
