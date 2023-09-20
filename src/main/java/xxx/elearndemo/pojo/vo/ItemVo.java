package xxx.elearndemo.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xxx.elearndemo.pojo.Item;
import xxx.elearndemo.pojo.ItemImage;

@Data
public class ItemVo {

    private String name;

    @JsonProperty("order_count")
    private Integer orderCount;

    @JsonProperty("bookmark_count")
    private Integer bookmarkCount;

    private String anonymous;

    private String access;


    private String image;

    public Item toItem(){
        return new Item(name,bookmarkCount,orderCount,anonymous,access);
    }

    public ItemImage toItemImage(Integer itemId){
        return new ItemImage(itemId,image);
    }

    @Override
    public String toString() {
        return "ItemVo{" +
                "name='" + name + '\'' +
                ", orderCount=" + orderCount +
                ", bookmarkCount=" + bookmarkCount +
                ", anonymous='" + anonymous + '\'' +
                ", access='" + access + '\'' +
                ", image='" + image.substring(0,10) + '\'' +
                '}';
    }
}
