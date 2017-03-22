package nstecch.spinapppurchases;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nstecch.spinapppurchases.adapter.GameRecyclerAdapter;
import nstecch.spinapppurchases.controller.OnGameCLicked;
import nstecch.spinapppurchases.model.GameModel;

public class MainActivity extends AppCompatActivity implements OnGameCLicked{

    private static final String TAG = "MainActivity";
    
    List<GameModel> gameModelList = new ArrayList<>();
    RecyclerView recyclerViewGame;
    GameRecyclerAdapter gameRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewGame = (RecyclerView) findViewById(R.id.recyclerViewGame);
        recyclerViewGame.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        String[] gameTitles = getResources().getStringArray(R.array.gameNames);
        String[] gameIds = getResources().getStringArray(R.array.gameId);

        for (int i = 0; i < gameTitles.length; i++) {
            gameModelList.add(new GameModel(gameTitles[i], "100", gameIds[i]));
        }
        gameRecyclerAdapter = new GameRecyclerAdapter(MainActivity.this, gameModelList,this);
        recyclerViewGame.setAdapter(gameRecyclerAdapter);
    }

    @Override
    public void onBuyClick(String gameName, String price) {
        
    }

    @Override
    public void onSuscribeClick(String gameName, String price) {

    }
}
