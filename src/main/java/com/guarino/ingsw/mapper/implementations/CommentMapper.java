package com.guarino.ingsw.mapper.implementations;

import com.guarino.ingsw.dto.CommentDTO;
import com.guarino.ingsw.mapper.Mapper;
import com.guarino.ingsw.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper implements Mapper<Comment, CommentDTO> {

    @Override
    public CommentDTO toDTO(Comment source) {
        CommentDTO dto = new CommentDTO();
        dto.createdDate = source.getCreatedDate();
        dto.id = source.getId();
        dto.postId = source.getPost().getId();
        dto.text = source.getText();
        dto.userName = source.getUser().getUsername();
        return dto;
    }

}
