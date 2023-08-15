package com.MyApplication.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.PetsViewHolder> {

    private ArrayList<PetModel> pets = new ArrayList<>();

    private OnPetClick onPetClickListener;
    public PetsAdapter(OnPetClick listener) {
        this.onPetClickListener = listener;
    }

    public void addPets(ArrayList<PetModel> animalProfiles) {
        pets = animalProfiles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PetsAdapter.PetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.pet_layout, parent, false);
        PetsViewHolder viewHolder = new PetsViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PetsAdapter.PetsViewHolder holder, int position) {
        holder.bind(pets.get(position));
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public class PetsViewHolder extends RecyclerView.ViewHolder {
        Button showProfilesButton;
        ImageView profileImage;
        TextView textViewName;
        TextView textViewAge;
        TextView textViewGender;
        TextView textViewType;
        TextView textViewAddress;

        public PetsViewHolder(View itemView) {
            super(itemView);
            showProfilesButton = itemView.findViewById(R.id.showProfilesButton);
            profileImage = itemView.findViewById(R.id.profileImage);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            textViewGender = itemView.findViewById(R.id.textViewGender);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
        }

        public void bind(PetModel animalProfiles) {
            System.out.println("@@@@@@))))) $"+animalProfiles.name + " = " + animalProfiles.id);
            Glide
                    .with(itemView.getContext())
                    .load(animalProfiles.pictureUrl)
                    .centerCrop()
                    .placeholder(R.drawable.dog)
                    .into(profileImage);

            textViewName.setText(animalProfiles.name);
            textViewAge.setText(animalProfiles.age);
            textViewGender.setText(animalProfiles.gender);
            textViewType.setText(animalProfiles.type);
            textViewAddress.setText(animalProfiles.address);

            showProfilesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPetClickListener.onPetClickListener(animalProfiles, pets);
                }
            });
        }
    }
    interface OnPetClick{
        void onPetClickListener(PetModel petModel, ArrayList<PetModel> petModelArrayList);
    }
}
