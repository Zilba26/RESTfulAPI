package fr.zilba.restfulapi.controller;

import fr.zilba.restfulapi.model.City;
import fr.zilba.restfulapi.service.VilleBLO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    @Autowired
    VilleBLO villeBLO;

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

        return villeBLO.getInfoVilles(params);
    }
}
