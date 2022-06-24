package com.guarino.ingsw.mapper;

import org.springframework.stereotype.Component;

@Component
public interface Mapper<ModelObject, DtoObject> {

    DtoObject toDTO(ModelObject destination);

}
