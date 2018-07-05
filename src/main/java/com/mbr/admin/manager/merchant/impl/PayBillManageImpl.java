package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.dao.merchant.PayBillDao;
import com.mbr.admin.domain.merchant.PayBill;
import com.mbr.admin.domain.merchant.Product;
import com.mbr.admin.domain.merchant.Vo.PayBillVo;
import com.mbr.admin.manager.merchant.PayBillManager;
import com.mbr.admin.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayBillManageImpl implements PayBillManager {

    @Resource
    private PayBillDao payDao;
    @Resource
    private ProductRepository productRepository;
    @Override
    public List<PayBillVo> queryAllPayBill(String merchantId, String fromAddr, String toAddr,int billType,String merchantnameSearch,int status) {
        List<PayBillVo> payBillList = payDao.queryAllPayBill(merchantId, fromAddr, toAddr, billType, merchantnameSearch, status);
        for (int i=0;i<payBillList.size();i++){
            if(payBillList.get(i).getCoinId()!=null&&payBillList.get(i).getCoinId()!=0){
                Product coin = productRepository.findById(payBillList.get(i).getCoinId());
                payBillList.get(i).setCoinName(coin.getCoinName());
            }

        }

        return payBillList;
    }
}
