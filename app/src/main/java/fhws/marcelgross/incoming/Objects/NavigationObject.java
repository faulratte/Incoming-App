package fhws.marcelgross.incoming.Objects;

/**
 * Created by Marcel on 28.04.2015.
 */
public class NavigationObject {

    private long id;
    private String favorite;
    private String category;
    private String Name;
    private String Street;
    private int House_NR;
    private int Zipcode;
    private String City;
    private String Phone;
    private String Contactperson;
    private String Oeffnungszeiten;
    private double Longitude;
    private double Latitude;
    private String pic;

    public NavigationObject(long id, String favorite, String category, String name, String street, int house_NR, int zipcode, String city, String phone, String contactperson, String oeffnungszeiten, double longitude, double latitude, String pic) {
        this.id = id;
        this.favorite = favorite;
        this.category = category;
        Name = name;
        Street = street;
        House_NR = house_NR;
        Zipcode = zipcode;
        City = city;
        Phone = phone;
        Contactperson = contactperson;
        Oeffnungszeiten = oeffnungszeiten;
        Longitude = longitude;
        Latitude = latitude;
        this.pic = pic;
    }

    public NavigationObject() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public int getHouse_NR() {
        return House_NR;
    }

    public void setHouse_NR(int house_NR) {
        House_NR = house_NR;
    }

    public int getZipcode() {
        return Zipcode;
    }

    public void setZipcode(int zipcode) {
        Zipcode = zipcode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getContactperson() {
        return Contactperson;
    }

    public void setContactperson(String contactperson) {
        Contactperson = contactperson;
    }

    public String getOeffnungszeiten() {
        return Oeffnungszeiten;
    }

    public void setOeffnungszeiten(String oeffnungszeiten) {
        Oeffnungszeiten = oeffnungszeiten;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

}
