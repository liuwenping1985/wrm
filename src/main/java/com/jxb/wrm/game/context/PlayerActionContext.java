package com.jxb.wrm.game.context;

import com.jxb.wrm.game.vo.GameActionVo;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwenping on 2018/6/28.
 */
@Data
public class PlayerActionContext {

    private String gameId;
    private Map<String,List<GameActionVo>> playerGameContext = new HashMap<>();

}
