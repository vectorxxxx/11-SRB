package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.Dict;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface DictService extends IService<Dict>
{
    /**
     * 导入数据
     *
     * @param inputStream
     */
    void importData(InputStream inputStream);

    /**
     * 导出数据
     */
    void exportData(OutputStream outputStream);

    /**
     * 按父 ID 列出
     *
     * @param parentId 父 ID
     * @return {@link List}<{@link Dict}>
     */
    List<Dict> listByParentId(Long parentId);

    /**
     * 按字典代码查找
     *
     * @param dictCode 字典代码
     * @return {@link List}<{@link Dict}>
     */
    List<Dict> findByDictCode(String dictCode);

    /**
     * 通过父级字典code和value获取字典名称
     *
     * @param dictCode 字典代码
     * @param value    值
     * @return {@link String}
     */
    String getNameByParentDictCodeAndValue(String dictCode, Integer value);
}
