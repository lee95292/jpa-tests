package com.example.jpatests;

import com.example.jpatests.domain.Member;
import com.example.jpatests.domain.MemberRepository;
import com.example.jpatests.domain.Team;
import com.example.jpatests.domain.TeamRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class CacheTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;


    /*
    테스트코드에서 기본적으로 제공하는 Rollback기능을 비활성화해 실제 쿼리 동작 확인 후 데이터베이스를 비워줍니다.
     */

    @Rollback(false)
    @BeforeEach
    public void setup(){
    }

    @AfterAll
    public void cleanAll(){
        memberRepository.deleteAll();
        teamRepository.deleteAll();
    }

    /*
    * 하나의 스레드에서 find-flush-find, 일차캐시를 사용할까?
    * JPA는 1차 조회 이후 엔티티를 영속성 매니저에 저장한다.
    * */
    @Test
    @DisplayName("하나의 스레드에서 find-flush-find 했을 때, 일차캐시를 사용할까?")
    public void testCache(){
        memberRepository.findById(1L);
        memberRepository.flush();
        memberRepository.findById(1L);
    }
}
