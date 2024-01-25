package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.BorrowerAttach;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerAttachVO;

import java.util.List;

/**
 * <p>
 * 借款人上传资源表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface BorrowerAttachService extends IService<BorrowerAttach>
{

    /**
     * 根据ID查询借款人附件信息
     *
     * @param borrowerId 借款编号
     * @return {@link List}<{@link BorrowerAttachVO}>
     */
    List<BorrowerAttachVO> selectBorrowerAttachVOList(Long borrowerId);
}
