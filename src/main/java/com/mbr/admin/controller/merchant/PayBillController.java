package com.mbr.admin.controller.merchant;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbr.admin.common.controller.BaseController;
import com.mbr.admin.common.dto.PageResultDto;
import com.mbr.admin.domain.merchant.PayBill;
import com.mbr.admin.domain.merchant.Vo.PayBillVo;
import com.mbr.admin.manager.merchant.PayBillManager;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("payBill")
public class PayBillController extends BaseController {

    @Autowired
    private PayBillManager payManager;
    @RequestMapping("initPage")
    public String initPage(){
        return "merchant/paybillList";
    }

    @RequestMapping("queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request,String merchantidSearch, String refBizNoSearch,String billTypeSearch,String merchantnameSearch,String statusSearch){
        System.out.println("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpRQRz9cwhgs0B511/qCU4eQXLDyL4TxJ2SGEcw3DK9EDoFQkxFgDmFQAwMa2jIKl/el0rom3tAQObQIG2UaNceV0y6h65UGr5rGYWCsUDTa0oc7ZIH2spceKJqneALXEYFedTvbIcuck+jguRp8jXBZyzJVLbcuoYAtm9cFYmKY1vNSMZlQdEKGFKNKcSKTG1Ee0ViL36hUNQNmPxM+azY86IrwZ/j6nkgSDK5tjO8o9LzZAxqMTKe+37zNE6VaIEEZQrEigzTahtVt//PbuMV5x54Y697JZ+LWMWt4ciYgYg3JnOh++KFsOYY48VEwL48XIy8sLKD3uRfEcRN7VXAgMBAAECggEAMvgbTSeYXgba4FXgq43gF08p1WkraIW203Mb+uNG1XYTDU84Q1WS07VDJz/uPw6Yu1AHOhpg61rA0UDtTg7rF+9bhvdEZRy3+ZJV8xEvHb2IBIRDQRhk1kf6pOV1slz8jvoZe/fE6C1xUarIBQX2zfhcpPz6JCZIIw7IrehjqH2f533V+guSm+iCaIhT6vUD5EfNpuam0E8GGUIPkdVfkRtykQa76yu1wpv2yFrBsrRsc8y9kGumJ1VFdaOVVX9d6LAIkipL3YNm1HvPBnktj64oM90YA6m3A/bFBOFqIrMwUBT71UIDmtM1grBnDzs3Banuvq2QPZUv4BI3g8AgIQKBgQDxEXQaKadM1pfj6JKW6kJBIz40wcqD+MionM3WP56GGuFn29Vq0H0vHwj2f/rTIklcFVYuoWYDOYIrMniRTt1I2WhwTj98Pcpb5asrkDdEaVlYQq7+R1bXJXUQKA9/JP8ppOqfg7HP/UCjuH3h/ak+y0ZKJGug1CQhds9ECZ1X6QKBgQCzwRM8XKTxnrCbDIy0WHWMe5HzPT6oi9gtulVr1jSpcXmO26NYQP34jIYHG2WFscVB8lhVRsTCaOXgbgpzBH99q6+wF9YzKUhmMO2V8c/MdXnsFHdYpUN+JpjQWvIKEFxcouXcyiaenkLN1m2oUoMwb46CABkxBRQlB7wXXv2bPwKBgBxNHkJMDZYZw29ASKVrDygyiQUMk0f3FyekcQ3sHiJEWZ4l0uJdY7T6gcTetYXACrjC0IFc9Wr/f2au4DS++3+n9njo1s8xOeacCgJtRe/EJncULRMxMOLFRP8GlPsqTsKG1/yuK1vtsX8HE9BKRWpX1wKxT+lrvmonVqH4Nv6xAoGAXTYBg4uG/MQNUFlxnRNB4Vcyl69qjnv13cCCCylIpZTyM+IxEdKh4AD+fzD1tB4667d/lrjbzvQWQArP4FS0x7X/pJC3wk/l+xfkG50I5D0GvCTgvlb0aLYbB/AhEpbpTiAqkhNBc38dpR9MPbyLytIOU9s5NPItQAaCwpu/ZoECgYEA7VVvx5Fn6O13gfl4MTLPBuG8jmOreak69eVcvYJeQ6ZP5BNx54mshr6KF+ck+3XBVQiD3IjV6V8lVBeH2OFUQ5+dDJWqdC+H8Y6X6loUa0V/q7vHwYNA11S3HeF/77Ybnq0zd7Cdk2J4ACV6IX7ChMglsvCdUMk5OgYUBaLjD2Y=".replace("+","%2B"));
        System.out.println("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgUtjUwQCpo5c49BHhU+k+DU5XYA5Ww9+Jeql9J4IzvHoW1ChX2tDiBPwB+pJbrUE9EEw+jEuj9QQIKyQwBbYpyoNh0uJMsGJJRcuIJYEsznw4wzvU/q9U0OcYJfQ9Qu5xQ3dH2yk1CS4v2Ai1v+wngZ1t4hcvKW2Ccbyh4SGxVQHhOCC4JchFHgmsRsytjIzZHxOGMvzhRy2fjHcYMyGNpgqHBMHx4sTtIdrKZ52MQ+A/Vjj3iznXbLNRxz5PtFO/c2iX8l6FbYj6iMXmcUZlUv08g7H1+hLObVHbDBLML/DkwQwpxz26f9S6jgR7XBBD31+tc5Vlede7cYWDLmx9QIDAQAB".replace("+","%2B"));
        int billType = Integer.parseInt(billTypeSearch);
        int status = Integer.parseInt(statusSearch);
        PageHelper.startPage(super.getPageNo(request), super.getPageSize(request));
        List<PayBillVo> payBillList = payManager.queryAllPayBill(merchantidSearch,refBizNoSearch,billType,merchantnameSearch,status);
        PageResultDto result = new PageResultDto<PayBillVo>(new PageInfo<PayBillVo>(payBillList));
        return result;
    }



}
