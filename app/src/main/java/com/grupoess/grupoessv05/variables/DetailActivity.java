package com.grupoess.grupoessv05.variables;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.grupoess.grupoessv05.R;

/***
 * Detail Activity that is launched when a list item is clicked. It shows more info on the sport.
 */
public class DetailActivity extends AppCompatActivity {


    /**
     * Initializes the activity, filling in the data from the Intent.
     * @param savedInstanceState Contains information about the saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Initialize the views
        TextView sportsTitle = (TextView)findViewById(R.id.titleDetail);
        ImageView sportsImage = (ImageView)findViewById(R.id.sportsImageDetail);

        //Create a placeholder gray scrim while the image loads
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.GRAY);

        //Set the text from the Intent extra
        sportsTitle.setText(getIntent().getStringExtra("Sport.TITLE_KEY"));

        //Load the image using the glide library and the Intent extra
        //Glide.with(this).load(getIntent().getIntExtra(Sport.IMAGE_KEY,0)).placeholder(gradientDrawable).into(sportsImage);
    }
}