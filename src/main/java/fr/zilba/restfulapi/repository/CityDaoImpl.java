package fr.zilba.restfulapi.repository;

import fr.zilba.restfulapi.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

    private String addParamToRequest(String request, Map<String, String> params, String param, String sqlParam, boolean isLike) {
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
                value = params.get(param);
            }
            request += " " + sqlParam + like + "'" + value + "'";
        }
        return request;
    }

    @Override
    public List<City> list(Map<String, String> params) {
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

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            result = statement.executeQuery(request);

            while (result.next()) {
                String codeCommuneInsee = result.getString("Code_commune_INSEE");
                String name = result.getString("Nom_commune");
                String postalCode = result.getString("Code_postal");
                String libelleAcheminement = result.getString("Libelle_acheminement");
                String ligne5 = result.getString("Ligne_5");
                String latitude = result.getString("Latitude");
                String longitude = result.getString("Longitude");

                City city = new City(codeCommuneInsee, name, postalCode, libelleAcheminement, ligne5, latitude, longitude);
                cities.add(city);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cities;
    }

    @Override
    public City add(City city) {
        Connection connexion;
        PreparedStatement statement;

        String request = "INSERT INTO ville_france (Code_commune_INSEE, Nom_commune, Code_postal, Libelle_acheminement, Ligne_5, Latitude, Longitude) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.prepareStatement(request);
            statement.setString(1, city.getCodeCommuneInsee());
            statement.setString(2, city.getNomCommune());
            statement.setString(3, city.getCodePostal());
            statement.setString(4, city.getLibelleAcheminement());
            statement.setString(5, city.getLigne5());
            statement.setString(6, city.getLatitude());
            statement.setString(7, city.getLongitude());
            statement.executeUpdate();
            return city;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String codeCommune) {
        Connection connexion;
        PreparedStatement statement;

        String request = "DELETE FROM ville_france WHERE Code_commune_INSEE = ?";

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.prepareStatement(request);
            statement.setString(1, codeCommune);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public City update(String codeCommune, Map<String, String> params) {
        Connection connexion;
        Statement statement;
        List<City> cities = list(Map.of("codeCommune", codeCommune));

        if (cities.isEmpty()) {
            return null;
        }
        City city = cities.get(0);

        //build request
        String request = "UPDATE ville_france";
        request = addParamToRequestUpdate(request, params, "nomCommune", "Nom_commune");
        request = addParamToRequestUpdate(request, params, "codePostal", "Code_postal");
        request = addParamToRequestUpdate(request, params, "libelleAcheminement", "Libelle_acheminement");
        request = addParamToRequestUpdate(request, params, "ligne5", "Ligne_5");
        request = addParamToRequestUpdate(request, params, "latitude", "Latitude");
        request = addParamToRequestUpdate(request, params, "longitude", "Longitude");

        request += " WHERE Code_commune_INSEE = '" + codeCommune + "'";

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            statement.executeUpdate(request);
            return city;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String addParamToRequestUpdate(String request, Map<String, String> params, String param, String sqlParam) {
        if (params.containsKey(param) && params.get(param) != null) {
            if (!request.contains("SET")) {
                request += " SET ";
            } else {
                request += ",";
            }
            request += " " + sqlParam + " = '" + params.get(param) + "'";
        }
        return request;
    }
}
