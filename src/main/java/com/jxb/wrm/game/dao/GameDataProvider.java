package com.jxb.wrm.game.dao;

import com.jxb.wrm.game.context.GameContext;
import com.jxb.wrm.game.context.PlayerActionContext;

/**
 * Created by liuwenping on 2018/6/22.
 */
public interface GameDataProvider {

    public GameContext getGameContext(String gameId);
    PlayerActionContext getPlayerActionContext(String gameId);
    public GameContext getGameContextByCreator(String creatorId);
    public void saveOrUpdate(GameContext context);
    public void delete(GameContext context);

}
