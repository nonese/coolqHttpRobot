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
    public class Autosiginin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String qq;

    private String genchid;

    private String genchpassword;

    private String open;

    private String phone;

    private String cookie;

    private String uid;

    private String name;


}
