package com.travelgo.backend.domain.mission.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMission is a Querydsl query type for Mission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMission extends EntityPathBase<Mission> {

    private static final long serialVersionUID = -173477324L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMission mission = new QMission("mission");

    public final com.travelgo.backend.util.QBaseTimeEntity _super = new com.travelgo.backend.util.QBaseTimeEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final com.travelgo.backend.domain.item.entity.QItem item;

    public final NumberPath<Long> missionId = createNumber("missionId", Long.class);

    public final EnumPath<com.travelgo.backend.domain.itemachievement.model.MissionType> missionType = createEnum("missionType", com.travelgo.backend.domain.itemachievement.model.MissionType.class);

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final com.travelgo.backend.domain.user.entity.QUser user;

    public QMission(String variable) {
        this(Mission.class, forVariable(variable), INITS);
    }

    public QMission(Path<? extends Mission> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMission(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMission(PathMetadata metadata, PathInits inits) {
        this(Mission.class, metadata, inits);
    }

    public QMission(Class<? extends Mission> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new com.travelgo.backend.domain.item.entity.QItem(forProperty("item")) : null;
        this.user = inits.isInitialized("user") ? new com.travelgo.backend.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

