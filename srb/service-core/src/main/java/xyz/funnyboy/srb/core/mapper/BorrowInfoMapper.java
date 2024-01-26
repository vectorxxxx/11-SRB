package xyz.funnyboy.srb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.funnyboy.srb.core.pojo.entity.BorrowInfo;

import java.util.List;

/**
 * <p>
 * 借款信息表 Mapper 接口
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface BorrowInfoMapper extends BaseMapper<BorrowInfo>
{

    /**
     * 获取借款列表
     *
     * @return {@link List}<{@link BorrowInfo}>
     */
    List<BorrowInfo> selectBorrowInfoList();

}
