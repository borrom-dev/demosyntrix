package com.angkorsuntrix.demosynctrix.controller;

import com.angkorsuntrix.demosynctrix.domain.Car;
import com.angkorsuntrix.demosynctrix.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class CarController {

    @Autowired
    private CarRepository repository;

    @GetMapping("/cars")
    public Iterable<Car> getCars() {
        return repository.findAll();
    }

    @PostMapping("/car")
    public Car create(@RequestBody final Car car) {
        return repository.save(car);
    }

    @DeleteMapping("/car")
    public void delete(@PathParam("id") final long id) {
        repository.deleteById(id);
    }

    @PutMapping("/car")
    public Car update(@RequestBody final Car car){
        return repository.save(car);
    }
}
