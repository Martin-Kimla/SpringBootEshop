package spring.eshop.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.eshop.models.dto.UserDTO;
import spring.eshop.models.exceptions.DuplicateEmailException;
import spring.eshop.models.exceptions.PasswordsDoNotEqualException;
import spring.eshop.models.services.UserService;

@Controller
@RequestMapping("/uzivatel")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/prihlasit")
    public String renderLogin() {
        return "/pages/account/login";
    }

    @GetMapping("/registrovat")
    public String renderRegister(@ModelAttribute UserDTO userDTO) {
        return "/pages/account/register";
    }

    @PostMapping("/registrovat")
    public String register(
            @Valid @ModelAttribute UserDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors())
            return renderRegister(userDTO);

        try {
            userService.create(userDTO, false);
        } catch (DuplicateEmailException e) {
            result.rejectValue("email", "error", "Email je již používán.");
            return "/pages/account/register";
        } catch (PasswordsDoNotEqualException e) {
            result.rejectValue("password", "error", "Hesla se nerovnají.");
            result.rejectValue("confirmPassword", "error", "Hesla se nerovnají.");
            return "/pages/account/register";
        }

        redirectAttributes.addFlashAttribute("success", "Uživatel zaregistrován.");
        return "redirect:/account/login";
    }

}
