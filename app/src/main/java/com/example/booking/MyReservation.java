package com.example.booking;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking.models.Reservation;
import com.example.booking.models.Reserve;
import com.example.booking.models.User;
import com.example.booking.models.UserReservation;
import com.example.booking.network.GetDataService;
import com.example.booking.network.RetrofitClientInstance;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReservation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navmenu);
        navigationView.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();


        // Get user reservations list from the web

        // Read user id from shared prefs
        /*  SharedPreferences  */

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userStr = sharedPref.getString("user", "ERROR on reading from SharedPrefs");

        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        User user = gson.fromJson(userStr, User.class); // des

        Log.d("user Id", user.id);


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<UserReservation>> call = service.myReservations(user.id);

        call.enqueue(new Callback<List<UserReservation>>() {
            @Override
            public void onResponse(Call<List<UserReservation>> call, Response<List<UserReservation>> response) {
//                progressDoalog.dismiss();

                Log.e("My reservations:", response.toString());

                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<UserReservation>> call, Throwable t) {
//                progressDoalog.dismiss();
                Toast.makeText(MyReservation.this, "Something went wrong on getting my reservations...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<UserReservation> myReservationList) {
//        recyclerView = findViewById(R.id.customRecyclerView);
//        adapter = new CustomAdapter(this,reservationList);

        final TableLayout tableLayout = (TableLayout) findViewById(R.id.my_reservations_table);

        for(int i = 0; i < myReservationList.size(); i++){

            final UserReservation res = myReservationList.get(i);

            Log.d("Date", res.getDate() + " - Time:" + res.getTime());

            /* Table Row */
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));


            /* Date TD */
            final TextView text = new TextView(this);

            text.setText(res.getDate());

            text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            tableRow.addView(text);
            /* ./Date TD */

            /* Time TD */
            final TextView timeTxt = new TextView(this);

            timeTxt.setText(res.getTime());

            timeTxt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            tableRow.addView(timeTxt);
            /* ./Time TD */


            /* Cancel Button TD */
            final Button button = new Button(this);
            button.setText("Cancel");
            button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyReservation.this);
                    // set title
                    alertDialogBuilder.setTitle("Are you sure?");
                    // set dialog message
                    alertDialogBuilder.setMessage("Are you sure?").setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // 1. Confirm button

                                    // Read user id from shared prefs
                                    /*  SharedPreferences  */

                                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    String userStr = sharedPref.getString("user", "ERROR on reading from SharedPrefs");

                                    Gson gson = new Gson(); // Or use new GsonBuilder().create();
                                    User user = gson.fromJson(userStr, User.class); // des

                                    Log.d("user Id", user.id);

                                    // 2. Request to the server to cancel reservation


                                    /*Create handle for the RetrofitInstance interface*/
                                    GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                                    Call<List<UserReservation>> call = service.cancelReservation(user.id, res.getId());

                                    call.enqueue(new Callback<List<UserReservation>>() {
                                        @Override
                                        public void onResponse(Call<List<UserReservation>> call, Response<List<UserReservation>> response) {
//                progressDoalog.dismiss();

                                            Log.e("My reservations after :", response.toString());
                                            showSuccessAndOpenHome();
                                            //generateDataList(response.body());
                                        }

                                        @Override
                                        public void onFailure(Call<List<UserReservation>> call, Throwable t) {
//                progressDoalog.dismiss();
                                            Toast.makeText(MyReservation.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                                            Log.d("Error:", t.getMessage());
                                            System.out.println(t.fillInStackTrace());

                                        }
                                    });
                                }

                                })
                                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                                // create alert dialog
                                AlertDialog alertDialog = alertDialogBuilder.create();

                                // show it
                        alertDialog.show();

                }
            });
            tableRow.addView(button);
            /* ./Cancel Button */


            tableLayout.addView(tableRow);


        }

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
    }


    private void showSuccessAndOpenHome(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MyReservation.this);
        builder.setMessage("Your reservation has been cancled succefully")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        MyReservation.this.recreate();


                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_home:
                Intent h= new Intent(MyReservation.this,MainActivity.class);
                h.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(h);
                break;
            case R.id.nav_profile:
                Intent w= new Intent(MyReservation.this,Profile.class);
                w.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(w);
                break;
            case R.id.nav_myReservation:
                Intent s= new Intent(MyReservation.this,MyReservation.class);
                s.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(s);
                break;
            case R.id.nav_about:
                Intent i= new Intent(MyReservation.this,AboutActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
            case R.id.nav_contact:
                Intent g= new Intent(MyReservation.this,ContactActivity.class);
                g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(g);
                break;
            case R.id.nav_logut:
                Intent l= new Intent(MyReservation.this,Login.class);
                l.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(l);
                break;


        }



        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}