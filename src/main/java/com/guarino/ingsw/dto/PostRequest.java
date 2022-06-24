package com.guarino.ingsw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostRequest {
    private Long subsectionId;
    private String postName;
    private String url;
    private String description;

}
