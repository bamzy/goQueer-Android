package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Circa Lab on 3/12/2017.
 */

public class QGallery implements Serializable {
    private long id;
    private String name;
    private String description;

    public void setMedias(ArrayList<QMedia> medias) {
        this.medias = medias;
    }

    private ArrayList<QMedia> medias = new ArrayList();

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<QMedia> getMedias() {
        return medias;
    }
}
