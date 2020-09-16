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

import com.leaderboard.Adapters.LeadersAdapter;
import com.leaderboard.Data.ApiUtil;
import com.leaderboard.Models.LearningLeader;

import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LearnerFragment extends Fragment {

    private RecyclerView leadersList;
    public static LearnerFragment getInstance(){
        return new LearnerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learner, container, false);
        leadersList = view.findViewById(R.id.rv_learner);

        loadLearningLeaders();
        return view;
    }



    private void loadLearningLeaders(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gadsapi.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtil apiUtil = retrofit.create(ApiUtil.class);
        Call<List<LearningLeader>> call = apiUtil.getLearningLeader();


        call.enqueue(new Callback<List<LearningLeader>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<LearningLeader>> call, Response<List<LearningLeader>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "Fail to load Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<LearningLeader> learningLeaders = response.body();
                assert learningLeaders != null;
                learningLeaders.sort(Comparator.comparing(LearningLeader::getHours).reversed());

                LeadersAdapter leadersAdapter = new LeadersAdapter(getContext(),learningLeaders);
                leadersList.setAdapter(leadersAdapter);
                leadersList.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onFailure(Call<List<LearningLeader>> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}