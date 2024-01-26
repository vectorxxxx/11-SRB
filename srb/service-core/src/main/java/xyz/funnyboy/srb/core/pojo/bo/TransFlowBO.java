package xyz.funnyboy.srb.core.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.funnyboy.srb.core.enums.TransTypeEnum;

import java.math.BigDecimal;

/**
 * 交易流水BO
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransFlowBO
{

    private String agentBillNo;
    private String bindCode;
    private BigDecimal amount;
    private TransTypeEnum transTypeEnum;
    private String memo;
}
