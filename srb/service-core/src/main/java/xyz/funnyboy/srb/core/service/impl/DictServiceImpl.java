package xyz.funnyboy.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import xyz.funnyboy.srb.core.listener.ExcelDictDTOListener;
import xyz.funnyboy.srb.core.mapper.DictMapper;
import xyz.funnyboy.srb.core.pojo.dto.ExcelDictDTO;
import xyz.funnyboy.srb.core.pojo.entity.Dict;
import xyz.funnyboy.srb.core.service.DictService;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
@Slf4j
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService
{

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 导入数据
     *
     * @param inputStream
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importData(InputStream inputStream) {
        // 这里需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel
                .read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(baseMapper))
                .sheet()
                .doRead();
        log.info("导入成功");
    }

    /**
     * 导出数据
     *
     * @return {@link List}<{@link ExcelDictDTO}>
     */
    @Override
    public void exportData(OutputStream outputStream) {
        // 转换为ExcelDTO数据
        final List<ExcelDictDTO> excelDictDTOList = baseMapper
                // 查询数据字典
                .selectList(null)
                .stream()
                // 转换为ExcelDTO数据
                .map(dict -> {
                    final ExcelDictDTO excelDictDTO = new ExcelDictDTO();
                    BeanUtils.copyProperties(dict, excelDictDTO);
                    return excelDictDTO;
                })
                .collect(Collectors.toList());

        // 导出数据
        EasyExcel
                .write(outputStream, ExcelDictDTO.class)
                .sheet("数据字典")
                .doWrite(excelDictDTOList);
        log.info("导出成功");
    }

    /**
     * 按父 ID 列出
     *
     * @param parentId 父 ID
     * @return {@link List}<{@link Dict}>
     */
    @Override
    public List<Dict> listByParentId(Long parentId) {
        // 先查询redis中是否存在数据列表
        List<Dict> dictList;
        try {
            dictList = (List<Dict>) redisTemplate
                    .opsForValue()
                    .get("srb:core:dictList:" + parentId);
            if (!CollectionUtils.isEmpty(dictList)) {
                log.info("从redis中获取数据");
                return dictList;
            }
        }
        catch (Exception e) {
            // 此处不抛出异常，继续执行后面的代码
            log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }

        log.info("从数据库中取值");
        dictList = baseMapper.selectList(new LambdaQueryWrapper<Dict>().eq(Dict::getParentId, parentId));
        dictList.forEach(dict -> {
            dict.setHasChildren(this.hashChildren(dict.getId()));
        });

        try {
            // 将数据存入redis
            redisTemplate
                    .opsForValue()
                    .set("srb:core:dictList:" + parentId, dictList);
            log.info("数据存入redis");
        }
        catch (Exception e) {
            // 此处不抛出异常，继续执行后面的代码
            log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }
        return dictList;
    }

    /**
     * 按字典代码查找
     *
     * @param dictCode 字典代码
     * @return {@link List}<{@link Dict}>
     */
    @Override
    public List<Dict> findByDictCode(String dictCode) {
        final Dict dict = baseMapper.selectOne(new LambdaQueryWrapper<Dict>().eq(Dict::getDictCode, dictCode));
        return this.listByParentId(dict.getId());
    }

    /**
     * 通过父级字典code和value获取字典名称
     *
     * @param dictCode 字典代码
     * @param value    值
     * @return {@link String}
     */
    @Override
    public String getNameByParentDictCodeAndValue(String dictCode, Integer value) {
        // 根据字典code获取父级字典
        final Dict parentDict = baseMapper.selectOne(new LambdaQueryWrapper<Dict>().eq(Dict::getDictCode, dictCode));
        if (parentDict == null) {
            return "";
        }

        // 根据父级id和value获取子级字典
        final Dict dict = baseMapper.selectOne(new LambdaQueryWrapper<Dict>()
                .eq(Dict::getParentId, parentDict.getId())
                .eq(Dict::getValue, value));

        // 返回子级字典名称
        return dict == null ?
               "" :
               dict.getName();
    }

    /**
     * 是否包含子节点
     *
     * @param parentId 编号
     * @return {@link Boolean}
     */
    private Boolean hashChildren(Long parentId) {
        return baseMapper.selectCount(new LambdaQueryWrapper<Dict>().eq(Dict::getParentId, parentId)) > 0;
    }
}
