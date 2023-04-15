package fr.zilba.restfulapi.service;

import fr.zilba.restfulapi.model.City;

import java.util.List;
import java.util.Map;

public interface CityService {


    List<City> getInfoCities(Map<String, String> params, String order, Integer page, Integer size);

    City createCity(City ville);

    City deleteCity(String codeCommune);

    City updateCity(String codeCommune, Map<String, String> params);

    Integer getCount();

    boolean deletePartialCity(String codeCommune);

    boolean uninhibitedCity(String codeCommune);
}
