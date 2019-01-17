package org.pursuit.dogbreeds;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.pursuit.dogbreeds.controller.DogBreedAdapter;
import org.pursuit.dogbreeds.database.DogDatabase;
import org.pursuit.dogbreeds.model.Breeds;
import org.pursuit.dogbreeds.model.Dog;
import org.pursuit.dogbreeds.network.DogService;
import org.pursuit.dogbreeds.network.RetrofitSingleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "breed_all";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.breed_recyclerview);

        Retrofit retrofit = RetrofitSingleton.getInstance();
        //this a creating a local field that will store objects of type Retrofit = a static reference that
        // gets the called instance from the Retrofit singleton instance class and pulls data from our instance field
        // in that class, if it doesnt exsist create it and return it

        DogService dogService = retrofit.create(DogService.class);
        //creating a dog service from the network package and creates and object that inherits from DogService at runtime

        Call<Breeds> puppy = dogService.getDogBreeds();
        //creating a new puppy of type Breeds from the getDogBreeds method signature written inside the DogService
        // cInterface thats getting the Call from the api endpoint

        puppy.enqueue(new Callback<Breeds>() {
            @Override
            public void onResponse(Call<Breeds> call, Response<Breeds> response) {
                Log.d(TAG, "onResponse: " + response.body().getMessage());
                //response.body().getMessage() is returning an instance of Breeds Object

//                recyclerView.setAdapter(new DogBreedAdapter(response.body().getMessage()));
//                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                final List<String> breedList = response.body().getMessage();

                //making second retrofit call to call the image http string thats connected to the first retrofit call that got the breed names
                Retrofit retrofit = RetrofitSingleton.getInstance();
                final DogDatabase dogDatabase = DogDatabase.getInstance(getApplicationContext());

                DogService dogService = retrofit.create(DogService.class);
                for(int i = 0; i < breedList.size(); i++){
                    final String currentDogBreed = breedList.get(i);
                    Call<Dog> puppy = dogService.getDogImage(breedList.get(i));
                    //method that creates the Retrofit call; its a get request. breed is the endpoint we are passing. looki inside the interface to see the

                    puppy.enqueue(new Callback<Dog>() {
                        @Override
                        public void onResponse(Call<Dog> call, Response<Dog> response) {
                            Log.d(TAG, "onResponse: " + response.body().getMessage());
//                        intent.putExtra("image", response.body().getMessage());
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString(breed+"_image", response.body().getMessage());
//                        editor.commit();
//                        itemView.getContext().startActivity(intent);

                            dogDatabase.addDogImage(currentDogBreed, response.body().getMessage());
                        }

                        @Override
                        public void onFailure(Call<Dog> call, Throwable t) {
                            Log.d(TAG, "onResponse: " + t.toString());
                        }
                    });
                }
                recyclerView.setAdapter(new DogBreedAdapter(dogDatabase.getDogImageList()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                //recyclerView.setAdapter(new DogBreedAdapter(dogDatabase.get));


//                Call<Dog> puppy = dogService.getDogImage(breed);
//                //method that creates the Retrofit call; its a get request. breed is the endpoint we are passing. looki inside the interface to see the
//
//                puppy.enqueue(new Callback<Dog>() {
//                    @Override
//                    public void onResponse(Call<Dog> call, Response<Dog> response) {
//                        Log.d(TAG, "onResponse: " + response.body().getMessage());
////                        intent.putExtra("image", response.body().getMessage());
////                        SharedPreferences.Editor editor = sharedPreferences.edit();
////                        editor.putString(breed+"_image", response.body().getMessage());
////                        editor.commit();
////                        itemView.getContext().startActivity(intent);
//                    }
//
//                    @Override
//                    public void onFailure(Call<Dog> call, Throwable t) {
//                        Log.d(TAG, "onResponse: " + t.toString());
//                    }
//                });
            }

            @Override
            public void onFailure(Call<Breeds> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.toString());
            }
        });


    }
}
