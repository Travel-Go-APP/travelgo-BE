package com.travelgo.backend.domain.path.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPath is a Querydsl query type for Path
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPath extends EntityPathBase<Path> {

    private static final long serialVersionUID = -1641721064L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPath path = new QPath("path");

    public final com.travelgo.backend.util.QBaseTimeEntity _super = new com.travelgo.backend.util.QBaseTimeEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> pathId = createNumber("pathId", Long.class);

    public final ListPath<com.travelgo.backend.domain.attraction.entity.Attraction, com.travelgo.backend.domain.attraction.entity.QAttraction> stopOver = this.<com.travelgo.backend.domain.attraction.entity.Attraction, com.travelgo.backend.domain.attraction.entity.QAttraction>createList("stopOver", com.travelgo.backend.domain.attraction.entity.Attraction.class, com.travelgo.backend.domain.attraction.entity.QAttraction.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final com.travelgo.backend.domain.user.entity.QUser user;

    public QPath(String variable) {
        this(Path.class, forVariable(variable), INITS);
    }

    public QPath(com.querydsl.core.types.Path<? extends Path> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPath(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPath(PathMetadata metadata, PathInits inits) {
        this(Path.class, metadata, inits);
    }

    public QPath(Class<? extends Path> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.travelgo.backend.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

