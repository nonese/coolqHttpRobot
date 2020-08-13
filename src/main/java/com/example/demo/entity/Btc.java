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
    public class Btc implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String usd;

    private String cny;

    private String open24h;

    private String high24h;

    private String low24h;

    private String lastprice;


}
