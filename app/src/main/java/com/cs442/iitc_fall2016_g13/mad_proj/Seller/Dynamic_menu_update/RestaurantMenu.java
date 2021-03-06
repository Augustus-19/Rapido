package com.cs442.iitc_fall2016_g13.mad_proj.Seller.Dynamic_menu_update;

import java.util.ArrayList;

/**
 * Created by KiranCD on 11/17/2016.
 */

public class RestaurantMenu {

    String mRestaurantName;
    ArrayList<MenuItems> mMenuList;


    public RestaurantMenu(String mRestaurantName, ArrayList<MenuItems> mMenuList) {
        this.mRestaurantName = mRestaurantName;
        this.mMenuList = new ArrayList<>();
        this.mMenuList.addAll(mMenuList);
    }

    public void clearRestaurantMenu(){

        this.mRestaurantName = null;
        this.mMenuList.clear();

    }

    public String getmRestaurantName() {
        return mRestaurantName;
    }

    public ArrayList<MenuItems> getmMenuList() {
        return mMenuList;
    }

    public void setmRestaurantName(String mRestaurantName) {
        this.mRestaurantName = mRestaurantName;
    }

    public void setmMenuList(ArrayList<MenuItems> mMenuList) {
        this.mMenuList.clear();
        this.mMenuList.addAll(mMenuList);
    }
}
