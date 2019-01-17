package org.pursuit.dogbreeds.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.pursuit.dogbreeds.DisplayActivity;
import org.pursuit.dogbreeds.R;
import org.pursuit.dogbreeds.model.Dog;
import org.pursuit.dogbreeds.model.DogImage;
import org.pursuit.dogbreeds.network.DogService;
import org.pursuit.dogbreeds.network.RetrofitSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//this class is being created
public class DogBreedViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "image_call";
    private SharedPreferences sharedPreferences;
    //this shared preference is used for caching(temporarily storing data, so you can use it later)
    //used so you dont have to keep making network calls over and over- read below t try to understand how
    //so for example, if i click on the african dog, it will make a retrofit call for the african dog, but
    // if you go back and click again, it sees that african dog call has already been made and send back the
    // first call and DOES NOT make a new call, but if you click the akita dog, it will make a new retrofit
    // call and get that dog

    private TextView breedTextView;
    private Intent intent;

    public DogBreedViewHolder(@NonNull View itemView) {
        super(itemView);
        breedTextView = itemView.findViewById(R.id.breed_textview);
        sharedPreferences = itemView.getContext().getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    //this seperates the action of taking data and putting it into views
    public void onBind(final DogImage breed) {
        breedTextView.setText(breed.getBreed());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(itemView.getContext(), DisplayActivity.class);
                intent.putExtra("breed", breed.getBreed());
                intent.putExtra("image", breed.getImageUrl());
                itemView.getContext().startActivity(intent);
                Log.d(TAG, String.valueOf(sharedPreferences.contains((breed + "_image"))));
//                if (sharedPreferences.contains((breed + "_image"))) {
//                    intent.putExtra("image", sharedPreferences.getString((breed + "_image"), null));
//                    itemView.getContext().startActivity(intent);
//                } else {
//                    Retrofit retrofit = RetrofitSingleton.getInstance();
//                    DogService dogService = retrofit.create(DogService.class);
//                    Call<Dog> puppy = dogService.getDogImage(breed);
//                    //method that creates the Retrofit call; its a get request. breed is the endpoint we are passing. looki inside the interface to see the
//
//                    puppy.enqueue(new Callback<Dog>() {
//                        @Override
//                        public void onResponse(Call<Dog> call, Response<Dog> response) {
//                            Log.d(TAG, "onResponse: " + response.body().getMessage());
//                            intent.putExtra("image", response.body().getMessage());
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString(breed+"_image", response.body().getMessage());
//                            editor.commit();
//                            itemView.getContext().startActivity(intent);
//                        }
//
//                        @Override
//                        public void onFailure(Call<Dog> call, Throwable t) {
//                            Log.d(TAG, "onResponse: " + t.toString());
//                        }
//                    });
                //}
            }
        });
    }
}
