package kr.co.bnksys.pipelineTest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/view")
public class HomeController {

    @RequestMapping("/main")
    public String getMain() {
        return "2번째 배포 완료";
    }
}
