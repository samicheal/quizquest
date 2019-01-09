package model;

public class ScoreBoard {
    private String contestant;
    private int points;
    private int bonus;
    private int total;

    public ScoreBoard(String contestant, int points, int bonus, int total) {
        this.contestant = contestant;
        this.points = points;
        this.bonus = bonus;
        this.total = total;
    }

    public String getContestant() {
        return contestant;
    }

    public void setContestant(String contestant) {
        this.contestant = contestant;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
