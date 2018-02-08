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
}
