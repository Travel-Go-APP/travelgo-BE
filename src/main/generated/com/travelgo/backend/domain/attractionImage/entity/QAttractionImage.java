package com.travelgo.backend.domain.attractionImage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttractionImage is a Querydsl query type for AttractionImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttractionImage extends EntityPathBase<AttractionImage> {

    private static final long serialVersionUID = -1008022476L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttractionImage attractionImage = new QAttractionImage("attractionImage");

    public final com.travelgo.backend.util.QBaseTimeEntity _super = new com.travelgo.backend.util.QBaseTimeEntity(this);

    public final com.travelgo.backend.domain.attraction.entity.QAttraction attraction;

    public final NumberPath<Long> attractionImageId = createNumber("attractionImageId", Long.class);

    public final StringPath attractionImageUrl = createString("attractionImageUrl");

    //inherited
    public final StringPath createdAt = _super.createdAt;

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public QAttractionImage(String variable) {
        this(AttractionImage.class, forVariable(variable), INITS);
    }

    public QAttractionImage(Path<? extends AttractionImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttractionImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttractionImage(PathMetadata metadata, PathInits inits) {
        this(AttractionImage.class, metadata, inits);
    }

    public QAttractionImage(Class<? extends AttractionImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attraction = inits.isInitialized("attraction") ? new com.travelgo.backend.domain.attraction.entity.QAttraction(forProperty("attraction"), inits.get("attraction")) : null;
    }

}

