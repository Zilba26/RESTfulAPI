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

    private static final String NOM_COMMUNE_PARAM = "Nom_commune";
    private static final String CODE_POSTAL_PARAM = "Code_postal";
    private static final String LIBELLE_ACHEMINEMENT_PARAM = "Libelle_acheminement";
    private static final String LIGNE_5_PARAM = "Ligne_5";
    private static final String LATITUDE_PARAM = "Latitude";
    private static final String LONGITUDE_PARAM = "Longitude";

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
        List<City> cities = new ArrayList<>();

        //build request
        String request = "SELECT * FROM ville_france";
        request = addParamToRequest(request, params, "nomCommune", NOM_COMMUNE_PARAM, true);
        request = addParamToRequest(request, params, "codeCommune", "Code_commune_INSEE", false);
        request = addParamToRequest(request, params, "codePostal", CODE_POSTAL_PARAM, false);
        request = addParamToRequest(request, params, "libelleAcheminement", LIBELLE_ACHEMINEMENT_PARAM, true);
        request = addParamToRequest(request, params, "ligne5", LIGNE_5_PARAM, false);
        request = addParamToRequest(request, params, "latitude", LATITUDE_PARAM, false);
        request = addParamToRequest(request, params, "longitude", LONGITUDE_PARAM, false);

        try (Connection connexion = daoFactory.getConnection();
             Statement statement = connexion.createStatement();
             ResultSet result = statement.executeQuery(request)) {

            while (result.next()) {
                String codeCommuneInsee = result.getString("Code_commune_INSEE");
                String name = result.getString(NOM_COMMUNE_PARAM);
                String postalCode = result.getString(CODE_POSTAL_PARAM);
                String libelleAcheminement = result.getString(LIBELLE_ACHEMINEMENT_PARAM);
                String ligne5 = result.getString(LIGNE_5_PARAM);
                String latitude = result.getString(LATITUDE_PARAM);
                String longitude = result.getString(LONGITUDE_PARAM);

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
        String request = "INSERT INTO ville_france (Code_commune_INSEE, Nom_commune, Code_postal, Libelle_acheminement, Ligne_5, Latitude, Longitude) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
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
        String request = "DELETE FROM ville_france WHERE Code_commune_INSEE = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, codeCommune);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public City update(String codeCommune, Map<String, String> params) {
        List<City> cities = list(Map.of("codeCommune", codeCommune));

        if (cities.isEmpty()) {
            return null;
        }
        City city = cities.get(0);

        //build request
        String request = "UPDATE ville_france";
        request = addParamToRequestUpdate(request, params, "nomCommune", NOM_COMMUNE_PARAM);
        request = addParamToRequestUpdate(request, params, "codePostal", CODE_POSTAL_PARAM);
        request = addParamToRequestUpdate(request, params, "libelleAcheminement", LIBELLE_ACHEMINEMENT_PARAM);
        request = addParamToRequestUpdate(request, params, "ligne5", LIGNE_5_PARAM);
        request = addParamToRequestUpdate(request, params, "latitude", LATITUDE_PARAM);
        request = addParamToRequestUpdate(request, params, "longitude", LONGITUDE_PARAM);

        request += " WHERE Code_commune_INSEE = '" + codeCommune + "'";

        try (Connection connection = daoFactory.getConnection();
             Statement statement = connection.createStatement()) {
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
