package ru.kirillbelousov.notebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kirillbelousov.notebook.service.LocaleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
@RequestMapping("/lcl")
public class LocaleController {

    @Autowired
    private LocaleService localeService;

    @GetMapping("/{mark}")
    public String setLocale(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable String mark) {
        Locale locale = localeService.getLocale(mark);
        localeService.setLocale(request, response, locale);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
