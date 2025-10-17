package org.shiroko.ai.service.impl;

import org.shiroko.ai.entity.dto.TestReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private BaseRespVO<Object> resp;

    private TestServiceImpl() {

    }

    @Override
    public BaseRespVO<Object> test() {
        return BaseRespVO.succeed("测试成功");
    }

    @Override
    public BaseRespVO<Object> testParam(TestReqDTO reqDTO) {
        return BaseRespVO.succeed("参数测试成功", reqDTO);
    }
}
