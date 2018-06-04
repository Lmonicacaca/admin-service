package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.EthTransaction;

import java.util.List;
import java.util.Map;

public interface EthTransactionManager {

    public List<Map<String,Object>> queryList(String orderId, String from, String to);
}
