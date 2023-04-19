package com.jk.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse<T> {
  
	private List<T> content;
	private int pageNumber;
	private int pageSize;
	private long toTotalElements;
	private int totalPages;
	
	private boolean lastPage;
}
