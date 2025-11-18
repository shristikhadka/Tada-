package com.shristikhadka.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import org.json.JSONObject;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();


    public String getHumidity(String city) {

        // Build the API URL
        String url = apiUrl
                + "?q=" + city
                + "&appid=" + apiKey
                + "&units=metric";

        // Call external API
        String response = restTemplate.getForObject(url, String.class);

        // Parse JSON
        JSONObject obj = new JSONObject(response);
        int humidity = obj.getJSONObject("main").getInt("humidity");

        return "Humidity in " + city + " is " + humidity + "%";
    }
    public String getTemp(String city){
        String url = apiUrl
                + "?q=" + city
                + "&appid=" + apiKey
                + "&units=metric";

        String response=restTemplate.getForObject(url,String.class);
        //parse json
        JSONObject object=new JSONObject(response);
        double temp = object.getJSONObject("main").getDouble("temp");

        return "Temparature in"+city+"is"+temp+"F";

    }
}
