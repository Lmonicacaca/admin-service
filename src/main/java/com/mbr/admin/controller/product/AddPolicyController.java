package com.mbr.admin.controller.product;

import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.domain.app.PrivacyPolicyAndAbout;
import com.mbr.admin.manager.app.AddPolicyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("addPolicy")
public class AddPolicyController extends BaseController {

    @Autowired
    private AddPolicyManager addPolicyManager;

    @RequestMapping("/initPage")
    public String initPage(){
        return "product/addPolicyList";
    }

    @RequestMapping("/queryChannel")
    @ResponseBody
    public Object queryChannel(){
        return addPolicyManager.queryChannel();
    }

    @RequestMapping("/addOrUpdate")
    @ResponseBody
    public Object addOrUpdate(PrivacyPolicyAndAbout privacyPolicyAndAbout){
        int i = addPolicyManager.addPolicy(privacyPolicyAndAbout);
        if(i>0){
            return success();
        }
        return null;
    }
}
