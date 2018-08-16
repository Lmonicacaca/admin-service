package com.mbr.admin.manager.app;

import com.mbr.admin.common.utils.MerchantException;
import com.mbr.admin.domain.app.ProductVsChannel;
import com.mbr.admin.domain.app.Vo.ProductVSChannelVo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductVSChannelManager {
    public List<Map<String,Object>> queryChannel();
    public List<Map<String,Object>> queryProductId();
    public List<Map<String,Object>> queryOnlineStatus();
    public List<Map<String,Object>> queryIsForceShow();
    public List<Map<String,Object>> queryMerchantShow();
    public Map<String,Object> queryList(Long channelQuery,Long productQuery,Pageable page);
    public String addOrUpdate(ProductVSChannelVo productVSChannelVo)throws MerchantException;
    public void deleteProductVSChannel(Long id);
    public ProductVsChannel queryById(Long id);
}
