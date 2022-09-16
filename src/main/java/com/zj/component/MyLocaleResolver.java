package com.zj.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = null;
        String parameter = request.getParameter("l");
        if (StringUtils.isNotBlank(parameter)) {
            String[] splits = parameter.split("_");
            locale = new Locale(splits[0], splits[1]);
        } else {
            locale = new Locale("zh", "CN");
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
