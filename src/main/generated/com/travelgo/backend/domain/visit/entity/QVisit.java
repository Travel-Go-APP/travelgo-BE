package com.travelgo.backend.domain.visit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVisit is a Querydsl query type for Visit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVisit extends EntityPathBase<Visit> {

    private static final long serialVersionUID = 1654944468L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVisit visit = new QVisit("visit");

    public final com.travelgo.backend.util.QBaseTimeEntity _super = new com.travelgo.backend.util.QBaseTimeEntity(this);

    public final com.travelgo.backend.domain.attraction.entity.QAttraction attraction;

    //inherited
    public final StringPath createdAt = _super.createdAt;

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final com.travelgo.backend.domain.user.entity.QUser user;

    public final NumberPath<Long> visitId = createNumber("visitId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> visitTime = createDateTime("visitTime", java.time.LocalDateTime.class);

    public QVisit(String variable) {
        this(Visit.class, forVariable(variable), INITS);
    }

    public QVisit(Path<? extends Visit> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVisit(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVisit(PathMetadata metadata, PathInits inits) {
        this(Visit.class, metadata, inits);
    }

    public QVisit(Class<? extends Visit> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attraction = inits.isInitialized("attraction") ? new com.travelgo.backend.domain.attraction.entity.QAttraction(forProperty("attraction"), inits.get("attraction")) : null;
        this.user = inits.isInitialized("user") ? new com.travelgo.backend.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

