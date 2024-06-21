package com.travelgo.backend.domain.drop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDrop is a Querydsl query type for Drop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDrop extends EntityPathBase<Drop> {

    private static final long serialVersionUID = 228526508L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDrop drop = new QDrop("drop");

    public final com.travelgo.backend.util.QBaseTimeEntity _super = new com.travelgo.backend.util.QBaseTimeEntity(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Long> dropId = createNumber("dropId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> dropTime = createDateTime("dropTime", java.time.LocalDateTime.class);

    public final com.travelgo.backend.domain.item.entity.QItem item;

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final com.travelgo.backend.domain.user.entity.QUser user;

    public QDrop(String variable) {
        this(Drop.class, forVariable(variable), INITS);
    }

    public QDrop(Path<? extends Drop> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDrop(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDrop(PathMetadata metadata, PathInits inits) {
        this(Drop.class, metadata, inits);
    }

    public QDrop(Class<? extends Drop> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new com.travelgo.backend.domain.item.entity.QItem(forProperty("item")) : null;
        this.user = inits.isInitialized("user") ? new com.travelgo.backend.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

