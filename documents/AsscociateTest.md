## 연관관계 매핑과 관련된 실험 정리

1. OneToMany 단방향인 경우 테이블이 어떻게 생성될까?
Team: OneToMany로 members가짐.
Member: 연관필드 없음. 
실험결과: 
```shell
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
### Pending
1. 영속상태가 아닌 엔티티는 연관관계 설정할 때 무슨일이 벌어지는가?

=> 엔티티를 영속하지 않고 연관관계 설정 후 저장해도 아무런 일도 벌어지지 않았다.
em.persist, repository.save 둘 다 실험해봤다. 책이 잘못된걸까...? 그냥 영속 -> 연관관계 설정을 권장한다는 말이었을까.  