package exercise.mainbatis.config;

import lombok.Data;

import java.util.List;

@Data
public class MapperBean {
    private String interfaceName;//接口名
    private List<Function> functions;//保存的方法信息
}
