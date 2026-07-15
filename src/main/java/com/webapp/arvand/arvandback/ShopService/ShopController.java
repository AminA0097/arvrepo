package com.webapp.arvand.arvandback.ShopService;

import com.webapp.arvand.arvandback.CategorySerivce.CategoryInterface;
import com.webapp.arvand.arvandback.CategorySerivce.CategorySimple;
import com.webapp.arvand.arvandback.EventService.EventInterface;
import com.webapp.arvand.arvandback.AAProductService.ProductInterface;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private CategoryInterface categoryInterface;
    @Autowired
    private ProductInterface productInterface;
    @Autowired
    private EventInterface eventInterface;

    @GetMapping()
    public String home(Model model) throws ApiException {
        PageResponse<CategorySimple> categoryDtos = categoryInterface.getCategoriesWithItsCount(
                    0,
                    8,
                    "faName",
                    "desc");
        model.addAttribute("categoryPage", categoryDtos);
        System.out.println(categoryDtos.getData());
        return "index";
    }
    @GetMapping("/product/{id}")
    public String product(@PathVariable String id, Model model) {
        model.addAttribute("productId", id);
        return "fragments/Shop-c/product";
    }
}
