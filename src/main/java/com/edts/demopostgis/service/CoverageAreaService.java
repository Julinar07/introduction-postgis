package com.edts.demopostgis.service;

import com.edts.demopostgis.bean.CoverageAreaBean;
import com.edts.demopostgis.model.CoverageArea;
import com.edts.demopostgis.repository.CoverageAreaRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoverageAreaService {
    private final CoverageAreaRepository coverageAreaRepo;
    private final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    @Autowired
    public CoverageAreaService(CoverageAreaRepository coverageAreaRepo) {
        this.coverageAreaRepo = coverageAreaRepo;
    }

    public void addCoverageArea(CoverageAreaBean areaBean) {
        CoverageArea coverageArea = new CoverageArea();
        coverageArea.setDistrictId(areaBean.getDistrictId());
        coverageArea.setKelurahan(areaBean.getKelurahan());
        coverageArea.setKecamatan(areaBean.getKecamatan());
        coverageArea.setKota(areaBean.getKota());
        coverageArea.setProvinsi(areaBean.getProvinsi());
        coverageArea.setKodepos(areaBean.getKodepos());
        coverageArea.setLatitude(areaBean.getLatitude());
        coverageArea.setLongitude(areaBean.getLongitude());
        coverageArea.setActive(areaBean.isActive());
        coverageArea.setGeom(factory.createPoint(new Coordinate(areaBean.getLongitude(), areaBean.getLatitude())));
        coverageAreaRepo.save(coverageArea);
    }

    public CoverageAreaBean getNearestLocation(double latitude, double longitude) {
        Point point =  factory.createPoint(new Coordinate(longitude, latitude));
//        CoverageArea coverageArea = coverageAreaRepo.findNearestLocationByLatAndLon(longitude, latitude);
//        CoverageArea coverageArea = coverageAreaRepo.findNearestLocationByLatAndLon(point);
        CoverageArea coverageArea = coverageAreaRepo.findNearestLocationByLatAndLon(point);
        return toCoverageAreaBean(coverageArea);
    }

    public List<CoverageAreaBean> getLocationWithinRadius(double lat, double lon, double radius) {
        Point point =  factory.createPoint(new Coordinate(lon, lat));
        List<CoverageArea> coverageAreas = coverageAreaRepo.findLocationWithinRadius(point, radius);
        return coverageAreas.stream().map(this::toCoverageAreaBean).toList();
    }

    private CoverageAreaBean toCoverageAreaBean(CoverageArea coverageArea) {
        CoverageAreaBean areaBean = new CoverageAreaBean();
        areaBean.setId(coverageArea.getId());
        areaBean.setDistrictId(coverageArea.getDistrictId());
        areaBean.setKodepos(coverageArea.getKodepos());
        areaBean.setKelurahan(coverageArea.getKelurahan());
        areaBean.setKecamatan(coverageArea.getKecamatan());
        areaBean.setKota(coverageArea.getKota());
        areaBean.setProvinsi(coverageArea.getProvinsi());
        areaBean.setLatitude(coverageArea.getLatitude());
        areaBean.setLongitude(coverageArea.getLongitude());
        areaBean.setActive(coverageArea.isActive());
        return areaBean;
    }
}
