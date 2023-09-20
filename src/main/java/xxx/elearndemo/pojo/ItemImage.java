package xxx.elearndemo.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;

@Data
@TableName("item_images")
public class ItemImage {

    private Integer id;

    private Integer itemId;

    private String image;

    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill= FieldFill.UPDATE)
    private LocalDateTime updatedAt;

//    @TableLogic
//    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime deletedAt;

    public ItemImage() {
    }

    public ItemImage(Integer itemId, String image) {
        this.itemId = itemId;
        this.image = image;
    }
}
