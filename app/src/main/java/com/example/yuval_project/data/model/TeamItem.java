package com.example.yuval_project.data.model;
import java.util.ArrayList;

public class TeamItem {
    private String name;
    private ArrayList<PlayerItem> playerList;
    private String id;

    public TeamItem(String name, String id) {
        this.name = name;
        this.id = id;
        this.playerList = new ArrayList<PlayerItem>();
    }
    public TeamItem(String name) {
        this.name = name;
        this.id = "";
        this.playerList = new ArrayList<PlayerItem>();
    }
    public void addPlayer(PlayerItem player){
        this.playerList.add(player);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String getId(){
        return this.id;
    }
    private void setId(String id){
        this.id = id;
    }

    public ArrayList<PlayerItem> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<PlayerItem> playerList) {
        this.playerList = playerList;
    }
}
