package com.example.noapperance;

import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private TextView textViewResult;
    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        textViewResult= findViewById(R.id.text_view_result);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                //.baseUrl("https://192.168.31.148:8080/")
                .baseUrl("http://192.168.31.148:8081/v1/chat/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        ChatApi chatApi= retrofit.create(ChatApi.class);

        Call<List<ChatRequest>> call = chatApi.getChats();

        call.enqueue(new Callback<List<ChatRequest>>() {

            @Override
            public void onResponse(Call<List<ChatRequest>> call, Response<List<ChatRequest>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<ChatRequest> chats = response.body();
                for (ChatRequest chat : chats) {
                    String content = "";
                    content += chat.getUser2UserName() + "\n";
                    content += chat.getContentChat() + "\n";
                    content += chat.getDateChat() + "\n\n\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<ChatRequest>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

}