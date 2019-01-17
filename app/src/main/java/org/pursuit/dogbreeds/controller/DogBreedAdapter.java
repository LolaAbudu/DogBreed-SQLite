package org.pursuit.dogbreeds.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pursuit.dogbreeds.R;
import org.pursuit.dogbreeds.model.DogImage;
import org.pursuit.dogbreeds.view.DogBreedViewHolder;

import java.util.List;

//this class is in control of keeping track of where the data is on the screen
public class DogBreedAdapter extends RecyclerView.Adapter<DogBreedViewHolder> {
    List<DogImage> breedsList;
    //this is creating a List of Strings called breedsList

    public DogBreedAdapter(List<DogImage> breedsList) {
        this.breedsList = breedsList;
    }
    //this is setting up the constructor for the class DogBreedAdapter and its setting its parameters to
    // the List of Strings created above

    @NonNull
    @Override
    public DogBreedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View childView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.breed_itemview, viewGroup, false);
        return new DogBreedViewHolder(childView);
    }
    //this method is inflating the layout created inside the Resource layout folder and getting the context
    // from the viewGroup that will be passed into it. It then returns a new DogViewHolder

    @Override
    public void onBindViewHolder(@NonNull DogBreedViewHolder dogBreedViewHolder, int i) {
        dogBreedViewHolder.onBind(breedsList.get(i));
    }
    //says everytime the view pops up, populate from the view that you get from that position of the List.
    //this method binds onto the viewHolder whatever String item is on the List depending on its position

    @Override
    public int getItemCount() {
        return breedsList.size();
    }
    //this returns the size of the items inside the List created and creates that many elements
}
//TODO onCreateViewHolder is called only once when the app is ran
//TODO onBindViewHolder is called as many times as you have to bind data to the view(like when you are swiping the screen)