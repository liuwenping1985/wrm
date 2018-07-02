package com.jxb.wrm.game.event;

import com.jxb.wrm.game.constant.EnumEventType;
import lombok.Data;

/**
 * Created by liuwenping on 2018/6/22.
 */
@Data
public class GamingEvent {

    private String gameId;

    private String triggerUserId;

    private String triggerUserName;

    private EnumEventType type;

    private Object bindObject;



}
