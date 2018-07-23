package com.mbr.admin.controller.merchant;

import com.alibaba.fastjson.JSONObject;
import com.mbr.admin.common.utils.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Controller
@RequestMapping("kindeditor")
public class KindEditorController {

    @Autowired
    private FileUpload fileUpload;

    @RequestMapping(value = "fileUpload")
    @ResponseBody
    public Object fileUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
        response.setHeader("X-Frame-Options", null);
        JSONObject obj = new JSONObject();
        try {
            Map<String, MultipartFile> fileMap = request.getFileMap();
            if (fileMap!=null) {
                String result = fileUpload.httpClientUploadFile(fileMap);
                obj.put("error", 0);
                obj.put("url", result);
            }
            PrintWriter out = response.getWriter();
            out.print(obj.toJSONString());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
