package com.mbr.admin.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectUtils {
    /**
     *生成下拉列表
     * @param ids
     * @param texts
     * @return
     */
    public static List<Map<String,Object>> createListMap(Object[] ids, Object[] texts){
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",ids[i]);
            map.put("text",texts[i]);
            list.add(map);
        }
        return list;
    }
}
