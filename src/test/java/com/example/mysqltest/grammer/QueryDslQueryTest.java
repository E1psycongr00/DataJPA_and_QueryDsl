package com.example.mysqltest.grammer;

import com.example.mysqltest.config.TestQueryDslConfig;
import com.example.mysqltest.dto.UserDto;
import com.example.mysqltest.dto.UserDto2;
import com.example.mysqltest.entity.QOrders;
import com.example.mysqltest.entity.QPlayer;
import com.example.mysqltest.entity.QUser;
import com.example.mysqltest.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(TestQueryDslConfig.class)
@Sql("/createEntity.sql")
public class QueryDslQueryTest {
    
    @Autowired
    JPAQueryFactory queryFactory;
    
    QUser user = QUser.user;
    
    @Test
    @DisplayName("기본적인 검색 쿼리")
    void commonTest() {
        List<User> result = queryFactory.select(user).from(user).where(user.password.eq("126").and(user.age.lt(25))).fetch(); // return List form
        result.forEach(u -> System.out.println(u.getNickname() + " " + u.getAge()));
    }
    
    @Test
    @DisplayName("토탈 카운트 반환")
    void totalCountTest() {
        long result = queryFactory
                .selectFrom(user)
                .fetchCount();
        
        System.out.println(result);
    }
    
    @Test
    @DisplayName("집합 함수 테스트")
    void aggregationTest() {
        List<Tuple> result = queryFactory
                .select(
                        user.count(),
                        user.age.sum(),
                        user.age.avg(),
                        user.age.max(),
                        user.age.min()
                )
                .from(user)
                .fetch();
        Tuple tuple = result.get(0);
        System.out.println(tuple.get(user.count()));
        System.out.println(tuple.get(user.age.sum()));
        System.out.println(tuple.get(user.age.avg()));
        System.out.println(tuple.get(user.age.max()));
        System.out.println(tuple.get(user.age.min()));
    }
    
    @Test
    @DisplayName("상수 더하기")
    void constantTest() {
        List<Tuple> result = queryFactory
                .select(user.nickname, Expressions.constant("A"))
                .from(user)
                .fetch();
        
        System.out.println(result);
    }
    
    @Test
    @DisplayName("문자 더하기")
    void concatTest() {
        List<String> result = queryFactory
                .select(user.nickname.concat("_").concat(user.age.stringValue()))
                .from(user)
                .fetch();
        
        System.out.println(result);
    }
    
    @Nested
    @DisplayName("fetch() 테스트")
    class fetchTest {
        
        @Test
        @DisplayName("fetch 1건 이상 테스트")
        void successTest() {
            List<User> result = queryFactory.selectFrom(user).fetch();
            
            System.out.println(result.size());
            assertThat(result.size()).isGreaterThan(0);
        }
        
        @Test
        @DisplayName("fetch 0건 테스트")
        void failTest() {
            List<User> result = queryFactory.selectFrom(user).where(user.nickname.eq("adsfasdjflasjdfj")).fetch();
            
            System.out.println(result.size());
            assertThat(result).isNotNull();
            assertThat(result.size()).isEqualTo(0);
        }
    }
    
    @Nested
    @DisplayName("fetchOne() 테스트")
    class FetchOneTest {
        
        @Test
        @DisplayName("fetchOne 1개 테스트")
        void answerSizeOneTest() {
            User result = queryFactory.selectFrom(user).where(user.nickname.eq("mina")).fetchOne();
            
            assertThat(result).isNotNull();
        }
        
        @Test
        @DisplayName("fetchOne 0개 테스트")
        void answerSizeZeroTest() {
            User result = queryFactory.selectFrom(user).where(user.nickname.eq("mina1")).fetchOne();
            
            assertThat(result).isNull();
        }
        
        @Test()
        @DisplayName("[예외]fetchOne 2개 이상 테스트")
        void answerSizeTwoMoreTest() {
            assertThatThrownBy(() -> {
                User result = queryFactory.selectFrom(user).where(user.age.lt(30)).fetchOne();
            }).isInstanceOf(NonUniqueResultException.class);
            System.out.println("예외: com.qeurydsl.core.NonUniqueResultException");
        }
    }
    
    @Nested
    @DisplayName("fetchResults 페이징 테스트")
    class fetchResultsTest {
        
