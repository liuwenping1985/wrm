package com.jxb.wrm.game.vo;

import lombok.Data;

/**
 * Created by liuwenping on 2018/6/22.
 */
@Data
public class GameCreateVo extends BaseGameVo{

    private String roomName;
    private Integer playerCount;

}
