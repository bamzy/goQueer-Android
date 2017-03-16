package entity;

import java.util.ArrayList;

/**
 * Created by Circa Lab on 3/12/2017.
 */

public class QGallery {
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







}
