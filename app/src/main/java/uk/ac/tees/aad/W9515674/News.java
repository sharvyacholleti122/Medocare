package uk.ac.tees.aad.W9515674;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

public class News extends AppCompatActivity {


    ListView listView;
    String heading[] ;
    String subheading[];
    String images[];
    String newsContent[];
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        listView = findViewById(R.id.list_view);

        getNews2();


    }

    public void getNews2()
    {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("https://newsapi.org/v2/top-headlines?category=health&apiKey=24e86313edac41ceb2458efe08214403").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                final String responseData = response.body().string();


                News.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JsonObject jsonObject = new JsonParser().parse(responseData).getAsJsonObject();

                        heading=new String[jsonObject.get("articles").getAsJsonArray().size()];
                        subheading = new String[jsonObject.get("articles").getAsJsonArray().size()];
                        images = new String[jsonObject.get("articles").getAsJsonArray().size()];
                        newsContent = new String[jsonObject.get("articles").getAsJsonArray().size()];
                        for( int i=0;i<jsonObject.get("articles").getAsJsonArray().size();i++ )
                        {
                            if(i<200){
                                try {
                                    JsonElement loop = jsonObject
                                            .get("articles")
                                            .getAsJsonArray()
                                            .get(i);
                                    heading[i] = loop.getAsJsonObject()
                                            .get("title")
                                            .getAsString();
                                    subheading[i] = loop.
                                            getAsJsonObject()
                                            .get("description")
                                            .getAsString();
                                    images[i] = loop.getAsJsonObject()
                                            .get("urlToImage")
                                            .getAsString();
                                    newsContent[i] = loop.getAsJsonObject()
                                            .get("source").getAsJsonObject()
                                            .get("name").getAsString();

                                }catch (Exception e){
                                    Toast.makeText(getApplicationContext(),"Unable to process news",Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), heading,  subheading,images);

                        listView.setAdapter(adapter);

                    }
                });
            }
        });
    }


}



