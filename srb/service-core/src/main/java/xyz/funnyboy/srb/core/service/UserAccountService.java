package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.UserAccount;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 用户账户 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface UserAccountService extends IService<UserAccount>
{

    /**
     * 充值
     *
     * @param chargeAmt 充电 AMT
     * @param userId    用户 ID
     * @return {@link String}
     */
    String commitCharge(Long chargeAmt, Long userId);

    /**
     * 用户充值异步回调
     *
     * @param paramMap 参数映射
     */
    void notify(Map<String, Object> paramMap);

    /**
     * 查询账户余额
     *
     * @param userId 用户 ID
     * @return {@link BigDecimal}
     */
    BigDecimal getAccountAmount(Long userId);

    /**
     * 用户提现
     *
     * @param fetchAmt 获取 AMT
     * @param userId   用户 ID
     * @return {@link String}
     */
    String commitWithdraw(Long fetchAmt, Long userId);

    /**
     * 用户提现异步回调
     *
     * @param paramMap 参数映射
     */
    void notifyWithdraw(Map<String, Object> paramMap);
}
