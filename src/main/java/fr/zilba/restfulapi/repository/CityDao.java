package fr.zilba.restfulapi.repository;

import fr.zilba.restfulapi.model.City;

import java.util.List;
import java.util.Map;

public interface CityDao {

    List<City> list(Map params);
}
