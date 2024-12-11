package com.hjhotelback.dto.board;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Notice {
    private Integer noticeId;

    @NotBlank(message = "제목은 비워둘 수 없습니다.")
    @Size(max = 200, message = "제목은 최대 200자까지 가능합니다.")
    private String title;

    @NotBlank(message = "내용은 비워둘 수 없습니다.")
    private String content;

    @NotNull(message = "카테고리는 반드시 선택해야 합니다.")
    private NoticeCategory category;

    @AssertTrue(message = "중요 여부는 true 또는 false 여야 합니다.")
    private Boolean isImportant;

    @Min(value = 0, message = "조회수는 0 이상이어야 합니다.")
    private Integer views = 0;

    private Timestamp createdAt;
    private Timestamp updatedAt;
}