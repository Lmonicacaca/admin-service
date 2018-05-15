package com.mbr.admin.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleMessageSource {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key,Object[] objects){
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(key,objects,locale);
        return message;
    }
}
