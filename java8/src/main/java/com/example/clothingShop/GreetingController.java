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

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Good> goods = goodRepo.findAll();
        model.put("message", "hello!");
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

        if ((categoryId == 1 ||
                categoryId == 2 ||
                categoryId == 3 ||
                categoryId == 4 ||
                categoryId == 7 ||
                categoryId == 8 ||
                categoryId == 9) &&
                !size.equals("S") && !size.equals("M") && !size.equals("L") && !size.equals("XL")
        ) {
            Iterable<Good> goods = goodRepo.findAll();
            model.put("message", "wrong size type");
            model.put("goods", goods);
            return "main";
        }

        if ((categoryId == 5 ||
                categoryId == 6) &&
                !size.equals("38") && !size.equals("39") && !size.equals("40") && !size.equals("41")) {
            Iterable<Good> goods = goodRepo.findAll();
            model.put("message", "wrong size type");
            model.put("goods", goods);
            return "main";
        }

        System.out.println(size);
        if (categoryId == 10 && !size.equals("one_size")) {
            Iterable<Good> goods = goodRepo.findAll();
            model.put("message", "wrong size type");
            model.put("goods", goods);
            return "main";
        }

        Good good = new Good(name, categoryId, size, count, price);

        goodRepo.save(good);

        Iterable<Good> goods = goodRepo.findAll();

        model.put("message", "record added");
        model.put("goods", goods);

        return "main";
    }

    @PostMapping("delete")
    public String delete(@RequestParam Integer id, Map<String, Object> model) {

        goodRepo.deleteById(id);

        Iterable<Good> goods = goodRepo.findAll();

        model.put("message", "record deleted");
        model.put("goods", goods);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam Integer from, @RequestParam Integer to, Map<String, Object> model) {
        Iterable<Good> goods = goodRepo.findAll();
        ArrayList<Good> goodsList = new ArrayList<>();
        List<Good> filteredGoodsList;

        if (from == null && to == null) {
            model.put("message", "no filters set");
            model.put("goods", goods);
            return "main";
        }

        goods.forEach(g -> goodsList.add(g));

        filteredGoodsList = goodsList.stream()
                .filter(g -> from < g.getPrice() && g.getPrice() < to).collect(Collectors.toList());

        model.put("message", "filtered out");
        model.put("goods", filteredGoodsList);

        return "main";
    }

    @PostMapping("update")
    public String update(@RequestParam Integer id, @RequestParam String name, Map<String, Object> model) {
        Iterable<Good> goods = goodRepo.findAll();

        goodRepo.findById(id).get().setName(name);

        goodRepo.saveAll(goods);

        model.put("message", "record updated");
        model.put("goods", goods);

        return "main";
    }
}
