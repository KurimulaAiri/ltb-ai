package org.shiroko.ai.controller;

import org.shiroko.ai.entity.dto.TestReqDTO;
import org.shiroko.ai.entity.vo.BaseRespVO;
import org.shiroko.ai.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @Autowired
    private TestController (TestService testService) {
        this.testService = testService;
    }

    @RequestMapping("/test")
    public BaseRespVO<Object> test() {
        return testService.test();
    }

    @RequestMapping("/testParam")
    public BaseRespVO<Object> testParam(@RequestBody TestReqDTO param) {
        return testService.testParam(param);
    }

}
