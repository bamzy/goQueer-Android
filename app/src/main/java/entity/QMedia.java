package entity;

/**
 * Created by Circa Lab on 3/15/2017.
 */

public class QMedia {
    private long id;
    private String source;
    private String description;
    private String name;
    private String publish_date;
    private String display_date;
    private String type_id;
    private String media_url;
    private String extra_links;

    public String getType_id() { return type_id; }

    public long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getMedia_url() {return media_url;}

    public String getExtra_links() {
        return extra_links;
    }
}
