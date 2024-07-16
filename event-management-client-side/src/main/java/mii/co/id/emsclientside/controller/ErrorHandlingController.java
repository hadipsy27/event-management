package mii.co.id.emsclientside.controller;

//<editor-fold defaultstate="collapsed" desc="Import">
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
//</editor-fold>

@Controller
public class ErrorHandlingController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        String errorPage = "error";
        String pageTittle = "Error";

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                pageTittle = "404 : Page Not Found";
                errorPage = "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                pageTittle = "500 : Internal Server Error";
                errorPage = "error/500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                pageTittle = "403 : Forbidden";
                errorPage = "error/403";
            }
        }
        model.addAttribute("pageTitle", pageTittle);
        return errorPage;
    }

}
