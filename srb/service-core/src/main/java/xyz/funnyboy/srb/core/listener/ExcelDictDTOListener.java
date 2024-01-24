package xyz.funnyboy.srb.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import xyz.funnyboy.srb.core.mapper.DictMapper;
import xyz.funnyboy.srb.core.pojo.dto.ExcelDictDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典Excel监听器
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-23 21:01:52
 */
@Slf4j
@NoArgsConstructor
public class ExcelDictDTOListener extends AnalysisEventListener<ExcelDictDTO>
{
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    private List<ExcelDictDTO> list = new ArrayList();
    private DictMapper dictMapper;

    public ExcelDictDTOListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    /**
     * 遍历每一行的记录
     *
     * @param data            Excel 字典 DTO
     * @param analysisContext 上下文
     */
    @Override
    public void invoke(ExcelDictDTO data, AnalysisContext analysisContext) {
        log.info("解析到一条记录: {}", data);
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 保存数据
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        dictMapper.insertBatch(list);
        log.info("存储数据库成功！");
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param analysisContext 上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }
}
