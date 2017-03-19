package ca.ualberta.huco.goqueer_android.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;


import ca.ualberta.huco.goqueer_android.R;
import ca.ualberta.huco.goqueer_android.config.Constants;
import entity.QGallery;

/**
 * Created by Circa Lab on 3/18/2017.
 */

public class GalleryActivity extends AppCompatActivity {
    private ImageView mainMediaImage;
    public static QGallery gallery;
    private int currentIndex;
    private TextView mediaTitle, mediaDescription;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_main);
        mainMediaImage = (ImageView) findViewById(R.id.mainMediaImage);
        mediaTitle = (TextView) findViewById(R.id.mediaTitle);
        mediaDescription = (TextView) findViewById(R.id.mediaDescription);
        currentIndex = 0;
        setMediaContent(gallery.getMedias().get(currentIndex).getId());

        FloatingActionButton fabn = (FloatingActionButton) findViewById(R.id.next);
        fabn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex<gallery.getMedias().size()-1) {
                    currentIndex++;
                    setMediaContent(currentIndex);
                }
            }
        });


        FloatingActionButton fabp = (FloatingActionButton) findViewById(R.id.previous);
        fabp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex>0) {
                    currentIndex--;
                    setMediaContent(currentIndex);
                }
            }
        });
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(parent, name, context, attrs);
        return view;
    }

    public void setMediaContent(long mediaIndex) {
        if (mediaIndex < gallery.getMedias().size() && mediaIndex>=0){
            mediaTitle.setText(gallery.getMedias().get(currentIndex).getName());
            mediaDescription.setText(gallery.getMedias().get(currentIndex).getDescription());
            Picasso.with(getApplicationContext()).load(Constants.GO_QUEER_BASE_SERVER_URL + "client/downloadMediaById?media_id="+ gallery.getMedias().get(currentIndex).getId()).into(mainMediaImage);
        }


    }
}
