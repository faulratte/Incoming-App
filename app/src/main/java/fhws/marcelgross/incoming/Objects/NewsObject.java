package fhws.marcelgross.incoming.Objects;

/**
 * Created by Marcel on 28.04.2015.
 */
public class NewsObject {

    private long id;
    private String title;
    private String text;
    private String date;
    private String validTo;
    private String created;

    public NewsObject()
    { };

    public NewsObject(long id, String title, String text, String date, String validTo, String created) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.validTo = validTo;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
