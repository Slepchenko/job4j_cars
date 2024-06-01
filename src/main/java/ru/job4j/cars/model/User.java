package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "auto_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private int id;
    private String login;
    private String password;
}