package nstecch.spinapppurchases;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import nstecch.spinapppurchases.adapter.GameRecyclerAdapter;
import nstecch.spinapppurchases.controller.OnGameCLicked;
import nstecch.spinapppurchases.model.GameModel;
import nstecch.spinapppurchases.util.BillingHelper;
import nstecch.spinapppurchases.util.BillingResult;
import nstecch.spinapppurchases.util.Inventory;
import nstecch.spinapppurchases.util.PurchaseInfo;

public class MainActivity extends AppCompatActivity implements OnGameCLicked, BillingHelper.QueryInventoryFinishedListener, BillingHelper.OnConsumeFinishedListener, BillingHelper.OnIabPurchaseFinishedListener {

    private static final String TAG = "MainActivity";


    private final String SKU_GAME1 = "sku_game1";
    final BillingHelper.QueryInventoryFinishedListener onQueryInventoryFinished = new BillingHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(BillingResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");
            if (result.isFailure()) {
                Log.d(TAG, "Failed to query inventory: " + result);

                return;
            } else {
                Log.d(TAG, "Query inventory was successful.");
                // does the user have the premium upgrade?
                PurchaseInfo goldMonthly = inventory.getPurchase(SKU_GAME1);
            }
        }
    };
    private final String SKU_GAME2 = "sku_game2";
    private final String SKU_GAME3 = "sku_game3";
    private final String SKU_GAME4 = "sku_game4";
    private final String SKU_GAME5 = "sku_game5";
    private final String SKU_GAME6 = "sku_game6";
    private final int RC_REQUEST_SKU = 1001;
    private final int RC_REQUEST_SUBSCRIPTION = 1001;

//    GET THE SUBSCRIPTION DETAILS
//    https://developers.google.com/android-publisher/api-ref/purchases/subscriptions/get
// RESPONSE IS HERE
//    https://developers.google.com/android-publisher/api-ref/purchases/subscriptions#resource


//    IN APP PURCHASES
//    https://developer.android.com/google/play/billing/billing_subscriptions.html#administering
//    https://github.com/anjlab/android-inapp-billing-v3
//    http://stackoverflow.com/questions/35127086/android-inapp-purchase-receipt-validation-google-play
//    http://stackoverflow.com/questions/10792465/how-to-implement-in-app-purchase-in-my-android-application
//    http://www.techotopia.com/index.php/An_Android_Studio_Google_Play_In-app_Billing_Tutorial
    List<GameModel> gameModelList = new ArrayList<>();
    RecyclerView recyclerViewGame;
    ProgressBar progressLoadMOre;
    GameRecyclerAdapter gameRecyclerAdapter;

    // payment gatewaye
    BillingHelper mHelper;

    BillingHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new BillingHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(BillingResult result,
                                          PurchaseInfo purchase) {
            if (result.isFailure()) {
                // Handle error
                return;
            } else if (purchase.getSku().equals(""/*ITEM_SKU*/)) {
                /*consumeItem();
                buyButton.setEnabled(false);*/
            }

        }
    };
    private String payload = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpInAPpPurchases(getResources().getString(R.string.base_key));

        recyclerViewGame = (RecyclerView) findViewById(R.id.recyclerViewGame);
        progressLoadMOre = (ProgressBar) findViewById(R.id.progressLoadMOre);
        recyclerViewGame.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        String[] gameTitles = getResources().getStringArray(R.array.gameNames);
        String[] gameIds = getResources().getStringArray(R.array.gameId);

        /*for (int i = 0; i < gameTitles.length; i++) {
            gameModelList.add(new GameModel(gameTitles[i], "desc", "100", gameIds[i]));
        }*/

        for(int i = 0;i<30;i++) {
            gameModelList.add(new GameModel("Siba "+i, "desc "+i, "100"+i, "sp"+i));
        }
        gameRecyclerAdapter = new GameRecyclerAdapter(MainActivity.this, gameModelList, this);
        recyclerViewGame.setAdapter(gameRecyclerAdapter);

        recyclerViewGame.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int childCount = recyclerView.getLayoutManager().getChildCount();
                int totalCount = recyclerView.getLayoutManager().getItemCount();
                int visiblePOsition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if((childCount+visiblePOsition)>=totalCount){

                    progressLoadMOre.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for(int j = 0;j<10;j++) {
                                gameModelList.add(new GameModel("Siba "+j, "desc "+j, "100"+j, "sp"+j));
                            }
                            progressLoadMOre.setVisibility(View.GONE);
                            gameRecyclerAdapter.notifyDataSetChanged();
                        }
                    },3000);


                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

/*    @Override
    protected void onStop() {
        super.onStop();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }*/

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBuyClick(String gameName, String price, String gameId) {
        if (mHelper.isSetupDone()) {
            mHelper.launchPurchaseFlow(this, gameId, RC_REQUEST_SKU,
                    mPurchaseFinishedListener, "mypurchasetoken");
        } else {
            Toast.makeText(MainActivity.this, "DOing Set up, Please Wait...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuscribeClick(String gameName, String price, String gameID) {
        if (gameID.equalsIgnoreCase("sp104"))
            mHelper.launchSubscriptionPurchaseFlow(MainActivity.this, gameID, RC_REQUEST_SUBSCRIPTION, this);
        else
            mHelper.launchSubscriptionPurchaseFlow(MainActivity.this, gameID, RC_REQUEST_SUBSCRIPTION, this);

    }

    private void setUpInAPpPurchases(String base64) {
        mHelper = new BillingHelper(this, base64);
        mHelper.enableDebugLogging(true, TAG);
        mHelper.startSetup(new BillingHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(BillingResult result) {

                Log.d(TAG, "Setup finished.");
                if (!result.isSuccess()) {
                    Log.i("inappp", "a:" + result.getMessage());
                    return;
                }
                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) {
                    Log.i("inappp", "b");
                    return;
                }

                try {
                    mHelper.queryInventoryAsync(onQueryInventoryFinished);
                } catch (Exception e) {
                    Log.i(TAG, "onIabSetupFinished: "+e.getMessage());
                }

            }
        });
    }

    boolean verifyDeveloperPayload(PurchaseInfo p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void consumeItem() {
        mHelper.queryInventoryAsync(this);
    }

    @Override
    public void onQueryInventoryFinished(BillingResult result, Inventory inventory) {
        if (result.isFailure()) {
            // Handle failure
        } else {
            mHelper.consumeAsync(inventory.getPurchase(/*ITEM_SKU*/""),
                    this);
        }
    }

    @Override
    public void onConsumeFinished(PurchaseInfo purchase, BillingResult result) {
        if (result.isSuccess()) {
                        /*clickButton.setEnabled(true);*/
        } else {
            // handle error
        }
    }

    @Override
    public void onIabPurchaseFinished(BillingResult result, PurchaseInfo purchase) {
        if (!result.isFailure()) {

            Log.i(TAG, "onIabPurchaseFinished: " + purchase.getOrderId() + " " + purchase.getDeveloperPayload() + " " + purchase.getSku() + " " + purchase.toString() + " " + purchase.getPurchaseTime());

            if (purchase.getOrderId().equals(purchase.getToken())) {
                if (purchase.getSku().equals(SKU_GAME5)
                        && purchase.getDeveloperPayload().equals(payload)) {
                    //Purchase successfull.

                } else if (purchase.getSku().equals(SKU_GAME6)
                        && purchase.getDeveloperPayload().equals(payload)) {


                }
            } else {
                //Purchase is not valid!
                Toast.makeText(MainActivity.this, "was not successful", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(MainActivity.this, "cancel payment operation", Toast.LENGTH_LONG).show();
        }
    }
}
