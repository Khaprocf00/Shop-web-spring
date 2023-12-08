package com.web.shopweb.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SliderDto {
    private String name;
    private String content;
    private String imagePath;
    private Long id;
    private Date updatedOn;
    private String updatedBy;
    private Date createdOn;
    private String createdBy;
    private MultipartFile image;
    private long[] ids;
    private Integer page;
    private Integer maxPageItem;
    private Integer totalPage;
    private Integer totalItem;
    private String alert;
    private String message;
    private List<SliderDto> listResult = new ArrayList<>();
}
