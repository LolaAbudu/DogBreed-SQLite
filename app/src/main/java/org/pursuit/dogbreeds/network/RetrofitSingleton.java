package org.pursuit.dogbreeds.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Wrapper class thats holds an instance of a Retrofit class
public class RetrofitSingleton {
    //because its stored statically, only the class can reference this instance
    //this creates only one instance and reuses it as often as you need. Allows for less garbage collection needed
    //think of the plate analogy
    private static Retrofit ourInstance;

    //this is responsible for creating the Breed and Dog objects
    public static Retrofit getInstance() {
        if (ourInstance != null) {
            return ourInstance;
        }
        ourInstance = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return ourInstance;
    }
    //this is creating a single instance of Retrofit, if it had already been called (!= null), it will just return what
    // was gotten the first time (thats saved in memory), but if its the first call, then it will use Retrofit to build the call
    // it prevents multiple instances call

    private RetrofitSingleton() { }
    //this is the default constructor; its private because we dont want other code in other classes to create an instance of the Retrofit; aka, NOONE that will be using my app
    // can ever make an instance of my Retrofit Object
    //explicitly creating the default constructor on my own takes away the automatic default constructor that would be public created by
}
//TODO 3 steps to creating the Retrofit Singleton instance
//default constructor should be private
//Fields should all be static
//put a check that checks for != null and returns original first Instance that was previously gotten, if it is null, then get the instance