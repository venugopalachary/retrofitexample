package com.pidigital.retrofitexample;

import static com.pidigital.retrofitexample.R.id.textView;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    private TextView textView;

    private TextView textView2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize the ImageView
        imageView = findViewById(R.id.imageView);

        textView =findViewById(R.id.textView);


        textView2 =findViewById(R.id.textView2);


        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.npoint.io/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // Create an instance of the ApiService
        ApiService apiService = retrofit.create(ApiService.class);

        // Make the network request to get the image URL
        Call<ImageModel> call = apiService.getImageUrl();
        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ImageModel imageModel = response.body();

                    // Load the image into the ImageView using a library like Picasso or Glide
                    String imageUrl = imageModel.getImageUrl();
                    Picasso.get().load(imageUrl).into(imageView);

                    Toast.makeText(MainActivity.this, "response"+imageModel.getHeading(), Toast.LENGTH_SHORT).show();
                    textView.setText(imageModel.getHeading());
                    textView2.setText(imageModel.getDescription());
                } else {
                    // Handle the error
                    Toast.makeText(MainActivity.this, "Failed to update image", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                // Handle network errors
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}