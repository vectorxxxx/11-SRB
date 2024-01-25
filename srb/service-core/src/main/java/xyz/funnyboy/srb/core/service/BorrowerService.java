package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.Borrower;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerApprovalVO;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerDetailVO;
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

    /**
     * 获取借款人分页列表
     *
     * @param pageParam 页面参数
     * @param keyword   关键词
     * @return {@link Page}<{@link Borrower}>
     */
    Page<Borrower> listPage(Page<Borrower> pageParam, String keyword);

    /**
     * 获取借款人信息
     *
     * @param id 编号
     * @return {@link BorrowerDetailVO}
     */
    BorrowerDetailVO getBorrowerDetailVOById(Long id);

    /**
     * 借款额度审批
     *
     * @param borrowerApprovalVO 借款人批准VO
     */
    void approval(BorrowerApprovalVO borrowerApprovalVO);
}
