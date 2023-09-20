package xxx.elearndemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xxx.elearndemo.dao.ItemMapper;
import xxx.elearndemo.pojo.Item;
import xxx.elearndemo.service.ItemService;


@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    @Autowired
    ItemMapper itemMapper;

    @Override
    public Integer saveReturnId(Item item) {
        if (itemMapper.insert(item) == 0) {
            return null;
        }
        return itemMapper.selectLastInsertId();
    }
}
