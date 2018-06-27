package com.mbr.admin.dao.merchant;

import com.mbr.admin.common.dao.TkMapper;
import com.mbr.admin.domain.merchant.MerchantHelp;
import com.mbr.admin.domain.merchant.Vo.MerchantHelpVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MerchantHelpDao extends TkMapper<MerchantHelp> {

    @Select("<script>" +
            "select h.id,h.cat_id as catId,h.title,h.content,h.create_time as createTime,h.create_user as createUser,h.create_user_name as createUserName,h.lang, hc.`name` as catName " +
            "from t_help as h,t_help_category as hc where h.cat_id = hc.id"+
            "<if test=\"title!=null and title!=''\">" +
            " and h.title LIKE '%${title}%'"+
            "</if>"+
            "</script>")
    public List<MerchantHelpVo> queryList(@Param("title") String title);

    @Select("select content from t_help where id=#{id}")
    public String queryContent(@Param("id")Integer id);

    @Delete("delete from t_help where id=#{id}")
    public int deleteMerchantHelp(@Param("id")Integer id);
}
