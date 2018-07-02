package com.jxb.wrm.game.po;

import com.jxb.wrm.game.constant.EnumPlayerState;
import lombok.Data;

import java.util.List;

/**
 * Created by liuwenping on 2018/6/22.
 */
@Data
public class Player {
    //唯一标时 一般就是微信id
    private String id;

    private String gameId;

    private String name;

    private String picUrl;
    /**
     *  0没准备 1 ready  2： gaming 3 over
     */
    private EnumPlayerState state;

    private boolean isCreator;

    private Integer playerIndex;
    /**
     * 已用卡牌
     */
    private List<Card> usedCardList;
    /**
     * 为用卡牌
     */
    private List<Card> unUsedCardList;


    private Long lastOnLine;


}