        @Test
        @DisplayName("페이징 조회")
        void successTest() {
            QueryResults<User> result = queryFactory
                    .selectFrom(user)
                    .orderBy(user.nickname.desc())
                    .offset(0)
                    .limit(4).fetchResults();
            
            System.out.println(result.getLimit());
            System.out.println(result.getOffset());
            System.out.println(result.getTotal());
            for (User resultResult : result.getResults()) {
                System.out.println(resultResult.getNickname());
            }
        }
    }
    
    @Nested
    @DisplayName("정렬 테스트")
    class SortTest {
        
        @Test
        @DisplayName("오름차순 테스트")
        void ascTest() {

            List<User> result = queryFactory
                    .selectFrom(user)
                    .orderBy(user.nickname.asc())
                    .fetch();
            
            result.forEach((u) -> {
                System.out.println(u.getNickname());
            });
        }
        
        @Test
        @DisplayName("내림차순 테스트")
        void descTest() {
            List<User> result = queryFactory
                    .selectFrom(user)
                    .orderBy(user.nickname.desc())
                    .fetch();
            
            result.forEach((u) -> {
                System.out.println(u.getNickname());
            });
        }
    }
    
    @Nested
    @DisplayName("GroupBy 테스트")
    class GroupByTest {
        
        @Test
        @DisplayName("GroupBy만 테스트")
        void groupByTest() {
            QOrders orders = QOrders.orders;
            
            List<Tuple> result = queryFactory
                    .select(orders.item, user.age.avg())
                    .from(orders)
                    .join(orders.user, user)
                    .groupBy(orders.item)
                    .fetch();
            
            System.out.println(result);
        }
        
        @Test
        @DisplayName("GroupBy + having 테스트")
        void groupByUsingHavingTest() {
            QOrders orders = QOrders.orders;
            
            List<Tuple> result = queryFactory
                    .select(orders.item, user.age.avg())
                    .from(orders)
                    .join(orders.user, user)
                    .groupBy(orders.item)
                    .having(orders.item.contains("s"))
                    .fetch();
            
            System.out.println(result);
        }
    }
    
    @Nested
    @DisplayName("Join 테스트")
    class JoinTest {
        
        @Test
        @DisplayName("세타 조인")
        void sethaJoinTest() {
            QPlayer player = QPlayer.player;
            
            List<Tuple> result = queryFactory
                    .select(user, player)
                    .from(user, player)
                    .fetch();
            System.out.println(result.size());
        }
        
        @Test
        @DisplayName("세타 조인 where 사용")
        void sethaUsingWhereJoinTest() {
            QPlayer player = QPlayer.player;
            
            List<Tuple> result = queryFactory
                    .select(user, player)
                    .from(user, player)
                    .where(user.age.lt(25).and(player.age.lt(25)))
                    .fetch();
            System.out.println(result.size());
        }
        
        @Test
        @DisplayName("세타 조인 on 사용")
        void sethaUsingOnJoinTest() {
            QPlayer player = QPlayer.player;
            
            List<Tuple> result = queryFactory
                    .select(user, player)
                    .from(user)
                    .leftJoin(player).on(user.age.lt(player.age))
                    .fetch();
            System.out.println(result.size());
        }
    }
    
    @Nested
    @DisplayName("서브 쿼리 테스트")
    class SubQueryTest {
        
        @Test
        @DisplayName("서브쿼리 예제1")
        void subQueryTest1() {
            QUser userSub = new QUser("userSub");
            
            List<User> result = queryFactory
                    .selectFrom(user)
                    .where(user.age.goe(
                            JPAExpressions
                                    .select(userSub.age.avg())
                                    .from(userSub)
                    ))
                    .orderBy(user.age.asc())
                    .fetch();
            
            result.forEach(u -> {
                System.out.println(u.getNickname() + " " + u.getAge());
            });
            
        }
    }
    
    @Nested
    @DisplayName("Case 식 테스트")
    class CaseTest {
        
        @Test
        @DisplayName("when, then 테스트")
        void whenThenTest() {
            List<String> result = queryFactory
                    .select(user.age
                            .when(10).then("열살")
                            .when(20).then("스무살")
                            .otherwise("기타"))
                    .from(user)
                    .fetch();
            System.out.println(result);
        }
        
