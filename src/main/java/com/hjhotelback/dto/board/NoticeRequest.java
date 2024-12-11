package com.hjhotelback.dto.board;

import jakarta.validation.constraints.*;
import com.hjhotelback.dto.board.NoticeCategory;
import lombok.Data;

@Data
public class NoticeRequest {

    @NotBlank(message = "제목은 비워둘 수 없습니다.")
    @Size(max = 200, message = "제목은 최대 200자까지 가능합니다.")
    private String title;

    @NotBlank(message = "내용은 비워둘 수 없습니다.")
    private String content;

    @NotNull(message = "카테고리는 반드시 선택해야 합니다.")
    private NoticeCategory category;

    private Boolean isImportant;
}
