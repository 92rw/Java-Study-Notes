package exercise.eneity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    private Integer id;
    private String name;
    private Integer code;
    private Integer cargo;
    private Date openday;
    private double distance;
}
