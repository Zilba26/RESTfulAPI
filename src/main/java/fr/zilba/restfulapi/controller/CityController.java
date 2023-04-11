package fr.zilba.restfulapi.controller;

import fr.zilba.restfulapi.model.City;
import fr.zilba.restfulapi.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CityController {

    @Autowired
    CityService villeService;

    @GetMapping("/ville")
    @ResponseBody
    public List<City> get(
            @ModelAttribute City city,
            @RequestParam(required = false, value = "order") String order,
            @RequestParam(required = false, value = "page") Integer page,
            @RequestParam(required = false, value = "size") Integer size) {

        Map<String, String> params = getParams(city);
        return villeService.getInfoCities(params, order, page, size);
    }

    @GetMapping("/ville/count")
    public Integer count() {
        return villeService.getCount();
    }

    @PostMapping("/ville")
    @ResponseBody
    public City post(@RequestBody City city) {
        return villeService.createCity(city);
    }

    @DeleteMapping("/ville")
    @ResponseBody
    public ResponseEntity<City> delete(@RequestParam(value = "codeCommune") String codeCommune) {
        City city = villeService.deleteCity(codeCommune);
        if (city == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/ville")
    @ResponseBody
    public ResponseEntity<City> update(@RequestBody City city) {

        if (city.getCodeCommuneInsee() == null) {
            return ResponseEntity.badRequest().build();
        }

        Map<String, String> params = getParams(city);

        City goodCity = villeService.updateCity(city.getCodeCommuneInsee(), params);
        if (goodCity == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/ville/delete/{codeCommune}")
    @ResponseBody
    public ResponseEntity<City> partialDelete(@PathVariable String codeCommune) {
        boolean isGood = villeService.deletePartialCity(codeCommune);
        if (!isGood) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    private Map<String, String> getParams(City city) {
        Map<String, String> params = new HashMap<>();
        params.put("codeCommune", city.getCodeCommuneInsee());
        params.put("nomCommune", city.getNomCommune());
        params.put("codePostal", city.getCodePostal());
        params.put("libelleAcheminement", city.getLibelleAcheminement());
        params.put("ligne5", city.getLigne5());
        params.put("latitude", city.getLatitude());
        params.put("longitude", city.getLongitude());
        return params;
    }
}
