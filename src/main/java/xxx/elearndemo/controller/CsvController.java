package xxx.elearndemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xxx.elearndemo.pojo.Item;
import xxx.elearndemo.pojo.ItemImage;
import xxx.elearndemo.pojo.vo.CommonResult;
import xxx.elearndemo.pojo.vo.ItemVo;
import xxx.elearndemo.service.ItemImageService;
import xxx.elearndemo.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/item")
@Slf4j
public class CsvController {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemImageService itemImageService;

    @GetMapping("/hello")
    CommonResult hello() {
        return CommonResult.okResult("hello ???");
    }

    @GetMapping
    CommonResult itemList() {
        return CommonResult.okResult(itemService.list());
    }

    @GetMapping("/image")
    CommonResult imageList() {
        return CommonResult.okResult(itemImageService.list());
    }

    @PostMapping
    CommonResult itemList(@RequestBody List<ItemVo> itemVoList) {

        log.debug(itemVoList.toString());


        try {
            for (ItemVo itemVo : itemVoList) {
                Item item = itemVo.toItem();
                Integer itemId = itemService.saveReturnId(item);
                ItemImage itemImage = itemVo.toItemImage(itemId);
                itemImageService.save(itemImage);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.badResult();
        }

        return CommonResult.okResult();
    }

}
