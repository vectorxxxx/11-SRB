package xyz.funnyboy.srb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.funnyboy.srb.core.pojo.entity.UserAccount;

import java.math.BigDecimal;

/**
 * <p>
 * 用户账户 Mapper 接口
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface UserAccountMapper extends BaseMapper<UserAccount>
{

    /**
     * 更新帐户
     *
     * @param bindCode     绑定代码
     * @param amount       量
     * @param freezeAmount 冻结金额
     */
    void updateAccount(
            @Param("bindCode")
                    String bindCode,
            @Param("amount")
                    BigDecimal amount,
            @Param("freezeAmount")
                    BigDecimal freezeAmount);
}
