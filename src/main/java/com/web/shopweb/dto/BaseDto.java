package com.web.shopweb.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseDto<T> {
    private Long id;
    private Date updatedOn;
    private String updatedBy;
    private Date createdOn;
    private String createdBy;
    private long[] ids;
    private Integer page;
    private Integer maxPageItem;
    private Integer totalPage;
    private Integer totalItem;
    private String alert;
    private String message;
    private List<T> listResult = new ArrayList<>();
}
