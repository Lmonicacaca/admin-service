package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.EthTransaction;
import com.mbr.admin.domain.app.Vo.EthTransactionVo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface EthTransactionManager {

    public  Map<String,Object> queryList(String orderId, String from, String to,String status,Pageable page);
}
