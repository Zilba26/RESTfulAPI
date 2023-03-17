package fr.zilba.restfulapi.repository;

import fr.zilba.restfulapi.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CityDaoImpl implements CityDao {

    @Autowired
    private final DaoFactory daoFactory;

    CityDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private String addParamToRequest(String request, Map params, String param, String sqlParam, boolean isLike) {
        if (params.containsKey(param) && params.get(param) != null) {
            if (!request.contains("WHERE")) {
                request += " WHERE";
            } else {
                request += " AND";
            }
            String like;
            String value;
            if (isLike) {
                like = " LIKE";
                value = "%" + params.get(param) + "%";
            } else {
                like = "=";
                value = (String) params.get(param);
            }
            request += " " + sqlParam + like + "'" + value + "'";
        }
        return request;
    }

    @Override
    public List<City> list(Map params) {
        Connection connexion;
        Statement statement;
        ResultSet result;
        List<City> cities = new ArrayList<>();

        //build request
        String request = "SELECT * FROM ville_france";
        request = addParamToRequest(request, params, "nomCommune", "Nom_commune", true);
        request = addParamToRequest(request, params, "codeCommune", "Code_commune_INSEE", false);
        request = addParamToRequest(request, params, "codePostal", "Code_postal", false);
        request = addParamToRequest(request, params, "libelleAcheminement", "Libelle_acheminement", true);
        request = addParamToRequest(request, params, "ligne5", "Ligne_5", false);
        request = addParamToRequest(request, params, "latitude", "Latitude", false);
        request = addParamToRequest(request, params, "longitude", "Longitude", false);
        System.out.println(request);

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            result = statement.executeQuery(request);

            while (result.next()) {
                int id = result.getInt("Code_commune_INSEE");
                String name = result.getString("Nom_commune");
                String postalCode = result.getString("Code_postal");
                String libelleAcheminement = result.getString("Libelle_acheminement");
                String ligne5 = result.getString("Ligne_5");
                String latitude = result.getString("Latitude");
                String longitude = result.getString("Longitude");

                City city = new City(id, name, postalCode, libelleAcheminement, ligne5, latitude, longitude);
                cities.add(city);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cities;
    }
}
