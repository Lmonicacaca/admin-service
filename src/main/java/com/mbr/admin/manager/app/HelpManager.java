package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.Help;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface HelpManager {
    public Map<String,Object> queryList(String title,String languageSearch, Pageable page);

    public int addOrUpdate(Help help);

    public void deleteHelp(Long id);

    public Help queryById(Long id);

    public List<Map<String,Object>> queryType();
}
