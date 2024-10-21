package org.GestionDesTournois.Models;

public class Player {
    private int id;
    private String pseudo;
    private int age;
    private Team equipe;

    public Player(int id, String pseudo, int age, Team equipe) {
        this.id = id;
        this.pseudo = pseudo;
        this.age = age;
        this.equipe = equipe;
    }

    public Player() {
    }

    public Player(String pseudo, int age, Team equipe) {
        this.pseudo = pseudo;
        this.age = age;
        this.equipe = equipe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getEquipe() {
        return equipe;
    }

    public void setEquipe(Team equipe) {
        this.equipe = equipe;
    }

    @Override
    public String toString() {
        return "player{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", age=" + age +
                ", equipe=" + equipe +
                '}';
    }
}
