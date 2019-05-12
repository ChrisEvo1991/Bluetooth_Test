package com.example.bluetooth_test;

public class Report {
    private String Goals;
    private String Points;
    private String Score;
    private String Steps;


    public Report() {
    }

    public Report(String goals, String points, String score, String steps) {
        Goals = goals;
        Points = points;
        Score = score;
        Steps = steps;
    }

    public String getGoals() {
        return Goals;
    }

    public void setGoals(String goals) {
        Goals = goals;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getSteps() {
        return Steps;
    }

    public void setSteps(String steps) {
        Steps = steps;
    }
}
