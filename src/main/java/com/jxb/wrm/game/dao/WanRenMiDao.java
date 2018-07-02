package com.jxb.wrm.game.dao;

import com.jxb.wrm.game.context.GameContext;
import com.jxb.wrm.game.po.Card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by liuwenping on 2018/6/22.
 */
@Component
public class WanRenMiDao {

    @Autowired
    private GameDataProvider gameDataProvider;

    public void saveGameContext(GameContext context){
        gameDataProvider.saveOrUpdate(context);

    }


    public GameContext getGameContextByCreator(String creatorId){
        return gameDataProvider.getGameContextByCreator(creatorId);
    }

    public GameContext getGameContextById(String gameId){

        return gameDataProvider.getGameContext(gameId);
    }

    public GameContext removeGameContextById(String gameId){
        GameContext context = gameDataProvider.getGameContext(gameId);
        gameDataProvider.delete(context);
        return context;

    }


}
