package com.example.jpatests;

import com.example.jpatests.domain.Member;
import com.example.jpatests.domain.MemberRepository;
import com.example.jpatests.domain.Team;
import com.example.jpatests.domain.TeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class AssotiationTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Rollback(false)
    @BeforeEach
    public void setup(){
    }

    @AfterEach
    public void cleanAll(){
        memberRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    @DisplayName("영속 엔티티의 연관관계에 영속되지 않은 엔티티를 저장하면 어떻게될까?[비교, 정상동작]")
    public void saveNotPersitedCompare(){
        Member member= Member.builder().id(1L).name("mklee").build();
        memberRepository.save(member);

        Team team = Team.builder().id(1L).name("name").build();

        //[ 연관관계 설정 및 영속성 컨텍스트에 올바르게 저장 ]
        teamRepository.save(team);
        member.setTeam(team);

        //트랜잭션 종료, 변경감지 및 쓰기지연을 통한 update쿼리 전달
        //영속성 컨텍스트를 비우고, 새로 맴버 객체 불러옴
        memberRepository.flush();
        teamRepository.flush();
        em.clear();

        //멤버 조회 후, 연관관계 확인
        member = memberRepository.findById(1L).orElseThrow();
        team = member.getTeam();
        Assert.notNull(team, "연관관계 존재");
    }

    @Test
    @DisplayName("영속 엔티티의 연관관계에 영속되지 않은 엔티티를 저장하면 어떻게될까?[실험]")
    public void saveNotPersitedTest(){
        Member member= Member.builder().id(1L).name("mklee").build();
        memberRepository.save(member);

        Team team = Team.builder().id(1L).name("name").build();

        // [ 멤버와의 연관관계 설정 후 엔티티 정보 등록 ]
        member.setTeam(team);
        teamRepository.save(team);

        //변경감지 및 쓰기지연을 통한 update쿼리 전달
        memberRepository.flush();

        //영속성 컨텍스트를 비우고, 새로 멤버 객체 불러옴
        em.clear();
        member = memberRepository.findById(1L).orElseThrow();
        Assert.notNull(member.getTeam(), "연관관계 존재");
    }

    @Test
    @DisplayName("영속 엔티티의 연관관계에 영속되지 않은 엔티티를 저장하면 어떻게될까?[실험]")
    public void saveNotPersitedTest2(){
        Member member= Member.builder().id(1L).name("mklee").build();
        memberRepository.save(member);

        Team team = Team.builder().id(1L).name("name").build();


        // [ 연관 엔티티를 영속성 컨텍스트에 저장하지 않음 ]
        member.setTeam(team);

        //트랜잭션 종료, 변경감지 및 쓰기지연을 통한 update쿼리 전달
        memberRepository.flush();

        //영속성 컨텍스트를 비우고, 새로 멤버 객체 불러옴
        em.clear();
        Assert.notNull(member.getTeam(), "연관관계 존재");
    }


}
