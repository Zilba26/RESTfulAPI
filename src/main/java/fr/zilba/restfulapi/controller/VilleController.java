package fr.zilba.restfulapi.controller;

import fr.zilba.restfulapi.model.City;
import fr.zilba.restfulapi.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VilleController {

    @Autowired
    VilleService villeService;

    @GetMapping("/ville")
    @ResponseBody
    public List<City> get(
            @RequestParam(required = false, value = "codeCommune") String codeCommune,
            @RequestParam(required = false, value = "nomCommune") String nomCommune,
            @RequestParam(required = false, value = "codePostal") String codePostal,
            @RequestParam(required = false, value = "libelleAcheminement") String libelleAcheminement,
            @RequestParam(required = false, value = "ligne5") String ligne5,
            @RequestParam(required = false, value = "latitude") String latitude,
            @RequestParam(required = false, value = "longitude") String longitude,
            @RequestParam(required = false, value = "order") String order,
            @RequestParam(required = false, value = "page") Integer page,
            @RequestParam(required = false, value = "size") Integer size) {

        Map<String, String> params = new HashMap<>();
        params.put("codeCommune", codeCommune);
        params.put("nomCommune", nomCommune);
        params.put("codePostal", codePostal);
        params.put("libelleAcheminement", libelleAcheminement);
        params.put("ligne5", ligne5);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

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

        Map<String, String> params = new HashMap<>();
        params.put("codeCommune", city.getCodeCommuneInsee());
        params.put("nomCommune", city.getNomCommune());
        params.put("codePostal", city.getCodePostal());
        params.put("libelleAcheminement", city.getLibelleAcheminement());
        params.put("ligne5", city.getLigne5());
        params.put("latitude", city.getLatitude());
        params.put("longitude", city.getLongitude());

        City goodCity = villeService.updateCity(city.getCodeCommuneInsee(), params);
        if (goodCity == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(goodCity);
        }
    }
}
