package com.webapp.arvand.arvandback.Controller;

import com.webapp.arvand.arvandback.CategorySerivce.CategoryInterface;
import com.webapp.arvand.arvandback.AAProductService.ProductInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private CategoryInterface categoryInterface;
    @Autowired
    private ProductInterface productInterface;
    @GetMapping("/admin/arv")
    public String dashboard(Model model) {
        String catCount = categoryInterface.getCategoriesCount();
        String proCount = productInterface.getProductsCount();

        model.addAttribute("title", "Dashboard");
        model.addAttribute("content", "admin/dashboard");
        model.addAttribute("categoriesCount", catCount);
        model.addAttribute("productCount", proCount);

        return "admin/layout";
    }
    @GetMapping("/admin/arv/categories")
    public String mainCategoryPage(Model model) {
        model.addAttribute("title", "Categories Management");
        model.addAttribute("content", "admin/category/main");
        model.addAttribute("currentPageName", "categories");
        return "admin/layout";
    }
    @GetMapping("/admin/arv/products")
    public String mainProductsPage(Model model) {
        model.addAttribute("title", "Products Management");
        model.addAttribute("content", "admin/products/product");
        model.addAttribute("currentPageName", "products");
        return "admin/layout";
    }
}
