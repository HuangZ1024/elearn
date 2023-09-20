package xxx.elearndemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xxx.elearndemo.pojo.Item;
import xxx.elearndemo.pojo.ItemImage;
import xxx.elearndemo.service.ItemImageService;
import xxx.elearndemo.service.ItemService;

import java.util.List;

@SpringBootTest
public class ItemTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemImageService itemImageService;

    @Test
    void loginDelete() {
//        itemImageService.removeById(4);
//        itemImageService.removeById(7);

    }

    @Test
    void testSave() {
//        for (int i = 0; i < 10; i++) {

//            ItemImage itemImage = new ItemImage("aaa-" + i);
//            itemImage.setItemId(0);
//            itemImageService.save(itemImage);
//        }

        Item item = new Item("n1", 4, 1, "実名", "一般公開");
        itemService.save(item);
    }

    @Test
    void getAll(){
        List<Item> list = itemService.list();
        System.out.println("list = " + list);
    }
}
