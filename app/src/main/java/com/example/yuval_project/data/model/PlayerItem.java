package com.example.yuval_project.data.model;

import java.util.Objects;

public class PlayerItem {
    private String name;
    private int grade;

    public PlayerItem(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }
    public PlayerItem(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object obj){
        if( !(obj instanceof PlayerItem)) return false;
        PlayerItem other = (PlayerItem) obj;
        return this.name.equals(other.name) && this.grade == other.grade;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.name, this.grade);
    }
}
