package com.example.yuval_project.data;

import com.example.yuval_project.data.model.PlayerItem;
import com.example.yuval_project.data.model.TeamItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class AppData {
    // singleton
    private List<PlayerItem> playerList;
    private Map<String, List<PlayerItem>> teamMap;

    private Map<String, List<PlayerItem>> selectedTeamMap;

    private ArrayList<TeamItem> teamItemList;   // all the teams of the current uesr

    private String userId = null;

    private static AppData instance = new AppData();


    private AppData(){
        List<PlayerItem> playerList = new ArrayList<PlayerItem>();

        teamMap = new HashMap<String, List<PlayerItem>>();
        teamMap.put("Hapoel", playerList);
        selectedTeamMap = new HashMap<String, List<PlayerItem>>();
        teamItemList = new ArrayList<TeamItem>();
    }

    public ArrayList<TeamItem> getTeamItemList(){
        return this.teamItemList;
    }
    public void addTeam(TeamItem team){
        if(!teamItemList.contains(team)){
            this.teamItemList.add(team);
        }
    }

    public void removeTeam(TeamItem team){
        teamItemList.remove(team);
    }
    public TeamItem findTeamById(String id){
        for(TeamItem t : teamItemList){
            if(t.getId().equals(id)){
                return t;
            }
        }
        return null;
    }
    public static AppData getInstance(){
        return instance;
    }

    public List<PlayerItem> getPlayerList(String teamName){
        return teamMap.get(teamName);
    }

    public void addPlayer(String teamName, PlayerItem player){
        teamMap.get(teamName).add(player);
    }

    public void createTeams(List<PlayerItem> selectedPlayers, int numOfGroups){

        int gradeSum = 0;
        int average = 0;

        for(PlayerItem player : selectedPlayers){
            gradeSum += player.getGrade();
        }
        average = gradeSum / selectedPlayers.size();
        Collections.sort(selectedPlayers, (p1, p2) -> p1.getGrade() - p2.getGrade());

        selectedTeamMap.clear();
        int playersPerGroup = selectedPlayers.size() / numOfGroups;
        char groupName = 'A';
        // create groups
        List<String> teamNames = new ArrayList<String>();
        for(int i = 0; i < numOfGroups; i++){
            List<PlayerItem> group = new ArrayList<PlayerItem>();
            selectedTeamMap.put(groupName+"", group);
            teamNames.add(groupName+"");
            groupName++;
        }

        while(selectedPlayers.size() > 0){
            for(int i = 0; i < teamNames.size() && selectedPlayers.size() > 0; i++){
                String name = teamNames.get(i);
                selectedTeamMap.get(name).add(selectedPlayers.remove(selectedPlayers.size()-1));
            }
            for(int i = teamNames.size()-1; i >= 0 && selectedPlayers.size() > 0 ; i--){
                String name = teamNames.get(i);
                selectedTeamMap.get(name).add(selectedPlayers.remove(selectedPlayers.size()-1));
            }
        }


    }
    public Map<String, List<PlayerItem>> getSelectedTeamMap(){
        return selectedTeamMap;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String id){
        this.userId = id;
    }
}
