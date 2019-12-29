package com.bishant.restaurantfinder.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bishant.restaurantfinder.R;
import com.bishant.restaurantfinder.helper.RestaurantFragment;
import com.bishant.restaurantfinder.helper.RestaurantRecyclerViewAdapter;
import com.bishant.restaurantfinder.model.MenuModel;
import com.bishant.restaurantfinder.model.PlaceDetailsModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.hdodenhof.circleimageview.CircleImageView;

/*
 * Created by Bishant on 26-Nov-19.
 * Copyright (c) 2019 Softwarica. All rights reserved.
 *
 */
public class MenuFragment extends RestaurantFragment implements View.OnClickListener {

    private RecyclerView recyclerViewMenu;
    private Button btnOrderNow;
    private String placeID;
    private MenuRVAdapter menuRVAdapter;
    private PlaceDetailsModel placeDetailsModel;
    private MenuModel menuModel;
    private List<MenuModel.Result> orderMenuList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        //placeID = getArguments().getString(Constants.PLACE_ID);

        initialiseView(rootView);
        initialiseListeners();
        prepareRecyclerView();

        menuModel = new GsonBuilder().create().fromJson(getString(R.string.food_menu_json), MenuModel.class);
        orderMenuList = new ArrayList<>();
        //placeDetailsModel = Utilities.getDetailData(placeID);

        return rootView;
    }

    @Override
    protected void initialiseView(View view) {
        recyclerViewMenu = view.findViewById(R.id.menu_recycler_view);
        btnOrderNow = view.findViewById(R.id.btn_order_food);
    }

    @Override
    protected void initialiseListeners() {
        btnOrderNow.setOnClickListener(this);
    }

    private void prepareRecyclerView() {
        menuRVAdapter = new MenuRVAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewMenu.setAdapter(menuRVAdapter);
        recyclerViewMenu.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_order_food:
                if (!orderMenuList.isEmpty()){
                    MenuModel menuModel = new MenuModel();
                    menuModel.setResults(orderMenuList);
                    Log.e("orders", new Gson().toJson(menuModel));
                } else {
                    Toast.makeText(getActivity(), "Please order some item first!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private class MenuRVAdapter extends RestaurantRecyclerViewAdapter {

        @Override
        public void add(Object object) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_food_menu, parent, false);
            return new VHMenu(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof VHMenu) {
                VHMenu vhMenu = (VHMenu) holder;
                vhMenu.tvFoodName.setText(menuModel.getResults().get(position).getName());
                vhMenu.tvFoodDesc.setText(menuModel.getResults().get(position).getDescription());
                vhMenu.tvFoodPrice.setText(new StringBuilder().append("Rs. ").append(menuModel.getResults().get(position).getPrice()).toString());
                vhMenu.ratingBar.setRating(menuModel.getResults().get(position).getRating());
                vhMenu.tvFoodQty.setText(String.valueOf(menuModel.getResults().get(position).getQuantity()));
            }
        }

        @Override
        public int getItemCount() {
            return menuModel.getResults().size();
        }

        private class VHMenu extends RecyclerView.ViewHolder implements View.OnClickListener {

            private CircleImageView ivFoodImage;
            private TextView tvFoodName, tvFoodDesc, tvFoodPrice, tvFoodQty;
            private ImageView btnAdd, btnRemove;
            private RatingBar ratingBar;

            public VHMenu(View itemView) {
                super(itemView);
                ivFoodImage = itemView.findViewById(R.id.iv_food_image);
                tvFoodName = itemView.findViewById(R.id.tv_food_name);
                tvFoodDesc = itemView.findViewById(R.id.tv_food_desc);
                tvFoodPrice = itemView.findViewById(R.id.tv_food_price);
                ratingBar = itemView.findViewById(R.id.food_rating_bar);
                tvFoodQty = itemView.findViewById(R.id.tv_food_quantity);
                btnAdd = itemView.findViewById(R.id.iv_btn_add);
                btnRemove = itemView.findViewById(R.id.iv_btn_remove);

                btnAdd.setOnClickListener(this);
                btnRemove.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_btn_add:
                        int fCount = Integer.parseInt(tvFoodQty.getText().toString());
                        Log.e("count: ", fCount+"");
                        if (fCount > 6) {
                            Toast.makeText(getActivity(), "Maximum limit reached.", Toast.LENGTH_SHORT).show();
                        } else {
                            fCount++;
                            Log.e("count+: ", fCount+"");
                            menuModel.getResults().get(getAdapterPosition()).setQuantity(fCount);
                            if (!orderMenuList.isEmpty()) {
                                if (orderMenuList.contains(menuModel.getResults().get(getAdapterPosition()))) {
                                    int index = orderMenuList.indexOf(menuModel.getResults().get(getAdapterPosition()));
                                    orderMenuList.get(index).setQuantity(fCount);
                                } else {
                                    orderMenuList.add(menuModel.getResults().get(getAdapterPosition()));
                                }
                            } else {
                                orderMenuList.add(menuModel.getResults().get(getAdapterPosition()));
                            }

                            notifyItemChanged(getAdapterPosition());
                        }
                        break;
                    case R.id.iv_btn_remove:
                        fCount = Integer.parseInt(tvFoodQty.getText().toString());

                        if (fCount <= 0) {
                            if (orderMenuList.contains(menuModel.getResults().get(getAdapterPosition()))) {
                                int index = orderMenuList.indexOf(menuModel.getResults().get(getAdapterPosition()));
                                orderMenuList.remove(index);
                            }
//                            orderMenuList.remove(menuModel.getResults().get(getAdapterPosition()));
                        } else {
                            fCount--;
                            menuModel.getResults().get(getAdapterPosition()).setQuantity(fCount);
                            if (!orderMenuList.isEmpty()) {
                                if (orderMenuList.contains(menuModel.getResults().get(getAdapterPosition()))) {
                                    int index = orderMenuList.indexOf(menuModel.getResults().get(getAdapterPosition()));
                                    orderMenuList.get(index).setQuantity(fCount);
                                }
                            }
                            notifyItemChanged(getAdapterPosition());
                        }
                        break;
                }
            }
        }
    }
}
