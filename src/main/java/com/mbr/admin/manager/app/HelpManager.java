package com.mbr.admin.manager.app;

import com.mbr.admin.domain.app.Help;

import java.util.List;

public interface HelpManager {
    public List<Help> queryList(String title);

    public int addOrUpdate(Help help);

    public void deleteHelp(Long id);

    public Help queryById(Long id);
}
