package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.dao.merchant.PayBillDao;
import com.mbr.admin.domain.merchant.PayBill;
import com.mbr.admin.manager.merchant.PayBillManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PayBillManageImpl implements PayBillManager {

    @Resource
    private PayBillDao payDao;
    @Override
    public List<PayBill> queryAllPayBill(String merchantId, String fromAddr, String toAddr,int billType) {
        return payDao.queryAllPayBill(merchantId,fromAddr,toAddr,billType);
    }

    @Override
    public int deleteById(Long id) {
        return payDao.deleteByPrimaryKey(id);
    }
}
