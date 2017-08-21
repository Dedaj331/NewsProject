package hr.itot.newsproject;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailedActivity extends AppCompatActivity {


    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detailed);


        TextView describe = (TextView) findViewById(R.id.description);
        ImageView imageView = (ImageView) findViewById(R.id.imageInDetailedView);
        TextView titletv = (TextView) findViewById(R.id.title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String urlToImage = intent.getStringExtra("urlToImage");
        titletv.setText(title);

        Glide.with(getApplicationContext()).load(urlToImage).into(imageView);
        describe.setText(description);


//        urlToSource.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                  startActivity(new Intent(Intent.ACTION_VIEW,uri));
//            Intent intent=new Intent(getApplicationContext(),BrowserActivity.class);
//                intent.putExtra("url",uriToSource);
//                startActivity(intent);
//            }
//        });


    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, DetailedActivity.class));
    }
}
