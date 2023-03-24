package fr.zilba.restfulapi.service;

import fr.zilba.restfulapi.model.City;
import fr.zilba.restfulapi.repository.CityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VilleServiceImpl implements VilleService {

    @Autowired
    private CityDao cityDao;

    @Override
    public List<City> getInfoCities(Map<String, String> params) {
        return cityDao.list(params);
    }

    @Override
    public City createCity(City city) {
        return cityDao.add(city);
    }

    @Override
    public City deleteCity(String codeCommune) {
        List<City> cities = cityDao.list(Map.of("codeCommune", codeCommune));
        if (cities.isEmpty()) {
            return null;
        } else {
            cityDao.delete(codeCommune);
            return cities.get(0);
        }
    }

    @Override
    public City updateCity(String codeCommune, Map<String, String> params) {
        return cityDao.update(codeCommune, params);
    }
}
