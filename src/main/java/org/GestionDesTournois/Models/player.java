package org.GestionDesTournois.Models;

public class player {
    private int id;
    private String pseudo;
    private int age;
    private team equipe;

    public player(int id, String pseudo, int age, team equipe) {
        this.id = id;
        this.pseudo = pseudo;
        this.age = age;
        this.equipe = equipe;
    }

    public player() {
    }

    public player(String pseudo, int age, team equipe) {
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

    public team getEquipe() {
        return equipe;
    }

    public void setEquipe(team equipe) {
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
