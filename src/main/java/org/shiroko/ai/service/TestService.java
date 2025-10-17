package org.shiroko.ai.service;

import org.shiroko.ai.entity.dto.TestReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;

public interface TestService {

    BaseRespVO<Object> test();

    BaseRespVO<Object> testParam(TestReqDTO reqDTO);

}
