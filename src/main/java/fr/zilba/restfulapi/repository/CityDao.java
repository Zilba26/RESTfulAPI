package fr.zilba.restfulapi.repository;

import fr.zilba.restfulapi.model.City;

import java.util.List;
import java.util.Map;

public interface CityDao {

    List<City> list(Map<String, String> params, String order);

    default List<City> list(Map<String, String> params) {
        return list(params, null);
    }

    City add(City city);

    void delete(String codeCommune);

    City update(String codeCommune, Map<String, String> params);
}
