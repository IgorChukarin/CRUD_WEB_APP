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

        if (name != null && categoryId != null && size != null && count != null && price != null) {
            Good good = new Good(name, categoryId, size, count, price);
            goodRepo.save(good);
        }
        Iterable<Good> goods = goodRepo.findAll();
        String message = setMessageForAdd(name, categoryId, size, count, price);
        model.put("message", message);
        model.put("goods", goods);
        return "main";
    }

    private String setMessageForAdd(String name, Integer categoryId, String size, Integer count, Integer price) {
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
            message = "record added";
        }
        return message;
    }


    @PostMapping("delete")
    public String delete(@RequestParam Integer id, Map<String, Object> model) {
        String message = setMessageForDelete(id);
        if (id != null) { goodRepo.deleteById(id); }
        Iterable<Good> goods = goodRepo.findAll();
        model.put("message", message);
        model.put("goods", goods);
        return "main";
    }

    private String setMessageForDelete(Integer id) {
        return (id != null) ? "record deleted" : "not all fields are filled in";
    }


    @PostMapping("filter")
    public String filter(@RequestParam Map<String, String> priceFromTo, Map<String, Object> model) {
        Integer priceFrom = convertStringNumberToInteger(priceFromTo.get("priceFrom"));
        Integer priceTo = convertStringNumberToInteger(priceFromTo.get("priceTo"));
        List filteredGoods = filterGoodsByPrice(priceFrom, priceTo);
        String message = setMessageForFilter(priceFrom, priceTo);
        model.put("message", message);
        model.put("goods", filteredGoods);
        return "main";
    }

    private String setMessageForFilter(Integer from, Integer to) {
        String message;
        if (from == null && to == null) {
            message = "no filters set";
        }else if (from != null && to == null) {
            message = "from " + from;
        }else if (from == null && to != null) {
            message = "up to " + to;
        }else {
            message = "from " + from + " to " + to;
        }
        return message;
    }

    private ArrayList filterGoodsByPrice(Integer priceFrom, Integer priceTo) {
        ArrayList<Good> listOfGoods = (ArrayList<Good>) goodRepo.findAll();
        if (priceFrom == null && priceTo == null) {
            listOfGoods = (ArrayList<Good>) goodRepo.findAll();
        } else if (priceFrom != null && priceTo == null) {
            listOfGoods = (ArrayList<Good>) listOfGoods.stream().filter(good -> priceFrom <= good.getPrice())
                    .collect(Collectors.toList());
        } else if (priceFrom == null) {
            listOfGoods = (ArrayList<Good>) listOfGoods.stream().filter(good -> good.getPrice() <= priceTo)
                    .collect(Collectors.toList());
        } else {
            listOfGoods = (ArrayList<Good>) listOfGoods.stream().filter(good -> priceFrom <= good.getPrice() && good.getPrice() <= priceTo)
                    .collect(Collectors.toList());
        }
        return listOfGoods;
    }


    @PostMapping("update")
    public String update(@RequestParam Integer id, @RequestParam String newName, Map<String, Object> model) {
        setNewNameById(id, newName);
        Iterable<Good> goods = goodRepo.findAll();
        goodRepo.saveAll(goods);
        String message = setMessageForUpdate(id, newName);
        model.put("message", message);
        model.put("goods", goods);
        return "main";
    }

    private String setMessageForUpdate(Integer id, String name) {
        return  (id != null && name != null) ? "record updated" : "not all fields are filled in";
    }

    private void setNewNameById(Integer id, String newName) {
        if (id != null && newName != null) { goodRepo.findById(id).get().setName(newName); }
    }


    private Integer convertStringNumberToInteger(String stringNumber) {
        return (stringNumber.equals("")) ? null : Integer.valueOf(stringNumber);
    }
}

//TODO: refresh ids, methods checking that category fits size!!!