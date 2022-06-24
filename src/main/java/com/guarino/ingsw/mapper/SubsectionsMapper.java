package com.guarino.ingsw.mapper;

import com.guarino.ingsw.dto.SubsectionDTO;
import com.guarino.ingsw.model.Subsection;
import org.springframework.stereotype.Component;

@Component
public class SubsectionsMapper implements Mapper<Subsection, SubsectionDTO> {

    @Override
    public SubsectionDTO toDTO(Subsection source) {
        SubsectionDTO subsectionDTO = new SubsectionDTO();
        subsectionDTO.id = source.getId();
        subsectionDTO.description = source.getDescription();
        subsectionDTO.name = source.getName();
        subsectionDTO.numberOfPosts = source.getPosts().size();
        return subsectionDTO;
    }


}
