package com.travelgo.backend.domain.attraction.repository;

import com.travelgo.backend.domain.attraction.entity.Attraction;

import java.util.List;

public interface AttractionRepositoryCustom {
    List<Attraction> findAttractionsWithInDistance(double latitude, double longitude, double distance);
}
