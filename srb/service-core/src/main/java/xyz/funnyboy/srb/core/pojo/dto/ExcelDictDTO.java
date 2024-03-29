package xyz.funnyboy.srb.core.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 数据字典Excel实体类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-23 21:01:01
 */
@Data
public class ExcelDictDTO
{

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("上级id")
    private Long parentId;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("值")
    private Integer value;

    @ExcelProperty("编码")
    private String dictCode;
}
