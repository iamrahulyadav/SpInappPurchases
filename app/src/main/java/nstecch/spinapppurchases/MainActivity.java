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

//    IN APP PURCHASES
//    http://stackoverflow.com/questions/35127086/android-inapp-purchase-receipt-validation-google-play
//    http://stackoverflow.com/questions/10792465/how-to-implement-in-app-purchase-in-my-android-application
//    http://www.techotopia.com/index.php/An_Android_Studio_Google_Play_In-app_Billing_Tutorial
    
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
