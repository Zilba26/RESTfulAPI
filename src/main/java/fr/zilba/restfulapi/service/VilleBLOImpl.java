package fr.zilba.restfulapi.service;

import fr.zilba.restfulapi.model.City;
import fr.zilba.restfulapi.repository.CityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VilleBLOImpl implements VilleBLO {

    @Autowired
    private CityDao cityDao;

    @Override
    public List<City> getInfoVilles(Map params) {
        return cityDao.list(params);
    }
}
