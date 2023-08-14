package kr.co.yedaham.tablet.restserver.restserver.controller.v1;

import com.ubireport.common.util.StrUtil;
import com.ubireport.eform.UbiEformData;
import com.ubireport.viewer.report.preview.UbiViewer;
import io.swagger.annotations.Api;
import kr.co.yedaham.tablet.restserver.restserver.model.comm.CoComSrch2Request;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
    public String form(Model model, String functrlno, String josonYn, String fileNm) {

        Random random = new Random();

        System.out.println("functrlno = " + functrlno);
        System.out.println("josonYn = " + josonYn);

        String key = System.currentTimeMillis() + "_" + random.nextLong();
        //String file = "FSFU1007_3_1_RENEWAL_T.jrf";
        String file = "FSFU1007_3_1_RENEWAL_T_eform.jef";

        //조손가정
        if("Y".equals(josonYn)) {
            file = "FSFU1007_3_3_T.jrf";
        }

        if("E".equals(josonYn)) {
            file = "FSFU1007_3_1_RENEWAL_T_eform.jef";
        }

        if("".equals(functrlno)) {
            functrlno = "2022010063";
        }

        //String arg = "user#ubireport#";
        String arg = "functrl_no#" + functrlno + "#file_nm#" + fileNm;
        //arg = StrUtil.encrypt64(arg,"UTF-8");

        String resid = "UBIHTML";

        model.addAttribute("key", key);
        model.addAttribute("file", file);
        model.addAttribute("arg", arg);
        model.addAttribute("resid", resid);

        return "ubihtml";
    }

    @PostMapping("/ubisave")
    public void ubiSave(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));

        boolean isSuccess = false;
        boolean useLog = true;
        String NL = System.getProperty("line.separator");
        String TODAY = (new SimpleDateFormat("yyyyMMdd")).format(System.currentTimeMillis());

        //웹어플리케이션명
        String appName = StrUtil.nvl(request.getContextPath(), "");
        if( appName.indexOf("/") == 0 ) {
            appName = appName.substring(1, appName.length());
        }

        //웹어플리케이션 Root URL, ex)http://localhost:8080/myapp
        //String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + ((appName.length() == 0)?"":"/") + appName;
        //String serverUrl = "http://localhost:8080/UbiServer";
        String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/UbiServer";

        @SuppressWarnings("deprecation")
        String appPath = request.getRealPath("/");
        appPath = appPath.replaceAll("\\\\", "/");

        if( appPath.lastIndexOf("/") == (appPath.length() - 1) ) {
            appPath = appPath.substring(0, appPath.lastIndexOf("/"));
        }

        String savePath = "E:/uploads/" + TODAY; //pdf가 저장될 경로를 작성


        /* 설정 정보 */
        StringBuffer log = new StringBuffer();
        log.append("---------------------------------------------").append(NL);
        log.append("[ Setting Info]").append(NL);
        log.append("---------------------------------------------").append(NL);
        //log.append("* App URL : " + appUrl).append(NL);
        log.append("* UbiServer URL : " + serverUrl).append(NL);
        log.append("* App Path : " + appPath).append(NL);
        log.append("* Save Path : " + savePath).append(NL);


        if( useLog ) {
            System.out.println(log.toString());
        }

        log.delete(0, log.length());

        UbiEformData ubiEformData = null;
        try {

            ubiEformData = new UbiEformData(request, response);

            File fSavePath = new File(savePath);
            if( !fSavePath.exists() ) {
                fSavePath.mkdirs();
            }

            int i = 0;
            /* 아규먼트 정보 : ubiencrypt=true인 경우 아규먼트 정보처리가 안됨. 수정 필요*/
            log.append("---------------------------------------------").append(NL);
            log.append("[ Argument Info]").append(NL);
            log.append("---------------------------------------------").append(NL);
            String[] argNames = ubiEformData.getArgumentNames();
            String reporttitle = "";
            String funCtrlNo = "";
            String fileNm = "";

            if (argNames != null) {
                for (i = 0; i < argNames.length; i++) {
                    log.append(argNames[i] + " = " + ubiEformData.getArgument(argNames[i])).append(NL);

                    if(argNames[i].equals("reporttitle")) {
                        reporttitle = ubiEformData.getArgument(argNames[i]);
                    }

                    if(argNames[i].equals("functrl_no")) {
                        funCtrlNo = ubiEformData.getArgument(argNames[i]);
                    }

                    if(argNames[i].equals("file_nm")) {
                        fileNm = ubiEformData.getArgument(argNames[i]);
                    }
                }
            }
            log.append(NL);

            String saveFileName = "";
            if(!"".equals(fileNm)) {
                //saveFileName = "funCtrlNo_" + System.currentTimeMillis() + ".pdf";
                saveFileName = fileNm + ".pdf";
            }
            else {
                saveFileName = "funCtrlNo_" + System.currentTimeMillis() + ".pdf";
                //saveFileName = reporttitle + ".pdf";
            }
            String saveFullPath = savePath + "/" + saveFileName;

            /* 사용자 입력 정보 */
            log.append("---------------------------------------------").append(NL);
            log.append("[ Input Info ]").append(NL);
            log.append("---------------------------------------------").append(NL);
            String[] columnNames = ubiEformData.getParameterNames();

            if (columnNames != null) {
                for (i = 0; i < columnNames.length; i++) {
                    log.append(columnNames[i] + " = " + ubiEformData.getParameter(columnNames[i])).append(NL);
                }
            }

            if( useLog ) {
                System.out.println(log.toString());
            }

            log.delete(0, log.length());

            // 파일 생성
            boolean saveResult = ubiEformData.saveFile(UbiEformData.PDF, saveFullPath);

            /* 파일 저장 결과 정보 */
            log.append("---------------------------------------------").append(NL);
            log.append("* Save File Name : " + saveFileName).append(NL);
            log.append("* Save Full Path : " + saveFullPath).append(NL);
            log.append("[ File Save Result : " + (saveResult?"Success!":"Fail!") + " ]").append(NL);
            log.append("---------------------------------------------").append(NL);

            System.out.println(log.toString());
            log.delete(0, log.length());


            if( saveResult ) {
                ubiEformData.sendSuccess(out, "FILENAME#" + saveFullPath);
                isSuccess = true;
            }
            else {
                ubiEformData.sendError(out, "파일 저장 오류");
            }
            out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                if (ubiEformData != null) {
                    ubiEformData.sendError(out, e.getMessage());
                }
            } catch (Exception ex) {}
        }

    }

}
