package xyz.funnyboy.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.srb.core.pojo.entity.UserBind;
import xyz.funnyboy.srb.core.pojo.vo.UserBindVO;

import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-22
 */
public interface UserBindService extends IService<UserBind>
{

    /**
     * 账户绑定提交到托管平台的数据
     *
     * @param userBindVO 用户绑定 VO
     * @param userId     用户 ID
     * @return {@link String}
     */
    String commitBindUser(UserBindVO userBindVO, Long userId);

    /**
     * 通知
     *
     * @param paramMap 参数映射
     */
    void notify(Map<String, Object> paramMap);
}
