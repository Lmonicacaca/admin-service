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
        List<PayBill> payBillList = payDao.queryAllPayBill(merchantId, fromAddr, toAddr, billType, merchantnameSearch, status);
        List<PayBillVo> list = new ArrayList<>();
        for (int i=0;i<payBillList.size();i++){
            PayBillVo payBillVo = new PayBillVo();
            payBillVo.setId(payBillList.get(i).getId());
            payBillVo.setAmount(payBillList.get(i).getAmount());
            payBillVo.setAttach(payBillList.get(i).getAttach());
            payBillVo.setBillType(payBillList.get(i).getBillType());
            payBillVo.setChannel(payBillList.get(i).getChannel());
            payBillVo.setCoinId(payBillList.get(i).getCoinId());
            payBillVo.setCreateTime(payBillList.get(i).getCreateTime());
            payBillVo.setFromAddr(payBillList.get(i).getFromAddr());
            payBillVo.setGethMessage(payBillList.get(i).getGethMessage());
            payBillVo.setGoodsTag(payBillList.get(i).getGoodsTag());
            payBillVo.setGoodsType(payBillList.get(i).getGoodsType());
            payBillVo.setIndustry(payBillList.get(i).getIndustry());
            payBillVo.setLastUpdateTime(payBillList.get(i).getLastUpdateTime());
            payBillVo.setMerchantId(payBillList.get(i).getMerchantId());
            payBillVo.setMerchantName(payBillList.get(i).getMerchantName());
            payBillVo.setRefBizNo(payBillList.get(i).getRefBizNo());
            payBillVo.setSingedTx(payBillList.get(i).getSingedTx());
            payBillVo.setStatus(payBillList.get(i).getStatus());
            payBillVo.setToAddr(payBillList.get(i).getToAddr());
            payBillVo.setTokenAddr(payBillList.get(i).getTokenAddr());
            payBillVo.setTxData(payBillList.get(i).getTxData());
            payBillVo.setTxHash(payBillList.get(i).getTxHash());
            Product coin = productRepository.findById(payBillList.get(i).getCoinId());
            payBillVo.setCoinName(coin.getCoinName());
            list.add(payBillVo);
        }

        return list;
    }
}
