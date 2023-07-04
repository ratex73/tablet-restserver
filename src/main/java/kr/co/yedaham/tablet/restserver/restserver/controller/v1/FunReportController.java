package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = {"99. Report"})
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/v1")
public class FunReportController {


    @RequestMapping(value = "/funReport")
    public ModelAndView viewFunReport(Model model) {

        System.out.println("=================start================");
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("serverName", "Multipart Server!!!");

        ModelAndView mav = new ModelAndView();

        mav.setViewName("funReport"); //jsp(html)로 갈때는 setViewName // class로 갈때는 setView

        System.out.println("==================end===============");
        return mav;
    }

}
