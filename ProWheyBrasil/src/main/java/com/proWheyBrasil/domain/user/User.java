package com.proWheyBrasil.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id@GeneratedValue(strategy = GenerationType.UUID)
    private String idUser;
    private String login;
    private String password;

}
