package com.hjhotelback.mapper.board;

import com.hjhotelback.dto.board.BoardDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

    // TODO: Define database interactions for Board
    BoardDto findById(Long id);
}
