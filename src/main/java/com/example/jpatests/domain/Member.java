package com.example.jpatests.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    @Id
    private Long id;

    private String name;

    @JoinColumn(name="TEAM_ID")
    @ManyToOne
    private Team team;

}
