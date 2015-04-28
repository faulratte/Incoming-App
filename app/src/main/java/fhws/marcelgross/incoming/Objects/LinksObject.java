package fhws.marcelgross.incoming.Objects;

/**
 * Created by Marcel on 28.04.2015.
 */
public class LinksObject {

    private long id;
    private String title;
    private String url;
    private String created;

    public LinksObject(long id, String title, String url, String created) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.created = created;
    }

    public LinksObject() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
