package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"99. Report"})
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/v1")
public class FunReportController {


    @RequestMapping(value = "/funReport")
    public ModelAndView viewFunReport(Model model) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("funReport");

        Map<String, Object> map = new HashMap<>();
        map.put("name", "Bamdule");
        map.put("date", LocalDateTime.now());

        modelAndView.addObject("data", map);

        return modelAndView;
    }

}
