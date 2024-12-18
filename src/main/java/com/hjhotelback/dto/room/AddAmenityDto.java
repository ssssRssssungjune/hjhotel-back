package com.hjhotelback.dto.room;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddAmenityDto {
	@NotNull(message = "객실 타입 ID는 필수 값입니다.")
	private Integer roomTypeId;
	@NotNull(message = "어메니티 이름은 필수 값입니다.")
	private String name;
	private String description;
	@NotNull(message = "어메니티 활성화 여부는 필수 값입니다.")
	private Boolean isActive;
}

