package com.guarino.ingsw.service;

import com.guarino.ingsw.dto.SubsectionDTO;
import com.guarino.ingsw.exceptions.DemoAppException;
import com.guarino.ingsw.mapper.SubsectionsMapper;
import com.guarino.ingsw.model.Subsection;
import com.guarino.ingsw.model.User;
import com.guarino.ingsw.repository.SubsectionRepository;
import com.guarino.ingsw.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubsectionsService {

    @Autowired
    SubsectionRepository subsectionRepository;
    @Autowired
    SubsectionsMapper subsectionsMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Transactional
    public SubsectionDTO create(SubsectionDTO subsectionDTO) {
        User user = authService.getCurrentUser();
        Subsection subsection = Subsection.Builder.aSubsection()
                .withName(subsectionDTO.name)
                .withDescription(subsectionDTO.description)
                .withUser(user)
                .build();
        subsection = subsectionRepository.save(subsection);

        subsectionDTO.id = subsection.getId();

        return subsectionDTO;
    }

    @Transactional(readOnly = true)
    public List<SubsectionDTO> getAll() {
        return subsectionRepository.findAll()
                .stream()
                .map(subsectionsMapper::toDTO)
                .collect(toList());
    }

    public SubsectionDTO getSubsections(Long id) {
        Subsection subsection = subsectionRepository.findById(id)
                .orElseThrow(() -> new DemoAppException("No subsections found with ID - " + id));
        return subsectionsMapper.toDTO(subsection);
    }
}