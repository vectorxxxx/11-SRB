package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.LendItem;
import xyz.funnyboy.srb.core.pojo.vo.InvestVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借记录表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface LendItemService extends IService<LendItem>
{

    /**
     * 提交投资
     *
     * @param investVO 投资VO
     * @return {@link String}
     */
    String commitInvest(InvestVO investVO);

    /**
     * 会员投资异步回调
     *
     * @param paramMap 参数映射
     */
    void notify(Map<String, Object> paramMap);

    /**
     * 获取投资列表信息
     *
     * @param lendId 标的 ID
     * @param status 状态
     * @return {@link List}<{@link LendItem}>
     */
    List<LendItem> selectByLendId(Long lendId, Integer status);

    /**
     * 获取投资列表信息
     *
     * @param lendId 借出 ID
     * @return {@link List}<{@link LendItem}>
     */
    List<LendItem> selectByLendId(Long lendId);
}
