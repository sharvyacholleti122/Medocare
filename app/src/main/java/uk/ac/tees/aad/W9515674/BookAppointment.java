package uk.ac.tees.aad.W9515674;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookAppointment extends AppCompatActivity {

    ListView listView;
    String heading[] ;
    String subheading[];
    String images[];
    CustomAdapter adapter;

    ArrayList<Hospital> hospitals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        heading = new String[0];
        subheading= new String[0];
        images = new String[0];

        listView = findViewById(R.id.list_view);

        hospitals = new ArrayList<Hospital>();


        adapter = new CustomAdapter(this, heading, subheading, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                Intent intent = new Intent(getApplicationContext(),Doctors.class);
                intent.putExtra("name",hospitals.get(position).getName());
                intent.putExtra("number",hospitals.get(position).getNumber());
                intent.putExtra("lat",hospitals.get(position).getLat());
                intent.putExtra("lng",hospitals.get(position).getLng());
                startActivity(intent);
            }
        });

        FirebaseDatabase.getInstance().getReference("Hospital").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot da:dataSnapshot.getChildren()) {
                   hospitals.add(da.getValue(Hospital.class));
                }

                heading = new String[hospitals.size()];
                subheading= new String[hospitals.size()];
                images = new String[hospitals.size()];

                for (int i=0;i<hospitals.size();i++)
                {
                    heading[i] = hospitals.get(i).getName();
                    subheading[i] = hospitals.get(i).getNumber();
                    images[i] = hospitals.get(i).getImage();
                }

                adapter = new CustomAdapter(getApplicationContext(), heading, subheading, images);
                listView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_LONG).show();
            }
        });




    }
}
class Hospital{
    String name;
    String number;
    String image;
    double lat;
    double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
class CustomAdapter extends ArrayAdapter<String> {

    Context context;
    String heading[];
    String subheading[];
    String images[];

    CustomAdapter (Context c, String heading[], String subheading[], String images[]) {
        super(c, R.layout.column, R.id.textView1, heading);
        this.context = c;
        this.heading = heading;
        this.subheading = subheading;
        this.images = images;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View single_view = layoutInflater.inflate(R.layout.column, parent, false);

        ImageView imageView = single_view.findViewById(R.id.image);
        TextView head = single_view.findViewById(R.id.textView1);
        TextView subHead = single_view.findViewById(R.id.textView2);

        Glide.with(getContext().getApplicationContext()).load(images[position]).into(imageView);
        head.setText(heading[position]);
        subHead.setText(subheading[position]);
        return single_view;
    }
}