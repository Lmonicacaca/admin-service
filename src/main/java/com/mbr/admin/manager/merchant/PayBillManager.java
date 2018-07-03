package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.PayBill;
import com.mbr.admin.domain.merchant.Vo.PayBillVo;

import java.util.List;

public interface PayBillManager {
    public List<PayBillVo> queryAllPayBill(String merchantId, String fromAddr, String toAddr, int billType, String merchantnameSearch, int status);

}
