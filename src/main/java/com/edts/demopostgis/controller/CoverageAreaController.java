package com.edts.demopostgis.controller;

import com.edts.demopostgis.bean.CoverageAreaBean;
import com.edts.demopostgis.service.CoverageAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/coverage-area")
public class CoverageAreaController {
    private final CoverageAreaService coverageAreaService;

    @Autowired
    public CoverageAreaController(CoverageAreaService coverageAreaService) {
        this.coverageAreaService = coverageAreaService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCoverageArea(@RequestBody CoverageAreaBean areaBean) {
        coverageAreaService.addCoverageArea(areaBean);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/nearest")
    public ResponseEntity<?> getNearestLocation(@RequestParam(value = "lat") double latitude,
                                                @RequestParam(value = "lon") double longitude) {
        return new ResponseEntity<>(coverageAreaService.getNearestLocation(latitude, longitude), HttpStatus.OK);
    }

    @GetMapping(value = "/around")
    public ResponseEntity<?> getLocationWithinRadius(@RequestParam(value = "lat") double latitude,
                                                     @RequestParam(value = "lon") double longitude,
                                                     @RequestParam(value = "radius") double radius) {
        return new ResponseEntity<>(coverageAreaService.getLocationWithinRadius(latitude, longitude, radius), HttpStatus.OK);
    }

}
