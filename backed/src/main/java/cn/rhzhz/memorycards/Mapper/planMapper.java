package cn.rhzhz.memorycards.Mapper;

import cn.rhzhz.memorycards.Entity.plan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface planMapper {
    @Update("UPDATE plan SET  planName = #{planName}, planTime = #{planTime} WHERE id = 1")
    void planUp(plan plan);
    @Select("select * from plan WHERE id = 1")
    plan getPlan();
}
