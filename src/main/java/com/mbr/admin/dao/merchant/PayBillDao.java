package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.PayBill;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PayBillDao extends TkMapper<PayBill> {

    @Select("<script>" +
            "select * from pay_bill "+
            "<where>" +
                "<if test=\"merchantId!=null and merchantId!=''\">" +
                " and merchant_id=#{merchantId}"+
                "</if>"+
                "<if test=\"fromAddr!=null and fromAddr!=''\">" +
                " and from_addr=#{fromAddr}"+
                "</if>"+
                "<if test=\"toAddr!=null and toAddr!=''\">" +
                " and to_addr=#{toAddr}"+
                "</if>"+
                "<if test=\"billType!=-1\">" +
                    " and bill_type=#{billType}"+
                "</if>"+
                "<if test=\"merchantnameSearch!=null and merchantnameSearch!=''\">" +
                " and merchant_name like '%${merchantnameSearch}%'"+
                "</if>"+
                "<if test=\"status!=-1\">" +
                    " and status=#{status}"+
                "</if>"+
            "</where>"+
            " order by last_update_time desc"+
            "</script>")
    public List<PayBill> queryAllPayBill(@Param(value = "merchantId") String merchantId, @Param(value = "fromAddr")String fromAddr, @Param(value = "toAddr")String toAddr,@Param(value = "billType")int billType,@Param(value = "merchantnameSearch")String merchantnameSearch,@Param(value = "status")int status);
}
