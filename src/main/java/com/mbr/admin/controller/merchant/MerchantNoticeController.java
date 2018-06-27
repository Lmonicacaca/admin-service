package com.mbr.admin.controller.merchant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("merchantNotice")
public class MerchantNoticeController {

    @RequestMapping("initPage")
    public String initPage(){
        return "merchant/merchantNoticeList";
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(String titleSearch){
        return null;
    }
}
