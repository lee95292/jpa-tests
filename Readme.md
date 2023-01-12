
# 실험내용

1. 키 생성 중 IDENTITY방식의 과정이다.
em.persist(member) → insert query 날리고 → select query(id 포함된 레코드) → 영속성 컨텍스트에 저장
그렇다면, em.persist까지 통신이 두 번 일어나는지 확인해보자.


2. 일대다 ,다대일에서 "일" 이 연관관계의 주인이 되는 경우