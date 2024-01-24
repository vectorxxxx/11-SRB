package xyz.funnyboy.srb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.funnyboy.srb.core.pojo.dto.ExcelDictDTO;
import xyz.funnyboy.srb.core.pojo.entity.Dict;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface DictMapper extends BaseMapper<Dict>
{

    /**
     * 批处理插入
     *
     * @param list 列表
     */
    void insertBatch(List<ExcelDictDTO> list);
}
