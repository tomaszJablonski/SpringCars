package com.project.springcars;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/*
Napisz REST API dla listy pojazdów. Pojazd będzie miał pola:
id, mark, model, color.
API które będzie obsługiwało metody webowe:
•do pobierania wszystkich pozycji - done
•do pobierania elementu po jego id - done
•do dodawania pozycji - done
•do modyfikowania pozycji -done
•do usuwania jeden pozycji -done
Przy starcie aplikacji mają dodawać się 3 pozycje. (edited) - done



 */

@RestController
@RequestMapping(value = "/cars", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CarAPI {

    private List<Car> carList;

    public CarAPI() {
        this.carList = new ArrayList<>();
        carList.add(new Car(1L, "Citroen", "C-150", "BLACK"));
        carList.add(new Car(2L, "Audi", "X-450", "BLUE"));
        carList.add(new Car(3L, "Ferrari", "1567CE", "YELLOW"));
        carList.add(new Car(4L, "Peugeot", "5K78I", "WHITE"));
        carList.add(new Car(5L, "BMW", "XTURBOS3", "GREY"));
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(carList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> carId = carList.stream()
                .filter(car -> car.getId() == id)
                .findFirst();

        if (carId.isPresent()) {
            return new ResponseEntity<>(carId.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //TODO nothing happen, why ?
    //TODO do pobierania elementów w określonym kolorze (query) - prawie jest
    @GetMapping("/byColor/{color}")
    public ResponseEntity<Car> getCarByColor(@PathVariable String color) {
        List<Car> getCarWithColor = carList.stream()
                .filter(car -> car.getColor() == color)
                .toList();

        if (getCarWithColor.contains(color)) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addNewCar")
    public ResponseEntity postAddCar(@RequestBody Car car) {
        boolean addNewCar = carList.add(car);
        if (addNewCar) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Car> putEditCarById(@RequestBody Car newCar) {
        Optional<Car> carOptional = carList.stream()
                .filter(car -> car.getId() == newCar.getId())
                .findFirst();

        if (carOptional.isPresent()) {
            carList.remove(carOptional.get());
            carList.add(newCar);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // TODO
    // do modyfikowania jednego z pól pozycji (kolor)
    @PatchMapping ("/editNewColor")
    public ResponseEntity<Car> patchEditCarByColor(@RequestBody Car carByNewColor) {
        Optional<Car> carOptionalByColor = carList.stream()
                .filter(car -> car.getColor() == carByNewColor.getColor())
                .findAny();

        if (carOptionalByColor.isPresent()) {
            carList.remove(carOptionalByColor.get());
            carList.add(carByNewColor);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteCarById(@PathVariable Long id) {
        Optional<Car> carOptional = carList.stream()
                .filter(car -> car.getId() == id)
                .findFirst();

        if (carOptional.isPresent()) {
            carList.remove(carOptional.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
