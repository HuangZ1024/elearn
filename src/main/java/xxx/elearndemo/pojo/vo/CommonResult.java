package xxx.elearndemo.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult {//通用返回结果对象
    private Integer status;//状态码
    private String message;//返回信息
    private Object data;//返回数据

    public static CommonResult okResult() {
        return new CommonResult(HttpStatus.OK.value(), "成功", true);
    }

    public static CommonResult okResult(Object data) {
        return new CommonResult(HttpStatus.OK.value(), "成功", data);
    }

    public static CommonResult okResult(String message, Object data) {
        return new CommonResult(HttpStatus.OK.value(), message, data);
    }

    public static CommonResult badResult() {
        return new CommonResult(HttpStatus.BAD_REQUEST.value(), "失败", false);
    }
    public static CommonResult badResult(Object data) {
        return new CommonResult(HttpStatus.BAD_REQUEST.value(), "失败", data);
    }

    public static CommonResult badResult(String message, Object data) {
        return new CommonResult(HttpStatus.BAD_REQUEST.value(), message, data);
    }

    public static CommonResult unAuthorized(String message, Object data) {
        return new CommonResult(HttpStatus.UNAUTHORIZED.value(), message, data);
    }

    public static CommonResult unAuthorized() {
        return new CommonResult(HttpStatus.UNAUTHORIZED.value(), "unAuthorized", false);
    }
}
