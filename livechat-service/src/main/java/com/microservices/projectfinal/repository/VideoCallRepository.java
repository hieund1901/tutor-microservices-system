package com.microservices.projectfinal.repository;

import com.microservices.projectfinal.entity.VideoCallEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VideoCallRepository extends JpaRepository<VideoCallEntity, UUID> {
}
