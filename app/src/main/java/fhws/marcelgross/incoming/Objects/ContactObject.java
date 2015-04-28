package fhws.marcelgross.incoming.Objects;

/**
 * Created by Marcel on 28.04.2015.
 */
public class ContactObject {

    private long id;
    private String title;
    private String status;
    private String firstname;
    private String lastname;
    private String tel;
    private String fax;
    private String email;
    private String homepage;
    private String street;
    private int zip;
    private String city;
    private String room;
    private String consultationhour;
    private String photo;

    public ContactObject(long id, String title, String status, String firstname, String lastname, String tel, String fax, String email, String homepage, String street, int zip, String city, String room, String consultationhour, String photo) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.firstname = firstname;
        this.lastname = lastname;
        this.tel = tel;
        this.fax = fax;
        this.email = email;
        this.homepage = homepage;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.room = room;
        this.consultationhour = consultationhour;
        this.photo = photo;
    }

    public ContactObject() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String string) {
        this.room = string;
    }

    public String getConsultationhour() {
        return consultationhour;
    }

    public void setConsultationhour(String consultationhour) {
        this.consultationhour = consultationhour;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
