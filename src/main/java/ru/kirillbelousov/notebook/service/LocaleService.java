package ru.kirillbelousov.notebook.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Service
public class LocaleService {

    public Locale getLocale(String mark){
        switch (mark){
            case "en":
                return Locale.UK;
            case "us":
                return Locale.US;
            case"de":
                return Locale.GERMANY;
            case "ru":
                return new Locale("ru", "RU");
            default:
                return Locale.UK;
        }
    }
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale){
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        localeResolver.setLocale(request, response, locale);
    }
}
