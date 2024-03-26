package cn.rhzhz.memorycards.Mapper;

import cn.rhzhz.memorycards.Entity.Flashcard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;

@Mapper
public interface cardMapper {
    //注解加sql语句的方式来查询
    @Select("select * from flashcards")
    List<Flashcard> getAllCard();

    @Select("SELECT * FROM flashcards WHERE category = #{msg}")
    List<Flashcard> search(String msg);
    @Insert("INSERT INTO flashcards (image, category, content, remembered, chooseday, lastReview, startTime) " +
            "VALUES (#{image}, #{category}, #{content}, #{remembered}, #{chooseday}, #{lastReview}, #{startTime})")
    void insertFlashcard(Flashcard flashcard);

    @Update("DELETE FROM flashcards WHERE id = #{id}")
    void delCard(int id);

    @Update("UPDATE flashcards SET image = #{image}, category = #{category}, content = #{content}, remembered = #{remembered}, chooseday = #{chooseday}, lastReview = #{lastReview}, startTime = #{startTime} WHERE id = #{id}")
    void updateFlashcard(Flashcard flashcard);
}
