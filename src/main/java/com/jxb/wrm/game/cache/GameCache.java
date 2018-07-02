package com.jxb.wrm.game.cache;

import com.jxb.wrm.game.context.GameContext;
import com.jxb.wrm.game.context.PlayerActionContext;

import java.util.HashMap;
import java.util.Map;

/**
 * de
 *
 * @Author liuwenping
 * @see
 */
public class GameCache {

    private GameCache() {
    }

    public static final GameCache getInstance() {

        return Inner.instance;

    }

    private Map<String, GameContext> runningGameContextByGameId = new HashMap<String, GameContext>();

    private Map<String, String> runningGameIdMapAtCreatorId = new HashMap<String, String>();

    private Map<String,PlayerActionContext> actionContextMap = new HashMap<>();

    public GameContext getGameContext(String gameId) {

        return runningGameContextByGameId.get(gameId);

    }
    public PlayerActionContext getPlayerActionContext(String gameId){

        return actionContextMap.get(gameId);
    }
    public GameContext getGameContextByCreatorId(String creatorId) {

        String gameId = runningGameIdMapAtCreatorId.get(creatorId);
        if (gameId != null) {
            return runningGameContextByGameId.get(gameId);
        }
        return null;

    }

    /**
     * put
     * @param context
     */
    public void pGameContext(GameContext context){

        runningGameContextByGameId.put(context.getGameId(),context);
        runningGameIdMapAtCreatorId.put(context.getCreatorId(),context.getGameId());
        PlayerActionContext pContext = new PlayerActionContext();
        pContext.setGameId(context.getGameId());
        pContext.setPlayerGameContext(new HashMap<>());
        actionContextMap.put(context.getGameId(),pContext);
    }

    /**
     * remove
     * @param context
     */
    public void rGameContext(GameContext context){

        runningGameContextByGameId.remove(context.getGameId());
        runningGameIdMapAtCreatorId.remove(context.getCreatorId());
        actionContextMap.remove(context.getGameId());
    }



    static class Inner {
        public static GameCache instance = new GameCache();
    }

}
