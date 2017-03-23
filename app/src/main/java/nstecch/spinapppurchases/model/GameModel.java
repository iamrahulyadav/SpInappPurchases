package nstecch.spinapppurchases.model;

/**
 * Created by webwerks on 22/3/17.
 */

public class GameModel {

    public String title;
    public String desc;
    public String price;
    public String type;
    public String gameId;

    public GameModel(String gameTitle,String desc, String gamePrice, String gameId) {
        this.title = gameTitle;
        this.price = gamePrice;
        this.gameId = gameId;
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
