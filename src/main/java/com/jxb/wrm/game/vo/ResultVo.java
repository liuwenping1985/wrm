package com.jxb.wrm.game.vo;

import com.jxb.wrm.game.context.GameContext;
import com.jxb.wrm.game.po.Card;
import com.jxb.wrm.game.po.Player;
import lombok.Data;

/**
 * Created by liuwenping on 2018/6/26.
 */
@Data
public class ResultVo {

    private Boolean result;

    private String msg;

    private GameContext context;

    private Player player;

    private Card card;
}
