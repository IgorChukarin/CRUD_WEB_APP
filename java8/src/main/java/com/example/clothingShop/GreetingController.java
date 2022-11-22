package com.example.clothingShop;
import com.example.clothingShop.domain.Good;
import com.example.clothingShop.repos.GoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;
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
    public String add(@RequestParam String name, @RequestParam Integer categoryId,
                      @RequestParam String size, @RequestParam Integer count,
                      @RequestParam Integer price, Map<String, Object> model) {

        ArrayList<Integer> clothesIdList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 7, 8, 9));
        ArrayList<Integer> shoesIdList = new ArrayList<>(Arrays.asList(5, 6));
        ArrayList<String> clothesSizes = new ArrayList<>(Arrays.asList("S", "M", "L", "XL"));
        ArrayList<String> shoesSizes = new ArrayList<>(Arrays.asList("38", "39", "40", "41"));

        String message;
        if (name == null || categoryId == null || size == null || count == null || price == null) {
            message = "not all fields are filled in";
        }
        else if (clothesIdList.contains(categoryId) && !clothesSizes.contains(size)) {
            message = "wrong size type for clothes";
        }
        else if (shoesIdList.contains(categoryId) && !shoesSizes.contains(size)) {
            message = "wrong size type for shoes";
        }
        else if (categoryId == 10 && !size.equals("one_size")) {
            message = "wrong size type for headdress";
        }
        else {
            Good good = new Good(name, categoryId, size, count, price);
            message = "record added";
            goodRepo.save(good);
        }

        Iterable<Good> goods = goodRepo.findAll();
        model.put("message", message);
        model.put("goods", goods);
        return "main";
    }




    @PostMapping("delete")
    public String delete(@RequestParam Integer id, Map<String, Object> model) {
        String message = setMessageForDelete(id);
        if (id != null) {
            goodRepo.deleteById(id);
        }

        Iterable<Good> goods = goodRepo.findAll();
        model.put("message", message);
        model.put("goods", goods);
        return "main";
    }


    @PostMapping("filter")
    public String filter(@RequestParam Integer from, @RequestParam Integer to, Map<String, Object> model) {
        String message = setMessageForFilter(from, to);

        Iterable<Good> goods = goodRepo.findAll();
        List<Good> filteredGoods = filterGoods(from, to, (ArrayList) goods);

        model.put("message", message);
        model.put("goods", filteredGoods);
        return "main";
    }


    @PostMapping("update")
    public String update(@RequestParam Integer id, @RequestParam String name, Map<String, Object> model) {
        String message = setMessageForUpdate(id, name);
        Iterable<Good> goods = goodRepo.findAll();
        setNewNameById(id, name);
        goodRepo.saveAll(goods);
        model.put("message", message);
        model.put("goods", goods);
        return "main";
    }


    private ArrayList filterGoods(Integer priceFrom, Integer priceTo, ArrayList<Good> listOfGoods) {
          if (priceFrom != null && priceTo == null) {
            listOfGoods = (ArrayList) listOfGoods.stream()
                    .filter(good -> priceFrom <= good.getPrice())
                    .collect(Collectors.toList());
        } else if (priceFrom == null) {
            listOfGoods = (ArrayList) listOfGoods.stream()
                    .filter(good -> good.getPrice() <= priceTo)
                    .collect(Collectors.toList());
        } else {
            listOfGoods = (ArrayList) listOfGoods.stream()
                    .filter(good -> priceFrom <= good.getPrice() && good.getPrice() <= priceTo)
                    .collect(Collectors.toList());
        }
        return listOfGoods;
    }


    private String setMessageForFilter(Integer from, Integer to) {
        if (from == null && to == null) {
            return"no filters set";
        }else if (from != null && to == null) {
            return "from " + from;
        }else if (from == null && to != null) {
            return "up to " + to;
        }else {
            return "from " + from + " to " + to;
        }
    }


    private String setMessageForUpdate(Integer id, String name) {
        if (id != null && name != null) {
            return "record updated";
        } else {
            return "not all fields are filled in";
        }
    }


    private String setMessageForDelete(Integer id) {
        if (id != null) {
            return "record deleted";
        } else {
            return "not all fields are filled in";
        }
    }


    private void setNewNameById(Integer id, String name) {
        if (id != null && name != null) {
            goodRepo.findById(id).get().setName(name);
        }
    }
}

//TODO: refresh ids
