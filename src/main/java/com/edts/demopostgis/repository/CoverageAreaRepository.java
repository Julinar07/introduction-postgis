package com.edts.demopostgis.repository;

import com.edts.demopostgis.model.CoverageArea;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoverageAreaRepository extends JpaRepository<CoverageArea, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM {h-schema}coverage_area " +
                    "ORDER BY geom <-> :point " +
                    "LIMIT 1 ")
    CoverageArea findNearestLocationByLatAndLon(Point point);

//    @Query(nativeQuery = true,
//            value = "SELECT * FROM {h-schema}coverage_area ca " +
//                    "ORDER BY ST_DistanceSphere(ca.geom, :point) " +
//                    "LIMIT 1 ")
//    CoverageArea findNearestLocationByLatAndLon(Point point);

//    @Query(nativeQuery = true,
//            value = "select * from {h-schema}coverage_area " +
//                    "order by (6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:lon)) + sin(radians(:lat)) * sin(radians(latitude)))) " +
//                    "limit 1 ")
//    CoverageArea findNearestLocationByLatAndLon(double lat, double lon);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM {h-schema}coverage_area " +
                    "WHERE ST_DistanceSphere(geom, :point) <= :radius " +
                    "ORDER BY ST_DistanceSphere(geom, :point) " +
                    "LIMIT 5 ")
    List<CoverageArea> findLocationWithinRadius(Point point, double radius);

    @Query(nativeQuery = true,
            value = "SELECT PostGIS_Full_Version() ")
    String getVersion();
}
