package com.travelgo.backend.domain.itemachievement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemAchievement is a Querydsl query type for ItemAchievement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemAchievement extends EntityPathBase<ItemAchievement> {

    private static final long serialVersionUID = -1160385772L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemAchievement itemAchievement = new QItemAchievement("itemAchievement");

    public final com.travelgo.backend.util.QBaseTimeEntity _super = new com.travelgo.backend.util.QBaseTimeEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final com.travelgo.backend.domain.item.entity.QItem item;

    public final NumberPath<Long> itemAchievementId = createNumber("itemAchievementId", Long.class);

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final com.travelgo.backend.domain.user.entity.QUser user;

    public QItemAchievement(String variable) {
        this(ItemAchievement.class, forVariable(variable), INITS);
    }

    public QItemAchievement(Path<? extends ItemAchievement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemAchievement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemAchievement(PathMetadata metadata, PathInits inits) {
        this(ItemAchievement.class, metadata, inits);
    }

    public QItemAchievement(Class<? extends ItemAchievement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new com.travelgo.backend.domain.item.entity.QItem(forProperty("item")) : null;
        this.user = inits.isInitialized("user") ? new com.travelgo.backend.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

