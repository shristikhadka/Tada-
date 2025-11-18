package com.shristikhadka.demo.controller;

import com.shristikhadka.demo.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController
{
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService){
        this.weatherService=weatherService;
    }

    @GetMapping("/humidity")
    public String getHumidity(@RequestParam String city){
        return weatherService.getHumidity(city);
    }
    @GetMapping("/temp")
    public String getTemp(@RequestParam String city){
        return weatherService.getTemp(city);
    }

}
