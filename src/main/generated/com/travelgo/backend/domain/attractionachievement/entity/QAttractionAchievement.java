package com.travelgo.backend.domain.attractionachievement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttractionAchievement is a Querydsl query type for AttractionAchievement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttractionAchievement extends EntityPathBase<AttractionAchievement> {

    private static final long serialVersionUID = 1273452500L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttractionAchievement attractionAchievement = new QAttractionAchievement("attractionAchievement");

    public final com.travelgo.backend.util.QBaseTimeEntity _super = new com.travelgo.backend.util.QBaseTimeEntity(this);

    public final com.travelgo.backend.domain.attraction.entity.QAttraction attraction;

    public final NumberPath<Long> attractionAchievementId = createNumber("attractionAchievementId", Long.class);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final com.travelgo.backend.domain.user.entity.QUser user;

    public QAttractionAchievement(String variable) {
        this(AttractionAchievement.class, forVariable(variable), INITS);
    }

    public QAttractionAchievement(Path<? extends AttractionAchievement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttractionAchievement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttractionAchievement(PathMetadata metadata, PathInits inits) {
        this(AttractionAchievement.class, metadata, inits);
    }

    public QAttractionAchievement(Class<? extends AttractionAchievement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attraction = inits.isInitialized("attraction") ? new com.travelgo.backend.domain.attraction.entity.QAttraction(forProperty("attraction"), inits.get("attraction")) : null;
        this.user = inits.isInitialized("user") ? new com.travelgo.backend.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

