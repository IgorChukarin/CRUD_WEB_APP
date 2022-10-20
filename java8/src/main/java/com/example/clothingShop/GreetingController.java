package com.example.clothingShop;

import com.example.clothingShop.domain.Good;
import com.example.clothingShop.repos.GoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private GoodRepo goodRepo;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Good> goods = goodRepo.findAll();

        model.put("goods", goods);

        return "main";
    }

    @PostMapping
    public String add(@RequestParam String name, @RequestParam int categoryId, Map<String, Object> model) {
        Good good = new Good(name, categoryId);

        goodRepo.save(good);

        Iterable<Good> goods = goodRepo.findAll();

        model.put("goods", goods);

        return "main";
    }
}
