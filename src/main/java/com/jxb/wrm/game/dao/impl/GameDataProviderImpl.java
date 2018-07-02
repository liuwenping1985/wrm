package com.jxb.wrm.game.dao.impl;

import com.jxb.wrm.game.cache.GameCache;
import com.jxb.wrm.game.context.GameContext;
import com.jxb.wrm.game.context.PlayerActionContext;
import com.jxb.wrm.game.dao.GameDataProvider;
import org.springframework.stereotype.Component;

/**
 * Created by liuwenping on 2018/6/22.
 */
@Component
public class GameDataProviderImpl implements GameDataProvider {


    public GameContext getGameContext(String gameId) {

        return getCache().getGameContext(gameId);
    }

    @Override
    public PlayerActionContext getPlayerActionContext(String gameId) {
        return getCache().getPlayerActionContext(gameId);
    }

    public GameContext getGameContextByCreator(String creatorId) {

        return getCache().getGameContextByCreatorId(creatorId);
    }
    private GameCache getCache(){

        return GameCache.getInstance();
    }

    public void saveOrUpdate(GameContext context){

        getCache().pGameContext(context);
    }

    public void delete(GameContext context){

        getCache().rGameContext(context);
    }
}
