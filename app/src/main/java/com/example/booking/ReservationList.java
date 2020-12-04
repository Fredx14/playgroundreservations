package com.example.booking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking.models.Reservation;
import com.example.booking.models.Reserve;
import com.example.booking.models.User;
import com.example.booking.network.GetDataService;
import com.example.booking.network.RetrofitClientInstance;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        Log.d("MyTaG", "Hello, World! 9090");

//        progressDoalog.setMessage("Loading....");
//        progressDoalog.show();


        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Reservation>> call = service.getAllReservations();
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
//                progressDoalog.dismiss();

                Log.e("Res:", response.toString());

                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
//                progressDoalog.dismiss();
                Toast.makeText(ReservationList.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void user_reservations(String user_id){

    }
    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Reservation> reservationList) {
//        recyclerView = findViewById(R.id.customRecyclerView);
//        adapter = new CustomAdapter(this,reservationList);

        final TableLayout tableLayout = (TableLayout) findViewById(R.id.reservations_table);

        for(int i = 1; i < reservationList.size(); i++){

           final Reservation res = reservationList.get(i);
            Log.d("Date", res.getDate() + " - " + res.getFirstTime() + " - " + res.getSecondTime()+ " - " + res.getThirdTime());


            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            // Creation textView
            final TextView text = new TextView(this);

            text.setText(res.getDate());

            text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            tableRow.addView(text);
            // Creation  button

            Log.d("dd", res.getFirstTime());
            /*********************************/
            /************    05:00  **********/
            /*********************************/

            //end sec
            if(res.getFirstTime().equals("No")){
                final Button button = new Button(this);

                button.setText("Reserve");
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReservationList.this);
                        // set title
                        alertDialogBuilder.setTitle("Are you sure?");
                        // set dialog message
                        alertDialogBuilder.setMessage("Are you sure?").setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                        /*  SharedPreferences  */

                                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        String userStr = sharedPref.getString("user", "ERROR on reading from SharedPrefs");

                                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                                        User user = gson.fromJson(userStr, User.class); // des

                                        Log.d("user Id", user.id);

                                        // 2. Request to the server to reserve this date

                                        /*Create handle for the RetrofitInstance interface*/
                                        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                                        Call<List<Reservation>> call = service.reserve(res.getDate(), "05:00", user.id);
                                        call.enqueue(new Callback<List<Reservation>>() {
                                            @Override
                                            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {

                                                Log.e("Result:", response.toString());
                                                openMyReservations();                                            }

                                            @Override
                                            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                Toast.makeText(ReservationList.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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

            }else{
                final TextView text2 = new TextView(this);

                text2.setText("Reserved");

                text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                tableRow.addView(text2);
            }

            /*********************************/
            /************ ./ 05:00  **********/

            /*********************************/
            /************    07:00  **********/
            /*********************************/

            //end sec
            if(res.getSecondTime().equals("No")){
                final Button button = new Button(this);

                button.setText("Reserve");
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReservationList.this);
                        // set title
                        alertDialogBuilder.setTitle("Are you sure?");
                        // set dialog message
                        alertDialogBuilder.setMessage("Are you sure?").setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                        /*  SharedPreferences  */

                                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        String userStr = sharedPref.getString("user", "ERROR on reading from SharedPrefs");

                                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                                        User user = gson.fromJson(userStr, User.class); // des

                                        Log.d("user Id", user.id);

                                        // 2. Request to the server to reserve this date

                                        /*Create handle for the RetrofitInstance interface*/
                                        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                                        Call<List<Reservation>> call = service.reserve(res.getDate(), "07:00", user.id);
                                        call.enqueue(new Callback<List<Reservation>>() {
                                            @Override
                                            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {

                                                Log.e("Result:", response.toString());
                                                openMyReservations();                                            }

                                            @Override
                                            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                Toast.makeText(ReservationList.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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

            }else{
                final TextView text2 = new TextView(this);

                text2.setText("Reserved");

                text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                tableRow.addView(text2);
            }

            /*********************************/
            /************ ./ 07:00  **********/

            /*********************************/
            /************    09:00  **********/
            /*********************************/

            //end sec
            if(res.getThirdTime().equals("No")){
                final Button button = new Button(this);

                button.setText("Reserve");
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReservationList.this);
                        // set title
                        alertDialogBuilder.setTitle("Are you sure?");
                        // set dialog message
                        alertDialogBuilder.setMessage("Are you sure?").setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                        /*  SharedPreferences  */

                                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        String userStr = sharedPref.getString("user", "ERROR on reading from SharedPrefs");

                                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                                        User user = gson.fromJson(userStr, User.class); // des

                                        Log.d("user Id", user.id);

                                        // 2. Request to the server to reserve this date

                                        /*Create handle for the RetrofitInstance interface*/
                                        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                                        Call<List<Reservation>> call = service.reserve(res.getDate(), "09:00", user.id);
                                        call.enqueue(new Callback<List<Reservation>>() {
                                            @Override
                                            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {

                                                Log.e("Result:", response.toString());

                                                showSuccessAndOpenHome();




                                            }

                                            @Override
                                            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                                                Toast.makeText(ReservationList.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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

            }else{
                final TextView text3 = new TextView(this);

                text3.setText("Reserved");

                text3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                tableRow.addView(text3);
            }

            /*********************************/
            /************ ./ 09:00  **********/
            /*********************************/


            tableLayout.addView(tableRow);


        }

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
    }


    private void openHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    private void openMyReservations() {
        showSuccessAndOpenHome();
    }

    private void showSuccessAndOpenHome(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ReservationList.this);
        builder.setMessage("Your reservation done succufully, you will be direct to home page")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        openHome();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
