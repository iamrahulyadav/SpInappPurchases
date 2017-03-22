package nstecch.spinapppurchases.model;

/**
 * Created by webwerks on 22/3/17.
 */

public class GameModel {


    public String gameTitle;
    public String gamePrice;
    public String gameId;

    public GameModel(String gameTitle, String gamePrice, String gameId) {
        this.gameTitle = gameTitle;
        this.gamePrice = gamePrice;
        this.gameId = gameId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(String gamePrice) {
        this.gamePrice = gamePrice;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
