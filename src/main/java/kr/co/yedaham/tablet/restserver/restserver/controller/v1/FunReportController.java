package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import com.ubireport.common.util.StrUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Api(tags = {"99. Report"})
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/")
public class FunReportController {
/*
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
*/
    @RequestMapping(value = "/funReport")
    public String form(Model model, String functrlno, String josonYn) {

        Random random = new Random();

        System.out.println("functrlno = " + functrlno);
        System.out.println("josonYn = " + josonYn);

        String key = System.currentTimeMillis() + "_" + random.nextLong();
        String file = "FSFU1007_3_1_RENEWAL_T.jrf";

        //조손가정
        if("Y".equals(josonYn)) {
            file = "FSFU1007_3_3_T.jrf";
        }

        if("".equals(functrlno)) {
            functrlno = "2022010063";
        }

        //String arg = "user#ubireport#";
        String arg = "functrl_no#" + functrlno + "#";
        arg = StrUtil.encrypt64(arg,"UTF-8");

        String resid = "UBIHTML";

        model.addAttribute("key", key);
        model.addAttribute("file", file);
        model.addAttribute("arg", arg);
        model.addAttribute("resid", resid);

        return "ubihtml";
    }

}
