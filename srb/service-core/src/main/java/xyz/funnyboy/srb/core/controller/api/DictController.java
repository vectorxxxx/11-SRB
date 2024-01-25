package xyz.funnyboy.srb.core.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.common.result.R;
import xyz.funnyboy.srb.core.pojo.entity.Dict;
import xyz.funnyboy.srb.core.service.DictService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-25 14:09:17
 */
@Api(tags = "数据字典")
@RestController
@RequestMapping("/api/core/dict")
@Slf4j
public class DictController
{
    @Resource
    private DictService dictService;

    @ApiOperation("根据dictCode获取下级节点")
    @GetMapping("/findByDictCode/{dictCode}")
    public R findByDictCode(
            @ApiParam(value = "节点编码",
                      required = true)
            @PathVariable
                    String dictCode) {
        List<Dict> list = dictService.findByDictCode(dictCode);
        return R
                .ok()
                .data("dictList", list);
    }
}
