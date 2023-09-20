package xxx.elearndemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xxx.elearndemo.pojo.Item;


public interface ItemService extends IService<Item> {

    //插入并返回id
    Integer saveReturnId(Item item);

}
