package com.mytax.mytaxpayer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mytax.mytaxpayer.R;
import com.mytax.mytaxpayer.model.Users;
import com.mytax.mytaxpayer.utils.AdapterItems;
import com.mytax.mytaxpayer.utils.FirebaseMethods;

import java.util.ArrayList;
import java.util.EventListener;

public class HomeActivity extends AppCompatActivity {
  FirebaseMethods firebaseMethods;
    private ArrayList<AdapterItems> adapterItems;
    private CustomAdapter customAdapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private String TAG="DashBaard";
    private int safaricom = R.drawable.safaricom;
    private int telkom =R.drawable.telkom;
    private int airtel =R.drawable.airtel;
    private TextView tvUserName;
    private FirebaseDatabase myDataBase;
    private DatabaseReference myRefDataBase;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initFirebase();
        initDrawerLayout();
        initGridView();
        initWidgets();
        setProfile();

    }

    private void setProfile()
    {
          try {
              firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
              myRefDataBase= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
              myRefDataBase.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                      Users users=dataSnapshot.getValue(Users.class);
                      tvUserName.setText(users.getName());

                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError databaseError) {

                  }
              });
          }
       catch (Exception e)
       {

       }



    }


    private void initFirebase()
    {
        firebaseMethods=new FirebaseMethods(this);
        firebaseMethods.initFirbase();
        mAuth=FirebaseAuth.getInstance();
        myDataBase=FirebaseDatabase.getInstance();






    }

    private void initWidgets() {
        tvUserName = (TextView) findViewById(R.id.user_name);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        firebaseMethods.onChangeState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseMethods.clearState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void initDrawerLayout()
    {
        drawerLayout=(DrawerLayout)findViewById(R.id.drawable2);
        toggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initGridView()
    {
        GridView gridViewItems=(GridView)findViewById(R.id.grid_view_content);

        adapterItems=new ArrayList<AdapterItems>();
        adapterItems.add(new AdapterItems(R.drawable.safaricom));
        adapterItems.add(new AdapterItems(R.drawable.telkom));
        adapterItems.add(new AdapterItems(R.drawable.airtel));
        customAdapter=new CustomAdapter(adapterItems);
        gridViewItems.setAdapter(customAdapter);


    }



    private class CustomAdapter extends BaseAdapter
    {
        public ArrayList<AdapterItems> adapterItems=new ArrayList<AdapterItems>();

        public CustomAdapter(ArrayList<AdapterItems> adapterItems)
        {
            this.adapterItems=adapterItems;
        }


        @Override
        public int getCount() {
            return adapterItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            final AdapterItems items=adapterItems.get(position);

            LayoutInflater inflater=getLayoutInflater();
            View view1=(View)inflater.inflate(R.layout.list_items,null);
            final ImageView imageView=view1.findViewById(R.id.items);
            imageView.setImageResource(items.getID());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(items.getID()== safaricom)
                    {
                        int id=1;
                        nextActivity(id);

                    }
                    else if(items.getID()== telkom)
                    {
                        int id=2;
                        nextActivity(id);

                    }
                    else if(items.getID()== airtel) {
                        int id = 3;
                        nextActivity(id);

                    }


                }
            });



            return view1;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void nextActivity(int i)
    {
        switch (i)
        {

        }
    }
}
