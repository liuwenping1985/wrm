package com.jxb.wrm.game.po;

import com.jxb.wrm.game.constant.EnumCardType;
import lombok.Data;

/**
 * Created by liuwenping on 2018/6/22.
 */
@Data
public class Card {

    private EnumCardType cardType;

    private String url;

    private String context;

    private String cardNo;

    private String cardName;

    private Integer cardSequence;

    private int randomSeed;

    private boolean canUseLater;


}
