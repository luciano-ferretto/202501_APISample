package br.edu.atitus.apisample.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.atitus.apisample.entities.PointEntity;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, UUID>{

	@Query(value = "SELECT *, " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(latitude)))) AS distance " +
            "FROM foco " +
            "WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(latitude)))) < :radius", 
            nativeQuery = true)
    List<PointEntity> findAllWithinRadius(@Param("latitude") double latitude,
                                          @Param("longitude") double longitude,
                                          @Param("radius") double radius);

}
