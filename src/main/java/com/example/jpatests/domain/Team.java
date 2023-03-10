package com.example.jpatests.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {
    @Id
    private Long id;

    private String name;

    @OneToMany
    private List<Member> members;
}
