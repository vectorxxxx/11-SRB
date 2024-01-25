package xyz.funnyboy.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.core.mapper.BorrowerAttachMapper;
import xyz.funnyboy.srb.core.pojo.entity.BorrowerAttach;
import xyz.funnyboy.srb.core.pojo.vo.BorrowerAttachVO;
import xyz.funnyboy.srb.core.service.BorrowerAttachService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 借款人上传资源表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Service
public class BorrowerAttachServiceImpl extends ServiceImpl<BorrowerAttachMapper, BorrowerAttach> implements BorrowerAttachService
{

    /**
     * 根据ID查询借款人附件信息
     *
     * @param borrowerId 借款编号
     * @return {@link List}<{@link BorrowerAttachVO}>
     */
    @Override
    public List<BorrowerAttachVO> selectBorrowerAttachVOList(Long borrowerId) {
        return baseMapper
                .selectList(new LambdaQueryWrapper<BorrowerAttach>().eq(BorrowerAttach::getBorrowerId, borrowerId))
                .stream()
                .map(attach -> {
                    final BorrowerAttachVO borrowerAttachVO = new BorrowerAttachVO();
                    BeanUtils.copyProperties(attach, borrowerAttachVO);
                    return borrowerAttachVO;
                })
                .collect(Collectors.toList());
    }
}
