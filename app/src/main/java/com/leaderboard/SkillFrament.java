package com.leaderboard;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leaderboard.Adapters.SkillAdapter;
import com.leaderboard.Data.ApiUtil;
import com.leaderboard.Models.SkillLeader;

import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SkillFrament extends Fragment {

    private RecyclerView skillList;
    public static SkillFrament getInstance(){
        return new SkillFrament();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skill, container, false);
        skillList = view.findViewById(R.id.rv_skills);

        loadSkillLeaders();
        return view;
    }

    public void loadSkillLeaders() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gadsapi.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtil apiUtil = retrofit.create(ApiUtil.class);
        Call<List<SkillLeader>> call = apiUtil.getSkillLeader();


        call.enqueue(new Callback<List<SkillLeader>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<SkillLeader>> call, Response<List<SkillLeader>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "Fail to load Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<SkillLeader> skillLeaders = response.body();
                skillLeaders.sort(Comparator.comparing(SkillLeader::getScore).reversed());

                SkillAdapter skillAdapter = new SkillAdapter(getContext(),skillLeaders);
                skillList.setAdapter(skillAdapter);
                skillList.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onFailure(Call<List<SkillLeader>> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}