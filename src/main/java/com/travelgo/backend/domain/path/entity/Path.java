package com.travelgo.backend.domain.path.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Path extends BaseTimeEntity {

    @Id
    @Column(name = "path_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pathId;

    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "path", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Attraction> stopOver = new ArrayList<>();

    @Builder
    public Path(List<Attraction> stopOver, User user, String description, String title) {
        this.stopOver = stopOver;
        this.user = user;
        this.description = description;
        this.title = title;
    }
}
