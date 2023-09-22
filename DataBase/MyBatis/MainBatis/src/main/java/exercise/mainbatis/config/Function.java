package exercise.mainbatis.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Function {
    private String sqlType;//SQL类型，比如select, insert, update, delete
    private String funcName;//方法名
    private String sql;//需要执行的SQL语句
    private Object resultType;//返回类型
    private String parameterType;//参数类型
}
