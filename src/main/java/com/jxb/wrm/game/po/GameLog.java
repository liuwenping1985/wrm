package com.jxb.wrm.game.po;

import lombok.Data;

/**
 * Created by liuwenping on 2018/6/22.
 */
@Data
public class GameLog {

    private Long id;

    private String gameId;

    private String userId;

    private String type;

    private String content;


}
