package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.Borrower;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerVO;

/**
 * <p>
 * 借款人 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface BorrowerService extends IService<Borrower>
{

    /**
     * 保存借款人
     *
     * @param borrowerVO 借款人VO
     * @param userId     用户 ID
     */
    void saveBorrower(BorrowerVO borrowerVO, Long userId);

    /**
     * 获取借款人认证状态
     *
     * @param userId 用户 ID
     * @return {@link Integer}
     */
    Integer getBorrowerStatus(Long userId);
}
