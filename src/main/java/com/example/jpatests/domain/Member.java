package com.example.jpatests.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    @Id
    private Long id;

    private String name;

    @ManyToOne
    private Team team;

    public void setTeam(Team team){
        this.team = team;
    }

}
