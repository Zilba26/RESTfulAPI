package fr.zilba.restfulapi.model;

import lombok.Data;

@Data
public class City {

    private String codeCommuneInsee;

    private String nomCommune;

    private String codePostal;

    private String libelleAcheminement;

    private String ligne5;

    private String latitude;

    private String longitude;

    public City(String codeCommuneInsee, String nomCommune, String codePostal, String libelleAcheminement, String ligne5, String latitude, String longitude) {
        this.codeCommuneInsee = codeCommuneInsee;
        this.nomCommune = nomCommune;
        this.codePostal = codePostal;
        this.libelleAcheminement = libelleAcheminement;
        this.ligne5 = ligne5;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "City [codeCommune=" + codeCommuneInsee + ", nomCommune=" + nomCommune + ", codePostal=" + codePostal
                + ", libelleAcheminement=" + libelleAcheminement + ", ligne5=" + ligne5 + ", latitude=" + latitude
                + ", longitude=" + longitude + "]";
    }
}
