package com.example.demo.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cars")
public class CarsController {

    @PostMapping("/addcar")
    public String addCars(){
        return "6 to the 9 to the 8 representing the ABQ what UPPP BIOCHHHHHH";
    }

}
