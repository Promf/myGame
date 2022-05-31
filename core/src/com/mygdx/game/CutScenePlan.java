package com.mygdx.game;

public class CutScenePlan {
    private String[] plan;
    int i = -1;
    public void next(){
        if (i<plan.length-1){
            i++;
        }
    }
    public String getString(){
        return plan[i];
    }
}
