package nstecch.spinapppurchases;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nstecch.spinapppurchases.adapter.GameRecyclerAdapter;
import nstecch.spinapppurchases.controller.OnGameCLicked;
import nstecch.spinapppurchases.model.GameModel;
import nstecch.spinapppurchases.util.IabHelper;
import nstecch.spinapppurchases.util.IabResult;
import nstecch.spinapppurchases.util.Inventory;
import nstecch.spinapppurchases.util.Purchase;

public class MainActivity extends AppCompatActivity implements OnGameCLicked {

    private static final String TAG = "MainActivity";

//    IN APP PURCHASES
//    http://stackoverflow.com/questions/35127086/android-inapp-purchase-receipt-validation-google-play
//    http://stackoverflow.com/questions/10792465/how-to-implement-in-app-purchase-in-my-android-application
//    http://www.techotopia.com/index.php/An_Android_Studio_Google_Play_In-app_Billing_Tutorial

    List<GameModel> gameModelList = new ArrayList<>();
    RecyclerView recyclerViewGame;
    GameRecyclerAdapter gameRecyclerAdapter;

    // payment gatewaye
    IabHelper mHelper;

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase)
        {
            if (result.isFailure()) {
                // Handle error
                return;
            }
            else if (purchase.getSku().equals(""/*ITEM_SKU*/)) {
                /*consumeItem();
                buyButton.setEnabled(false);*/
            }

        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        /*clickButton.setEnabled(true);*/
                    } else {
                        // handle error
                    }
                }
            };

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
        gameRecyclerAdapter = new GameRecyclerAdapter(MainActivity.this, gameModelList, this);
        recyclerViewGame.setAdapter(gameRecyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    @Override
    public void onBuyClick(String gameName, String price,String gameId) {
        mHelper.launchPurchaseFlow(this, gameId, 10001,
                mPurchaseFinishedListener, "mypurchasetoken");
    }

    @Override
    public void onSuscribeClick(String gameName, String price,String gameID) {

    }

    private void setUpInAPpPurchases(String base64) {

        mHelper = new IabHelper(this, base64);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d(TAG, "In-app Billing setup failed: " + result);
                } else {
                    Log.d(TAG, "In-app Billing is set up OK");
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
                mHelper.consumeAsync(inventory.getPurchase(/*ITEM_SKU*/""),
                        mConsumeFinishedListener);
            }
        }
    };
}
