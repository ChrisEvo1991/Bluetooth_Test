package com.example.bluetooth_test;

public class Report {
    private String name;
    private String position;
    private String score;
    private String steps;
    private float goalsfor;
    private float goalsmiss;
    private String goals_score_percentage;
    private float pointsfor;
    private float pointsmiss;
    private String points_score_percentage;
    private float handpassfor;
    private float handpassmiss;
    private String handpass_success_percentage;
    private float kickpassfor;
    private float kickpassmiss;
    private String kickpass_percentage;


    public Report(){
    }

    public Report (String name, String position, String score, String steps, float goalsfor , float goalsmiss, String goals_score_percentage, float pointsfor, float pointsmiss, String points_score_percentage, float handpassfor, float handpassmiss, String handpass_success_percentage, float kickpassfor, float kickpassmiss, String kickpass_percentage) {
        this.name = name;
        this.position = position;
        this.score = score;
        this.steps = steps;
        this.goalsfor = goalsfor;
        this.goalsmiss = goalsmiss;
        this.goals_score_percentage = goals_score_percentage;
        this.pointsfor = pointsfor;
        this.pointsmiss = pointsmiss;
        this.points_score_percentage = points_score_percentage;
        this.handpassfor = handpassfor;
        this.handpassmiss = handpassmiss;
        this.handpass_success_percentage = handpass_success_percentage;
        this.kickpassfor = kickpassfor;
        this.kickpassmiss = kickpassmiss;
        this.kickpass_percentage = kickpass_percentage;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPosition() { return position; }
    public void setPosition(String position) {
       this.position = position;
    }
    public String getScore() { return score; }
    public void setScore(String score) { this.score = score; }
    public String getSteps() {return steps;}
    public void setSteps(String steps) {
        this.steps = steps;
    }

    public float getGoalsfor() { return goalsfor;}
    public void setGoalsfor(float goalsfor) { this.goalsfor = goalsfor;}

    public float getGoalsmiss(){return goalsmiss;}
    public void setGoalsmiss(float goalsmiss) { this.goalsmiss = goalsmiss;}

    public String getGoals_percentage(){return goals_score_percentage;}
    public void setGoals_percentage(String goals_score_percentage) {this.goals_score_percentage = goals_score_percentage;}

    public float getPointsfor(){return pointsfor;}
    public void setPointsfor(float pointsfor) { this.pointsfor = pointsfor; }

    public float getPointsmiss(){return pointsmiss;}
    public void setPointsmiss(float pointsmiss) {this.pointsmiss = pointsmiss;}

    public String getPoints_percentage(){return points_score_percentage;}
    public void setPoints_percentage(String points_score_percentage) {this.points_score_percentage = points_score_percentage; }

    public float getHandpassfor(){return handpassfor;}
    public void setHandpassfor(float handpassfor) { this.handpassfor = handpassfor; }

    public float getHandpassmiss(){return handpassmiss;}
    public void setHandpassmiss(float handpassmiss) { this.handpassmiss = handpassmiss;}

    public String getHandpass_percentage(){return handpass_success_percentage;}
    public void setHandpass_percentage(String handpass_success_percentage) { this.handpass_success_percentage = handpass_success_percentage;
    }

    public float getKickpassfor(){return kickpassfor;}
    public void setKickpassfor(float kickpassfor) {this.kickpassfor = kickpassfor; }

    public float getKickpassmiss(){return kickpassmiss;}
    public void setKickpassmiss(float kickpassmiss) {this.kickpassmiss = kickpassmiss; }

    public String getKickpass_percentage(){return kickpass_percentage;}
    public void setKickpass_percentage(String kickpass_percentage) {this.kickpass_percentage = kickpass_percentage; }
}
