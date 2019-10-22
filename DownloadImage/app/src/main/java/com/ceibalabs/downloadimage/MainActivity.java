package com.ceibalabs.downloadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ImageView downloadedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.downloadedImage = (ImageView) findViewById(R.id.imageView);
    }

    public void downloadImage(View view){

        //https://files.merca20.com/uploads/2018/03/Super-Mario-Nintendo-Bigstock.jpg

        Log.i("Interaction", "Button tapped");
        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;
        try {
            myImage = task.execute("https://files.merca20.com/uploads/2018/03/Super-Mario-Nintendo-Bigstock.jpg").get();

            this.downloadedImage.setImageBitmap(myImage); //assign the downloaded image to the image view.

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream(); //retrieves the whole image data pack in one go
                //convert to bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream); //converts data into an image with which we can work with
                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }
}
