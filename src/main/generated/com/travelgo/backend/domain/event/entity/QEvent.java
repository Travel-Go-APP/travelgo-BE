package com.travelgo.backend.domain.event.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEvent is a Querydsl query type for Event
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = -671495372L;

    public static final QEvent event = new QEvent("event");

    public final com.travelgo.backend.util.QBaseTimeEntity _super = new com.travelgo.backend.util.QBaseTimeEntity(this);

    public final EnumPath<com.travelgo.backend.domain.area.entity.Area> area = createEnum("area", com.travelgo.backend.domain.area.entity.Area.class);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> eventId = createNumber("eventId", Long.class);

    public final StringPath eventName = createString("eventName");

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> periodDays = createNumber("periodDays", Long.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QEvent(String variable) {
        super(Event.class, forVariable(variable));
    }

    public QEvent(Path<? extends Event> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEvent(PathMetadata metadata) {
        super(Event.class, metadata);
    }

}

