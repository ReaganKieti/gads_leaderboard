package com.leaderboard.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.leaderboard.Models.SkillLeader;
import com.leaderboard.R;

import java.util.List;


public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillsHolder> {
    private List<SkillLeader> leaders;
    private Context context;
    public SkillAdapter(Context context, List<SkillLeader> leaders){
        this.leaders =leaders;
        this.context = context;
    }
    @NonNull
    @Override
    public SkillsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.skill_item,parent,false);
        return new SkillsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillsHolder holder, int position) {
        SkillLeader leader = leaders.get(position);
        holder.name.setText(leader.getName());
        holder.score.setText(leader.getScore()+" Skill IQ Score, ");
        holder.country.setText(leader.getCountry()+".");
        Glide.with(context)
                .load(leader.getBadgeUrl())
                .into(holder.badge);
    }

    @Override
    public int getItemCount() {
        return leaders.size();
    }

    public class SkillsHolder  extends RecyclerView.ViewHolder{
        TextView name,score,country;
        ImageView badge;
        public SkillsHolder(@NonNull View itemView) {
            super(itemView);
            name  = itemView.findViewById(R.id.skill_name);
            score  = itemView.findViewById(R.id.skill_score);
            country  = itemView.findViewById(R.id.skill_country);
            badge  = itemView.findViewById(R.id.skill_badge);
        }
    }
}