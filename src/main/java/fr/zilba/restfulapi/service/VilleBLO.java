package fr.zilba.restfulapi.service;

import fr.zilba.restfulapi.model.City;

import java.util.List;
import java.util.Map;

public interface VilleBLO {


    List<City> getInfoVilles(Map params);
}
