package com.example.demo.entity;

    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author Yaojiaqi
* @since 2020-08-02
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class Shangzhengzhishu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String zhishu;

    private String xiangqing;


}
