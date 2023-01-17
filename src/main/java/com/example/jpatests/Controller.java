package com.example.jpatests;


import com.example.jpatests.domain.Member;
import com.example.jpatests.domain.MemberRepository;
import com.example.jpatests.domain.Team;
import com.example.jpatests.domain.TeamRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;


@AllArgsConstructor
@RestController
public class Controller {

    private TeamRepository teamRepository;
    private MemberRepository memberRepository;


    @GetMapping("/save-test")
    public ResponseEntity<?> save(){
        Team team = Team.builder().id(1L).name("teamA").build();
        Member member=  Member.builder().id(1L).name("mklee").team(team).build();
        teamRepository.save(team);
        memberRepository.save(member);

        return ResponseEntity.ok().body(member);
    }
}
