package com.travelgo.backend.domain.attraction.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttraction is a Querydsl query type for Attraction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttraction extends EntityPathBase<Attraction> {

    private static final long serialVersionUID = -1003580900L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttraction attraction = new QAttraction("attraction");

    public final com.travelgo.backend.util.QBaseTimeEntity _super = new com.travelgo.backend.util.QBaseTimeEntity(this);

    public final EnumPath<com.travelgo.backend.domain.area.entity.Area> area = createEnum("area", com.travelgo.backend.domain.area.entity.Area.class);

    public final NumberPath<Long> attractionId = createNumber("attractionId", Long.class);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final BooleanPath hiddenFlag = createBoolean("hiddenFlag");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final StringPath locationImage = createString("locationImage");

    public final StringPath locationName = createString("locationName");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final com.travelgo.backend.domain.path.entity.QPath path;

    public QAttraction(String variable) {
        this(Attraction.class, forVariable(variable), INITS);
    }

    public QAttraction(com.querydsl.core.types.Path<? extends Attraction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttraction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttraction(PathMetadata metadata, PathInits inits) {
        this(Attraction.class, metadata, inits);
    }

    public QAttraction(Class<? extends Attraction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.path = inits.isInitialized("path") ? new com.travelgo.backend.domain.path.entity.QPath(forProperty("path"), inits.get("path")) : null;
    }

}

