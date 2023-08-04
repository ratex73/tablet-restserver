package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"99. Report"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class GetBeanController {
    @Autowired
    private DefaultListableBeanFactory dfBean;

    @GetMapping("/funReport/getbean")
    public void getBean () {
        for(String name : dfBean.getBeanDefinitionNames()) {
            System.out.println(" INFO BEAN NAME ===  "+name);
        }
    }

}
