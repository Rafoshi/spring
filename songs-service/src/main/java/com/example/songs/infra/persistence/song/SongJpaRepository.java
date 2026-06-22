package com.example.songs.infra.persistence.song;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SongJpaRepository extends JpaRepository<SongEntity, Long> {
}
