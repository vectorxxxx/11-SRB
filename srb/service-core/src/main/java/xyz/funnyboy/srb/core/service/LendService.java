package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.BorrowInfo;
import xyz.funnyboy.srb.core.pojo.entity.Lend;
import xyz.funnyboy.srb.core.pojo.vo.BorrowInfoApprovalVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的准备表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface LendService extends IService<Lend>
{

    /**
     * 创建标的
     *
     * @param borrowInfoApprovalVO 借款信息审批VO
     * @param borrowInfo           借款信息
     */
    void createLend(BorrowInfoApprovalVO borrowInfoApprovalVO, BorrowInfo borrowInfo);

    /**
     * 查询标的列表
     *
     * @return {@link List}<{@link Lend}>
     */
    List<Lend> selectList();

    /**
     * 获取标的详情
     *
     * @param id 编号
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getLendDetail(Long id);
}
