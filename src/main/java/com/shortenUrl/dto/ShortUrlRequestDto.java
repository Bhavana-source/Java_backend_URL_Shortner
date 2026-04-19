package com.shortenUrl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortUrlRequestDto {
	
	@NotNull
	@NotBlank(message = "URL cannot be empty")
	@Pattern(
	        regexp = "^(http|https)://.*$",
	        message = "URL must start with http:// or https://"
	    )
	String originalUrl;

}
