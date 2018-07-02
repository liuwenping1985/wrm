package com.jxb.wrm.game.vo;

import com.jxb.wrm.game.constant.EnumPlayerActionType;
import com.jxb.wrm.game.context.GameContext;
import com.jxb.wrm.game.po.Card;
import com.jxb.wrm.game.po.Player;
import lombok.Data;

/**
 * Created by liuwenping on 2018/6/28.
 */
@Data
public class GameActionVo {

    private String gameId;

    private EnumPlayerActionType type;

    private String userId;

    private GameContext gameContext;

    private Player player;

    private Card card;
}
