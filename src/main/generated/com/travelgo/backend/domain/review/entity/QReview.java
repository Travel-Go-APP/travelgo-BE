package com.travelgo.backend.domain.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 415714110L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.travelgo.backend.util.QBaseTimeEntity _super = new com.travelgo.backend.util.QBaseTimeEntity(this);

    public final com.travelgo.backend.domain.attraction.entity.QAttraction attraction;

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdAt = _super.createdAt;

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final NumberPath<Float> rating = createNumber("rating", Float.class);

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final StringPath reviewImageUrl = createString("reviewImageUrl");

    public final DateTimePath<java.time.LocalDateTime> time = createDateTime("time", java.time.LocalDateTime.class);

    public final com.travelgo.backend.domain.user.entity.QUser user;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attraction = inits.isInitialized("attraction") ? new com.travelgo.backend.domain.attraction.entity.QAttraction(forProperty("attraction")) : null;
        this.user = inits.isInitialized("user") ? new com.travelgo.backend.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

