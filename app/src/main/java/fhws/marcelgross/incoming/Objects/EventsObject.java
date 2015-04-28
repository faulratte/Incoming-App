package fhws.marcelgross.incoming.Objects;

/**
 * Created by Marcel on 28.04.2015.
 */
public class EventsObject {


    private long id;
    private String title;
    private String text;
    private String termin;
    private String time;
    private String category;
    private String favorite;
    private String location;
    private String contactperson;
    private String contactdata;
    private String created;

    public EventsObject(long id, String title, String text, String termin, String time, String category, String favorite, String location, String contactperson, String contactdata, String created) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.termin = termin;
        this.time = time;
        this.category = category;
        this.favorite = favorite;
        this.location = location;
        this.contactperson = contactperson;
        this.contactdata = contactdata;
        this.created = created;
    }

    public EventsObject() {
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getContactdata() {
        return contactdata;
    }

    public void setContactdata(String contactdata) {
        this.contactdata = contactdata;
    }




}
