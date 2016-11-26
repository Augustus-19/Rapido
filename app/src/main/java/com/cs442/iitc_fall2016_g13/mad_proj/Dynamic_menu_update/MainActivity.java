package com.cs442.iitc_fall2016_g13.mad_proj.Dynamic_menu_update;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.iitc_fall2016_g13.mad_proj.R;
import com.cs442.iitc_fall2016_g13.mad_proj.Seller.Dynamic_menu_update.SingletonClass2;
import com.cs442.iitc_fall2016_g13.mad_proj.ServerConnect.GlobalVariables;
import com.cs442.iitc_fall2016_g13.mad_proj.fragmentlayout.MenuAndCartActivity;
import com.cs442.iitc_fall2016_g13.mad_proj.fragmentlayout.SingletonClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    String dbKeyRestaurants = "RESTAURANTS";
    String dbKeyRestaurantName = "RESTAURANT_NAME";
    String dbKeyPrice = "PRICE";
    String dbKeyIngredients = "INGREDIENTS";
    String dbKeyRestaurantMenu = "MENU";
    TextView mMenuNameTxt;
    TextView mMenuPrieTxt;
    TextView mMenuIngredientsTxt;
    CustomAdapterMenu mArrayAdapter;
    RestaurantMenu mRestaurantMenu;
    Context mContext;
    boolean mFlag = true;
    Double mLatitude, mLongitude;
    String mRestautantName;
    String mLatLongString;

    ListView mListView;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_updatemenu);
        key= GlobalVariables.SelectedRestaurantName;

        Intent intent = getIntent();

        mLatitude = intent.getDoubleExtra("LAT",0.0);
        Log.v(TAG,"latitutde"+mLatitude);
        mLongitude = intent.getDoubleExtra("LON",0.0);
        mRestautantName = intent.getStringExtra("restaurantName");

        Log.v(TAG,"mRestautantName"+mRestautantName);
        mLatLongString = String.valueOf(mLatitude)+":"+String.valueOf(mLongitude);
        mLatLongString = mLatLongString.replace('.','_');
        Log.v(TAG,"mLatLongString"+mLatLongString);

        mContext = this;
        TextView restaurantName = (TextView) findViewById(R.id.txtRestaurantName);
        restaurantName.setText(mRestautantName);


        LoadMenuListView();
        Button mBtnRrefresh = (Button) findViewById(R.id.btnRefreshMenu);
        mBtnRrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mRestaurantMenu == null ||mRestaurantMenu.getmMenuList().size() < 0 ){

                    Toast.makeText(getApplicationContext(),"There is no menu for this pls add some menu", Toast.LENGTH_SHORT).show();

                    return;
                }
                SingletonClass.initInstance(mContext).updateArray(mRestaurantMenu.getmMenuList());
                Intent intent = new Intent(v.getContext(), MenuAndCartActivity.class); //Menu and Cart.class was launched here.
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }


    void LoadMenuListView(){

        Log.v(TAG,"LoadMenuListView");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(dbKeyRestaurants);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.v(TAG,"onDataChange");
                if(mFlag == false){
                    Log.v(TAG,"mFlag is false");
                    return;

                }


//               dataSnapshot.child("latlong");



                for (DataSnapshot latlong : dataSnapshot.getChildren())
                {
                    if(key.compareTo(latlong.getKey().toString())== 0){


                        Log.v(TAG,"mLatLongString is equal to data base ");
                        ArrayList<MenuItems> menuItems = new ArrayList<MenuItems>();

                        Log.v(TAG,"Location:"+latlong.getKey());
                        Log.v(TAG,"Restaurant Name:"+latlong.child(dbKeyRestaurantName).getValue());
                        DataSnapshot menu = latlong.child(dbKeyRestaurantMenu);
                        for (DataSnapshot menuIttr : menu.getChildren()) {

                            String menuName = menuIttr.getKey();
                            String price = menuIttr.child(dbKeyPrice).getValue().toString();
                            String description = menuIttr.child(dbKeyIngredients).getValue().toString();
                            Log.v(TAG,"menuName"+menuName+"price"+price+"description"+description);
//                            String description = "junk";
                            menuItems.add(new MenuItems(menuName,price,description));
                        }

                        if(mRestaurantMenu == null){

                            Log.v(TAG,"mRestaurantMenu created");
                            mRestaurantMenu = new RestaurantMenu(latlong.getKey(),menuItems);
                        }else{
                            Log.v(TAG,"mRestaurantMenu cleared and updated");
                            mRestaurantMenu.clearRestaurantMenu();
                            mRestaurantMenu.setmRestaurantName(latlong.getKey());
                            mRestaurantMenu.setmMenuList(menuItems);

                        }

                    }
                }

                //refreshMenu();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    void refreshMenu(){

        Log.v(TAG,"refresh Menu");
        if(mRestaurantMenu != null){

            if(mArrayAdapter == null){

                Log.v(TAG,"new adapter");
                mArrayAdapter = new CustomAdapterMenu(getApplicationContext(), R.layout.menu_list_view_item, mRestaurantMenu.getmMenuList());
             //   mListView.setAdapter(mArrayAdapter);

            }else{

                Log.v(TAG,"updating  adapter");
                //mArrayAdapter.setmMenuItems(mRestaurantMenu.getmMenuList());
                mArrayAdapter.notifyDataSetChanged();

            }

        }


    }
}
