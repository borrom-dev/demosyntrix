package com.angkorsuntrix.demosynctrix.repository;

import com.angkorsuntrix.demosynctrix.domain.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {

//    List<Car> findByBrand(String brand);

//    List<Car> findByColor(String color);
//
//    List<Car> findByYear(int year);
//
//    List<Car> findByBrandOrColor(String brand, String colors);
//
//    @Query("select c from Car c where c.brand =?1")
//    List<Car> findByBrand(String brand);
//
//    @Query("select c from Car c where c.brand like %?1")
//    List<Car> findByBrandEndsWith(String brand);
}
