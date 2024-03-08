package com.example.crudfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<City> list = new ArrayList<>();
    private CityAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);
        db = FirebaseFirestore.getInstance();

        RecyclerView rcv = findViewById(R.id.rcvuser);

        FloatingActionButton fltadd = findViewById(R.id.fltthem);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(layoutManager);

        GridLayoutManager layoutManager1 = new GridLayoutManager(this, 1);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(layoutManager1);

        docdulieu();
        adapter = new CityAdapter(MainActivity.this,list);

        rcv.setAdapter(adapter);



        fltadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themcity();
            }
        });




    }

    public void themcity() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //tao view va gan gan layout
        LayoutInflater inflater = getLayoutInflater();
        View view2 = inflater.inflate(R.layout.themuser, null);
        builder.setView(view2); // gan view vao hop thoai
        Dialog dialog = builder.create();
        dialog.show();// show hop thoai
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtname = view2.findViewById(R.id.themname);
        EditText edtstate = view2.findViewById(R.id.themstate);
        EditText edtcoutry = view2.findViewById(R.id.themcountry);
        EditText edtcaptial = view2.findViewById(R.id.themcaptial);
        EditText edtpop = view2.findViewById(R.id.thempopulation);
        EditText edtregion = view2.findViewById(R.id.themregions);
        Button btnthem = view2.findViewById(R.id.thembtn);

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> city = new HashMap<>();
                city.put("name", edtname.getText().toString());
                city.put("state", edtstate.getText().toString());
                city.put("country", edtcoutry.getText().toString());
                city.put("capital", edtcaptial.getText().toString());
                city.put("population", edtpop.getText().toString());
                city.put("regions", edtregion.getText().toString());


                db.collection("city")
                        .add(city)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("checkk2", "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(MainActivity.this, "them thanh cong", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("checkk2", "Error adding document", e);
                                Toast.makeText(MainActivity.this, "them that bai", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
            }
        });

    }

    private void docdulieu () {
        db.collection("city").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange dc: value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED ){
                        list.add(dc.getDocument().toObject(City.class));
                    }
                    adapter.notifyDataSetChanged();

                }
                Log.d("checkkq", "onEvent: " +list.size());

            }
        });
    }

}