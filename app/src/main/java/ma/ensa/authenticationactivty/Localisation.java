package ma.ensa.authenticationactivty;

import java.io.Serializable;

public class Localisation implements Serializable {

    private final String agence;
    private final String resp;
    private final int tel;
    private final int altitude;
    private final int longitude;

    public Localisation(String agence, String resp, int tel, int altitude, int longitude) {
        this.agence = agence;
        this.resp = resp;
        this.altitude = altitude;
        this.longitude = longitude;
        this.tel = tel;
    }
    public String getAgence() {
        return agence;
    }
    public int getTel() {
        return tel;
    }
    public String getResp() {
        return resp;
    }
    public int getAltitude() {
        return altitude;
    }
    public int getLongitude() {
        return longitude;
    }
}
