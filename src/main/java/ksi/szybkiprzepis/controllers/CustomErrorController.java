package ksi.szybkiprzepis.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMsg = "Wystąpił nieoczekiwany błąd";

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            switch (statusCode) {
                case 404:
                    errorMsg = "Nie znaleziono strony";
                    break;
                case 403:
                    errorMsg = "Brak dostępu";
                    break;
                case 500:
                    errorMsg = "Błąd serwera";
                    break;
            }
        }

        model.addAttribute("errorMessage", errorMsg);
        return "error";
    }
}