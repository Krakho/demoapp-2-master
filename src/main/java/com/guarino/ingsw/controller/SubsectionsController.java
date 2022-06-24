package com.guarino.ingsw.controller;

import com.guarino.ingsw.dto.SubsectionDTO;
import com.guarino.ingsw.service.SubsectionsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subsection")
@AllArgsConstructor
@Slf4j
public class SubsectionsController {

    private final SubsectionsService subsectionsService;

    @PostMapping
    public ResponseEntity<SubsectionDTO> createSubsections(@RequestBody SubsectionDTO subsectionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subsectionsService.create(subsectionDTO));
    }

    @GetMapping
    public ResponseEntity<List<SubsectionDTO>> getAllSubsections() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subsectionsService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubsectionDTO> getSubsectionById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subsectionsService.getSubsections(id));
    }
}