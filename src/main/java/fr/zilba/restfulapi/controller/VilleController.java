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
            @RequestParam(required = false, value = "longitude") String longitude) {

        Map<String, String> params = new HashMap<>();
        params.put("codeCommune", codeCommune);
        params.put("nomCommune", nomCommune);
        params.put("codePostal", codePostal);
        params.put("libelleAcheminement", libelleAcheminement);
        params.put("ligne5", ligne5);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        return villeService.getInfoCities(params);
    }

    @PostMapping("/ville")
    @ResponseBody
    public City post(@RequestBody City ville) {
        return villeService.createCity(ville);
    }

    @DeleteMapping("/ville")
    @ResponseBody
    public ResponseEntity<?> delete(@RequestParam(value = "codeCommune") String codeCommune) {
        City city = villeService.deleteCity(codeCommune);
        if (city == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/ville")
    @ResponseBody
    public ResponseEntity<City> update(
            @RequestParam(value = "codeCommune") String codeCommune,
            @RequestParam(required = false, value = "nomCommune") String nomCommune,
            @RequestParam(required = false, value = "codePostal") String codePostal,
            @RequestParam(required = false, value = "libelleAcheminement") String libelleAcheminement,
            @RequestParam(required = false, value = "ligne5") String ligne5,
            @RequestParam(required = false, value = "latitude") String latitude,
            @RequestParam(required = false, value = "longitude") String longitude) {

        Map<String, String> params = new HashMap<>();
        params.put("codeCommune", codeCommune);
        params.put("nomCommune", nomCommune);
        params.put("codePostal", codePostal);
        params.put("libelleAcheminement", libelleAcheminement);
        params.put("ligne5", ligne5);
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        City city = villeService.updateCity(codeCommune, params);
        if (city == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(city);
        }
    }
}
