package nstecch.spinapppurchases.adapter;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import nstecch.spinapppurchases.R;
import nstecch.spinapppurchases.controller.OnGameCLicked;
import nstecch.spinapppurchases.model.GameModel;

public class GameRecyclerAdapter extends RecyclerView.Adapter<GameRecyclerAdapter.GameViewHolder> {

    private String TAG = "GameRecyclerAdapter";
    private Context context;
    private List<GameModel> listGames;
    OnGameCLicked onGameCLicked;

    Integer[] images = {R.drawable.game1,R.drawable.game2,R.drawable.game3,R.drawable.game4,R.drawable.game5};

    public GameRecyclerAdapter(Context context,
                               List<GameModel> games, OnGameCLicked onGameCLicked) {
        this.listGames = games;
        this.context = context;
        this.onGameCLicked = onGameCLicked;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.itemview_games, null);
        GameViewHolder rcv = new GameViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, final int position) {
        final GameModel gameModel = listGames.get(position);
        holder.textViewGameTitle.setText(gameModel.gameTitle);
        holder.textViewGamePrice.setText(gameModel.gamePrice);
        holder.imageViewGame.setImageResource(images[position]);
        holder.buttonBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGameCLicked.onBuyClick(gameModel.gameTitle,gameModel.getGamePrice());
            }
        });
        holder.buttonSubscribeNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGameCLicked.onSuscribeClick(gameModel.gameTitle,gameModel.getGamePrice());
            }
        });
    }

    @Override
    public int getItemCount() {
         return listGames.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
    ConstraintLayout itemviewRootLayout;
        AppCompatImageView imageViewGame;
        AppCompatTextView textViewGameTitle;
        AppCompatTextView textViewGamePrice;
        AppCompatButton buttonBuyNow;
        AppCompatButton buttonSubscribeNow;
        public GameViewHolder(View itemView) {
            super(itemView);
            itemviewRootLayout = (ConstraintLayout) itemView.findViewById(R.id.itemviewRootLayout);
            imageViewGame = (AppCompatImageView) itemView.findViewById(R.id.imageViewGame);
            textViewGameTitle = (AppCompatTextView) itemView.findViewById(R.id.textViewGameTitle);
            textViewGamePrice = (AppCompatTextView) itemView.findViewById(R.id.textViewGamePrice);
            buttonBuyNow = (AppCompatButton) itemView.findViewById(R.id.buttonBuyNow);
            buttonSubscribeNow = (AppCompatButton) itemView.findViewById(R.id.buttonSubscribeNow);
        }
    }
}
