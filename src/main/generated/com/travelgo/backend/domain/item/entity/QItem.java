package com.travelgo.backend.domain.item.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = -1963029516L;

    public static final QItem item = new QItem("item");

    public final com.travelgo.backend.util.QBaseTimeEntity _super = new com.travelgo.backend.util.QBaseTimeEntity(this);

    public final EnumPath<com.travelgo.backend.domain.area.entity.Area> area = createEnum("area", com.travelgo.backend.domain.area.entity.Area.class);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final EnumPath<com.travelgo.backend.domain.item.model.Grade> grade = createEnum("grade", com.travelgo.backend.domain.item.model.Grade.class);

    public final StringPath itemDescription = createString("itemDescription");

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    public final StringPath itemImageUrl = createString("itemImageUrl");

    public final StringPath itemName = createString("itemName");

    public final StringPath itemType = createString("itemType");

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QItem(String variable) {
        super(Item.class, forVariable(variable));
    }

    public QItem(Path<? extends Item> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem(PathMetadata metadata) {
        super(Item.class, metadata);
    }

}

