package kr.co.bnksys.pipelineTest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/view")
public class HomeController {

    @RequestMapping("/main")
    public String getMain() {
        return "test deployment!!!!\n 배포가 완료되었습니다\n";
    }
}
