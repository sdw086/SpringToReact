package com.example.reactboot.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;


public class MessageUtil {
    private static MessageSourceAccessor msAcc;

    @Autowired
    public void setMessageSourceAccessor(MessageSourceAccessor msAcc) {
        MessageUtil.msAcc = msAcc;
    }

    public static String getMessage(String key) {
        return msAcc.getMessage(key, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String key, Object... args) {
        return msAcc.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}
