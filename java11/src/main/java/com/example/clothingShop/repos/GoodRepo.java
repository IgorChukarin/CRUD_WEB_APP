package com.example.clothingShop.repos;

import com.example.clothingShop.domain.Good;
import org.springframework.data.repository.CrudRepository;

public interface GoodRepo extends CrudRepository<Good, Long> {
}
