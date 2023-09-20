package xxx.elearndemo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, LocalDateTime.now());//和field类型要一致不然可能会失败
//        this.strictInsertFill(metaObject, "deleteAt", LocalDateTime.class, LocalDateTime.now());//和field类型要一致不然可能会失败
//        this.strictInsertFill(metaObject, "deleteAt", String.class, LocalDateTime.now().toString());//和field类型要一致不然可能会失败

        log.info(this.toString());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());

    }

}
