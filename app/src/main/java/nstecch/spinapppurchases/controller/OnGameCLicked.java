package nstecch.spinapppurchases.controller;

/**
 * Created by webwerks on 22/3/17.
 */

public interface OnGameCLicked {
    void onBuyClick(String gameName,String price,String gameId);
    void onSuscribeClick(String gameName,String price,String gameId);
}
