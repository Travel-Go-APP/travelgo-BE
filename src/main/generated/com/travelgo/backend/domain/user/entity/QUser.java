package com.travelgo.backend.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1821251940L;

    public static final QUser user = new QUser("user");

    public final com.travelgo.backend.util.QBaseTimeEntity _super = new com.travelgo.backend.util.QBaseTimeEntity(this);

    public final EnumPath<com.travelgo.backend.domain.user.model.Bag> bag = createEnum("bag", com.travelgo.backend.domain.user.model.Bag.class);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Double> detectionRange = createNumber("detectionRange", Double.class);

    public final StringPath email = createString("email");

    public final NumberPath<Integer> experience = createNumber("experience", Integer.class);

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    //inherited
    public final StringPath modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> nextLevelExp = createNumber("nextLevelExp", Integer.class);

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Double> percentage = createNumber("percentage", Double.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final NumberPath<Integer> quest = createNumber("quest", Integer.class);

    public final EnumPath<com.travelgo.backend.domain.user.model.Shoes> shoes = createEnum("shoes", com.travelgo.backend.domain.user.model.Shoes.class);

    public final NumberPath<Integer> tg = createNumber("tg", Integer.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath username = createString("username");

    public final NumberPath<Integer> workCount = createNumber("workCount", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