        @Test
        @DisplayName("CaseBuilder 테스트")
        void caseBuilderTest() {
            List<String> result = queryFactory
                    .select(new CaseBuilder()
                            .when(user.age.between(10, 20)).then("청소년")
                            .when(user.age.between(20, 30)).then("성인")
                            .otherwise("기타"))
                    .from(user)
                    .fetch();
            System.out.println(result);
        }
    }
    
    @Nested
    @DisplayName("프로젝션 테스트")
    class ProjectTest {
        
        @Test
        @DisplayName("단일 프로젝션 테스트")
        void singleProjectionTest() {
            List<String> result = queryFactory
                    .select(user.nickname)
                    .from(user)
                    .fetch();
            
            System.out.println(result);
        }
        
        @Test
        @DisplayName("Tuple를 이용한 프로젝션 테스트")
        void tupleProjectionTest() {
            List<Tuple> result = queryFactory
                    .select(user.nickname, user.age)
                    .from(user)
                    .fetch();
            
            System.out.println(result);
        }
        
        @Nested
        @DisplayName("Dto를 활용한 프로젝션 테스트")
        class DtoProjectionTest {
            
            @Test
            @DisplayName("필드 접근 테스트")
            void propertyTest() {
                List<UserDto> result = queryFactory
                        .select(Projections.fields(UserDto.class,
                                user.nickname,
                                user.age))
                        .from(user)
                        .fetch();
                
                System.out.println(result);
            }
            
            @Test
            @DisplayName("setter 접근 테스트")
            void setterTest() {
                List<UserDto> result = queryFactory
                        .select(Projections.bean(UserDto.class,
                                user.nickname,
                                user.age))
                        .from(user)
                        .fetch();
                
                System.out.println(result);
            }
            
            @Test
            @DisplayName("생성자 접근 테스트")
            void constructorTest() {
                List<UserDto> result = queryFactory
                        .select(Projections.constructor(UserDto.class,
                                user.nickname,
                                user.age))
                        .from(user)
                        .fetch();
                
                System.out.println(result);
            }
            
            @Test
            @DisplayName("dto의 이름이 일치하지 않는 경우")
            void notSameDtoTest() {
                QUser userSub = new QUser("userSub");
                
                List<UserDto2> result = queryFactory
                        .select(Projections.fields(UserDto2.class,
                                        user.nickname.as("alis"),
                                        // 서브쿼리의 별칭의 경우 ExpressionUtils
                                        ExpressionUtils.as(
                                                JPAExpressions
                                                        .select(userSub.age.max())
                                                        .from(userSub), "age")
                                )
                        )
                        .from(user)
                        .fetch();
                
                System.out.println(result);
            }
        }
    }
    
    @Nested
    @DisplayName("동적 쿼리 테스트")
    class DynamicQueryTest {
        
        @Test
        @DisplayName("BooleanBuilder 테스트")
        void BooleanBuilderTest() {
            String usernameParam = "mina";
            int ageParam = 22;
            List<User> result = searchMember1(usernameParam, ageParam);
            result.forEach(u-> {
                System.out.println(u.getNickname() + " " + u.getAge());
            });
        }
        
        public List<User> searchMember1(String usernameCond, Integer ageCond) {
            
            BooleanBuilder builder = new BooleanBuilder();
            
            if (usernameCond != null) {
                builder.and(user.nickname.eq(usernameCond));
            }
            if (ageCond != null) {
                builder.and(user.age.eq(ageCond));
            }
            
            return queryFactory
                    .selectFrom(user)
                    .where(builder)
                    .fetch();
        }
        
        @Test
        @DisplayName("다중 where 테스트")
        void multiWhereTest() {
            String usernameParam = "mina";
            int ageParam = 22;
            List<User> result = searchMember(usernameParam, ageParam);
            result.forEach(u-> {
                System.out.println(u.getNickname() + " " + u.getAge());
            });
        }
        
        private List<User> searchMember(String nameCond, Integer ageCond) {
            return queryFactory
                    .selectFrom(user)
                    .where(nicknameEq(nameCond), ageEq(ageCond))
                    .fetch();
        }
        
        private BooleanExpression nicknameEq(String nameCond) {
            return nameCond != null ? user.nickname.eq(nameCond) : null;
        }
        
        private BooleanExpression ageEq(Integer ageCond) {
            return ageCond != null ? user.age.eq(ageCond) : null;
        }
    }
}
