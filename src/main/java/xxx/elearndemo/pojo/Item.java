package xxx.elearndemo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("items")
public class Item {
    private Integer id;

    private String name;

//    @TableField("order_count")
    private Integer orderCount;

//    @TableField("bookmark_count")
    private Integer bookmarkCount;

    private String anonymous;

    private String access;

    public Item() {
    }

    public Item(String name, Integer orderCount, Integer bookmarkCount, String anonymous, String access) {
        this.name = name;
        this.orderCount = orderCount;
        this.bookmarkCount = bookmarkCount;
        this.anonymous = anonymous;
        this.access = access;
    }
}
