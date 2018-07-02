package com.jxb.wrm.game.service;

import com.jxb.wrm.game.constant.EnumSocketStatus;
import lombok.Data;

/**
 * Created by liuwenping on 2018/6/22.
 */
@Data
public class SocketInfo {

    private Integer port;

    private String host;

    private Integer timeout;

    private String gameId;

    private EnumSocketStatus status;


}
