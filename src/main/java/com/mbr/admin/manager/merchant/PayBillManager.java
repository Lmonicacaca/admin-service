package com.mbr.admin.manager.merchant;

import com.mbr.admin.domain.merchant.PayBill;

import java.util.List;

public interface PayBillManager {
    public List<PayBill> queryAllPayBill(String merchantId,String fromAddr,String toAddr,int billType);

    public int deleteById(Long id);
}
