package com.hjhotelback.mapper.board;

import com.hjhotelback.dto.board.Notice;
import com.hjhotelback.dto.board.NoticeCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface NoticeMapper {

    @Select("SELECT * FROM notice WHERE notice_id = #{id}")
    @Results({
            @Result(property = "category", column = "category", javaType = NoticeCategory.class)
    })
    Optional<Notice> findById(@Param("id") Integer id);

    @Select("SELECT * FROM notice ORDER BY notice_id ${sortOrder} LIMIT ${offset}, ${limit}")
    @Results({
            @Result(property = "category", column = "category", javaType = NoticeCategory.class)
    })
    List<Notice> findAll(@Param("sortOrder") String sortOrder,
                         @Param("offset") int offset,
                         @Param("limit") int limit);

    @Select("SELECT count(*) FROM notice")
    int countTotal();

    @Insert("INSERT INTO notice (title, content, category, is_important) " +
            "VALUES (#{title}, #{content}, #{category}, #{isImportant})")
    @Options(useGeneratedKeys = true, keyProperty = "noticeId")
    void save(Notice notice);

    @Update("UPDATE notice SET title = #{title}, content = #{content}, category = #{category}, is_important = #{isImportant} WHERE notice_id = #{noticeId}")
    void update(Notice notice);

    @Delete("DELETE FROM notice WHERE notice_id = #{id}")
    void delete(@Param("id") Integer id);

    @Update("UPDATE notice SET views = views + 1 WHERE notice_id = #{id}")
    void incrementViews(@Param("id") Integer id);

}