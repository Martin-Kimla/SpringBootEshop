package spring.eshop.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.eshop.models.dto.ProductDTO;
import spring.eshop.models.dto.mappers.ProductMapper;
import spring.eshop.models.services.ProductService;

import java.util.List;

@Controller
@RequestMapping("/produkty")
public class ProductsControllers {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public String renderIndex(
            Model model
    ) {
        List<ProductDTO> products = productService.getAll();
        model.addAttribute("products", products);
        return "pages/products/index";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/vytvorit")
    public String renderCreate(
            @ModelAttribute ProductDTO product
    ) {
        return "pages/products/create";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/vytvorit")
    public String createProduct(
            @Valid @ModelAttribute ProductDTO product,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors())
            return renderCreate(product);
        productService.create(product);
        redirectAttributes.addFlashAttribute("success", "Produkt vytvořen.");
        return "redirect:/produkty";
    }

    @GetMapping("/{productId}")
    public String renderDetail(
            @PathVariable long productId,
            Model model
    ) {
        ProductDTO product = productService.getById(productId);
        model.addAttribute("product", product);
        return "pages/products/detail";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/upravit/{productId}")
    public String renderEditForm(
            @PathVariable long productId,
            ProductDTO product
    ) {
        ProductDTO fetchedProduct = productService.getById(productId);
        productMapper.updateProductDTO(fetchedProduct, product);
        return "pages/products/edit";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/upravit/{productId}")
    public String editProduct(
            @PathVariable long productId,
            @Valid ProductDTO product,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors())
            return renderEditForm(productId, product);
        product.setProductId(productId);
        productService.edit(product);
        redirectAttributes.addFlashAttribute("success", "Produkt upraven.");
        return "redirect:/produkty";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("smazat/{productId}")
    public String deleteProduct(
            @PathVariable long productId,
            RedirectAttributes redirectAttributes
    ) {
        productService.remove(productId);
        redirectAttributes.addFlashAttribute("success", "Produkt smazán.");
        return "redirect:/produkty";
    }

}
