package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.EthTransaction;
import com.mbr.admin.domain.app.Vo.EthTransactionVo;

import java.util.List;
import java.util.Map;

public interface EthTransactionManager {

    public List<EthTransactionVo> queryList(String orderId, String from, String to);
}
