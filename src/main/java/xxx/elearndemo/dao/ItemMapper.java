package xxx.elearndemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xxx.elearndemo.pojo.Item;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {

    @Select("select last_insert_id() from items limit 1")
    Integer selectLastInsertId();



}
