package com.mbr.admin.manager.merchant.impl;

import com.mbr.admin.dao.merchant.MerchantHelpDao;
import com.mbr.admin.domain.merchant.Vo.MerchantHelpVo;
import com.mbr.admin.manager.merchant.MerchantHelpManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class MerchantHelpManagerImpl implements MerchantHelpManager {

    @Resource
    private MerchantHelpDao merchantHelpDao;

    @Override
    public List<MerchantHelpVo> queryList(String title) {
        return merchantHelpDao.queryList(title);
    }

    @Override
    public String queryContent(Integer id) {
        return merchantHelpDao.queryContent(id);
    }

    @Override
    public int deleteMerchantHelp(Integer id) {
        return merchantHelpDao.deleteMerchantHelp(id);
    }
}
