package fr.zilba.restfulapi.controller;

import fr.zilba.restfulapi.model.City;
import fr.zilba.restfulapi.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CitiesDistanceController {

    @Autowired
    private VilleService cityService;

    @GetMapping("/cities-distance")
    public String get(Model model) {
        List<City> cities = cityService.getInfoCities("Nom_commune");
        model.addAttribute("cities", cities);
        return "cities_distances";
    }
}
