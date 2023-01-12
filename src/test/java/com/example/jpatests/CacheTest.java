package com.example.jpatests;

import com.example.jpatests.domain.Member;
import com.example.jpatests.domain.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class CacheTest {
    @Autowired
    MemberRepository memberRepository;


    @BeforeEach
    public void setup(){
        Member m1 = Member.builder().id(1L).build();
        memberRepository.save(m1);
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
