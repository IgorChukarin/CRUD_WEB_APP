package com.example.clothingShop;

import com.example.clothingShop.domain.Good;
import com.example.clothingShop.repos.GoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @PostMapping("add")
    public String add(@RequestParam String name,
                      @RequestParam int categoryId,
                      @RequestParam String size,
                      @RequestParam int count,
                      @RequestParam int price,
                      Map<String, Object> model) {
        Good good = new Good(name, categoryId, size, count, price);

        goodRepo.save(good);

        Iterable<Good> goods = goodRepo.findAll();

        model.put("goods", goods);

        return "main";
    }

    @PostMapping("delete")
    public String delete(@RequestParam Integer id, Map<String, Object> model) {

        goodRepo.deleteById(id);

        Iterable<Good> goods = goodRepo.findAll();

        model.put("goods", goods);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam Integer from, @RequestParam Integer to, Map<String, Object> model) {
        Iterable<Good> goods = goodRepo.findAll();
        ArrayList<Good> goodsList = new ArrayList<>();
        List<Good> filteredGoodsList = new ArrayList<>();

        if (from == null && to == null) {
            model.put("goods", goods);
            return "main";
        }

        goods.forEach(g -> goodsList.add(g));

        filteredGoodsList = goodsList.stream()
                .filter(g -> from < g.getPrice() && g.getPrice() < to).collect(Collectors.toList());

        model.put("goods", filteredGoodsList);


        return "main";
    }
}
