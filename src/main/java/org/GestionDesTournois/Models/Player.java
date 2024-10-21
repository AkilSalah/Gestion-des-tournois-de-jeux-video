package org.GestionDesTournois.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Le pseudo ne peut pas être nul.")
    @Size(min = 1, max = 50, message = "Le pseudo doit contenir entre 1 et 50 caractères.")
    private String pseudo;

    @Min(value = 10, message = "L'âge minimum est de 10 ans.")
    @Max(value = 100, message = "L'âge maximum est de 100 ans.")
    private int age;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
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
