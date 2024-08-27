package com.travelgo.backend.domain.user.repository;

import com.travelgo.backend.domain.user.entity.UserAgree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAgreeRepository extends JpaRepository<UserAgree, Long> {
}
