package za.ac.cput.lostfoundapp.domain;

public class Achievement {

    private int achievement_id;
    private int user_id;
    private String achievement_name;
    private String achievement_description;
    private String status;
    private int points;

    public Achievement(int achievement_id, int user_id, String achievement_name, String achievement_description, String status, int points) {
        this.achievement_id = achievement_id;
        this.user_id = user_id;
        this.achievement_name = achievement_name;
        this.achievement_description = achievement_description;
        this.status = status;
        this.points = points;
    }

    public int getAchievement_id() {
        return achievement_id;
    }

    public void setAchievement_id(int achievement_id) {
        this.achievement_id = achievement_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAchievement_name() {
        return achievement_name;
    }

    public void setAchievement_name(String achievement_name) {
        this.achievement_name = achievement_name;
    }

    public String getAchievement_description() {
        return achievement_description;
    }

    public void setAchievement_description(String achievement_description) {
        this.achievement_description = achievement_description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}// end of class
