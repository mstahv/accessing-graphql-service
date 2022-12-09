package org.example;

/**
 * DTO built on top of the GraphQL definition of IpApi example in StepZen.
 * 
 * Full type declaration:
 * <pre>
 * type IpApi_Location {
 *   status: String
 *   message: String
 *   continent: String
 *   continentCode: String
 *   country: String
 *   countryCode: String
 *   region: String
 *   regionName: String
 *   city: String
 *   district: String
 *   zip: String
 *   lat: Float
 *   lon: Float
 *   timezone: String
 *   offset: Int
 *   currency: String
 *   isp: String
 *   org: String
 *   as: String
 *   reserve: String
 *   mobile: Boolean
 *   proxy: Boolean
 *   hosting: Boolean
 *   ip: String
 * }
 * </pre>>
 */
public class LocationDTO {
    private String ip;
    private Double lat;
    private Double lon;
    private String city;
    private String isp;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    @Override
    public String toString() {
        return "LocationDTO{" + "ip=" + ip + ", lat=" + lat + ", lon=" + lon + ", city=" + city + ", isp=" + isp + '}';
    }

    
    
}
