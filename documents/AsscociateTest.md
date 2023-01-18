# 연관관계 매핑과 관련된 실험 정리

### OneToMany 단방향인 경우 JoinTable과 JoinColumn 각각 테이블이 어떻게 생성될까?

1.1 [실험설정커밋, JoinTable 버전](https://github.com/lee95292/jpa-tests/commit/d66afa18545a51f72c1d52c82440e1b449c86a2e)
<details>
<summary> 엔티티 코드 </summary>

```
@Entity
public class Team {
    @Id
    private Long id;

    private String name;

    @OneToMany
    private List<Member> members;
}
```

```
@Entity
public class Member {
    @Id
    private Long id;

    private String name;


}
```
</details>
아래와 같이 설정 후 애플리케이션 실행.  
Team: OneToMany로 members가짐.   
Member: 연관필드 없음.

JoinColumn옵션이 없으므로, JoinTable을 생성할것임.  
실험 결과   
1. team_members: 아이디 저장하는 조인테이블 생성  
2. team_members의 member_id에 unique 제약조건 생성  
(멤버는 하나의 팀만 가질 수 있으므로, Many쪽에 unique 제약조건 생성)  
3. member_id와 team_id가 각각 member, team 테이블을 참조하도록 외래키 제약조건 생성 

```
create table team_members (
       team_id bigint not null,
        members_id bigint not null
    )
Hibernate: 
    
    alter table if exists team_members 
       add constraint UK_4adet9n1mfa8wy3wbe963bt36 unique (members_id)
Hibernate: 
    
    alter table if exists team_members 
       add constraint FK3r7v8r6wqilpmx3ck9jigpwhm 
       foreign key (members_id) 
       references member
Hibernate: 
    
    alter table if exists team_members 
       add constraint FKb3toat7ors5scfmd3n69dhmr1 
       foreign key (team_id) 
       references team
```

1.2 [실험설정 커밋, JoinColumn버전](https://github.com/lee95292/jpa-tests/commit/5bc2ebe54a672ee4c708b964120af32d42a921a6)
<details>
<summary> 엔티티 코드 </summary>

```
@Entity
public class Team {
    @Id
    private Long id;

    private String name;

    @OneToMany @JoinColumn(name="team_id")
    private List<Member> members;
}
```

```
@Entity
public class Member {
    @Id
    private Long id;

    private String name;


}
```
</details>
아래와 같이 설정 후 애플리케이션 실행.  
Team: OneToMany로 members가짐.   
Member: 연관필드 없음. 

실험결과

1. member 엔티티에 team_id 컬럼이 생긴다 ( 엔티티에는 넣지 않은)   
2. member에 외래 키 제약조건을 추가한다.
   (JoinColumn은 외래키를 매핑해주는 역할을 하며, 자동으로 Many쪽에 외래키를 생성해준다.)


```
    create table member (
       id bigint not null,
        name varchar(255),
        team_id bigint,
        primary key (id)
    )
Hibernate: 
    
    create table team (
       id bigint not null,
        name varchar(255),
        primary key (id)
    )
Hibernate: 
    
    alter table if exists member 
       add constraint FKcjte2jn9pvo9ud2hyfgwcja0k 
       foreign key (team_id) 
       references team
```

### Pending
1. 영속상태가 아닌 엔티티는 연관관계 설정할 때 무슨일이 벌어지는가?

=> 엔티티를 영속하지 않고 연관관계 설정 후 저장해도 아무런 일도 벌어지지 않았다.
em.persist, repository.save 둘 다 실험해봤다. 책이 잘못된걸까...? 그냥 영속 -> 연관관계 설정을 권장한다는 말이었을까.  