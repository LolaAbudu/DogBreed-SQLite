package org.pursuit.dogbreeds.network;

import org.pursuit.dogbreeds.model.Breeds;
import org.pursuit.dogbreeds.model.Dog;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DogService {
    @GET("api/breeds/list")
    Call<Breeds> getDogBreeds();
    //above is using the url endpoint to call for and to get the dogBreeds as a list

    @GET("api/breed/{type}/images/random")
    Call<Dog> getDogImage(@Path("type") String breed);
    //above is using the url endpoint to get each specific breed one at a time
}

//interfaces cannot be instantiated
//Path method is where you want to pass in the argument of type String into the breed parameter.
// Retrofit then substitutes {type} with the breed and maps it into the path
//@GET is an annotation thats allows you to send info to Retofit to get data