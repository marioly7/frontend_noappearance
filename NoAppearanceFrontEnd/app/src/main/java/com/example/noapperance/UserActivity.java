package com.example.noapperance;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class UserActivity extends AppCompatActivity {
    private TextView textViewResult;
    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        textViewResult= findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.12:8080/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);
        Call<List<UserRequest>> call = userApi.getPosts();
        call.enqueue(new Callback<List<UserRequest>>() {
            @Override
            public void onResponse(Call<List<UserRequest>> call, Response<List<UserRequest>> response) {
                if(!response.isSuccessful()) {
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }
                List<UserRequest> posts = response.body();
                for (UserRequest post: posts){
                    String content ="";
                    content += "ID "+post.getUserId() + "\n";
                    content += "Account Type "+post.getAccountType() + "\n";
                    content += "User Type "+post.getUserType() + "\n";
                    content += "Name "+post.getName() + "\n";
                    content += "Surname "+post.getSurname() + "\n";
                    content += "Birthdate "+post.getBirthdate() + "\n";
                    content += "Gender "+post.getGender() + "\n";
                    content += "Email "+post.getEmail() + "\n";
                    content += "Password "+post.getPassword() + "\n";
                    content += "Photo "+post.getUserPhoto() + "\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<UserRequest >> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

}