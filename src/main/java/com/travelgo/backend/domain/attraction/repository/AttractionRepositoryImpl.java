//package com.travelgo.backend.domain.attraction.repository;
//
//import com.querydsl.core.types.OrderSpecifier;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.core.types.dsl.Expressions;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.travelgo.backend.domain.attraction.entity.Attraction;
//import com.travelgo.backend.domain.attraction.entity.QAttraction;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import static com.travelgo.backend.domain.attraction.entity.QAttraction.attraction;
//
//@Repository
//@RequiredArgsConstructor
//public class AttractionRepositoryImpl implements AttractionRepositoryCustom{
//    private final JPAQueryFactory queryFactory;
//
//    public List<Attraction> findAttractionsWithInDistance(double latitude, double longitude, double distance) {
//        final String pointWKT = String.format("POINT(%s %s)", latitude, longitude);
//
//        return queryFactory
//                .selectFrom(attraction)
//                .where(isWithInDistance(pointWKT, distance))
//                .orderBy(getOrderByDistance(pointWKT))
//                .fetch();
//    }
//
//    public BooleanExpression isWithInDistance(final String pointWKT, final Double distance) {
//        return Expressions.booleanTemplate(
//                "ST_Contains(ST_Buffer(ST_GeomFromText({0}, 4326), {1}), point)",
//                pointWKT,
//                distance
//        );
//    }
//
//    public OrderSpecifier<Double> getOrderByDistance(final String pointWKT) {
//        return Expressions.numberTemplate(
//                        Double.class,
//                        "ST_Distance_Sphere(point, ST_GeomFromText({0}, 4326))",
//                        pointWKT
//                )
//                .asc();
//    }
//}
