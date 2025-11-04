package org.shiroko.ai.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageConvertUtils {

    /**
     * DO分页转换为VO分页
     * @param doPage DO分页对象
     * @param convertFunc DO转VO的函数（如 userDO -> userVO）
     * @param <DO> 数据对象类型
     * @param <VO> 视图对象类型
     * @return VO分页对象
     */
    public static <DO, VO> IPage<VO> convert(IPage<DO> doPage, Function<DO, VO> convertFunc) {
        if (doPage == null) {
            return new Page<>();
        }
        // 转换 records 列表
        List<VO> voList = doPage.getRecords().stream()
                .map(convertFunc)
                .collect(Collectors.toList());
        // 构建 VO 分页并设置元数据
        IPage<VO> voPage = new Page<>();
        voPage.setTotal(doPage.getTotal());
        voPage.setPages(doPage.getPages());
        voPage.setCurrent(doPage.getCurrent());
        voPage.setSize(doPage.getSize());
        voPage.setRecords(voList);
        return voPage;
    }
}