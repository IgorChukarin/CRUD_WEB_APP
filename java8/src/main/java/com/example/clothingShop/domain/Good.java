package com.example.clothingShop.domain;
import javax.persistence.*;

@Entity
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String size;

    @Column(name = "category_id")
    private int categoryId;

    private int count;
    private int price;


    public Good() {
    }

    public Good(String name, int categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public Good(String name, int categoryId, String size,  int count, int price) {
        this.name = name;
        this.size = size;
        this.categoryId = categoryId;
        this.count = count;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
