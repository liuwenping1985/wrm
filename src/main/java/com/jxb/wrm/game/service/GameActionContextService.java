package com.jxb.wrm.game.service;

import com.jxb.wrm.game.cache.GameCache;
import com.jxb.wrm.game.constant.EnumPlayerActionType;
import com.jxb.wrm.game.context.GameContext;
import com.jxb.wrm.game.context.PlayerActionContext;
import com.jxb.wrm.game.po.Card;
import com.jxb.wrm.game.po.Player;
import com.jxb.wrm.game.vo.GameActionVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwenping on 2018/6/28.
 */
@Service
public class GameActionContextService {


    /**
     * 添加用户动作队列
     *
     * @param actionType
     * @param context
     * @param userId
     */
    public void addUserGameAction(EnumPlayerActionType actionType, GameContext context, String userId) {

        GameCache cache = GameCache.getInstance();
        PlayerActionContext playerContext = cache.getPlayerActionContext(context.getGameId());
        Map<String, List<GameActionVo>> gameActions = playerContext.getPlayerGameContext();

        List<Player> players = context.getPlayerList();

        for (Player player : players) {

            List<GameActionVo> gameActionList = gameActions.get(player.getId());
            if (gameActionList == null) {
                gameActionList = new ArrayList<>();
                gameActions.put(player.getId(), gameActionList);
            }
            GameActionVo vo = new GameActionVo();
            vo.setGameId(context.getGameId());
            vo.setUserId(userId);
            vo.setType(actionType);
            switch (actionType) {

                case JOIN_GAME:
                case GAME_READY: {
                    vo.setPlayer(context.getPlayerById(userId));
                    break;
                }
                case DRAW_CARD: {
                    Player currentPlayer = context.getPlayerById(userId);
                    vo.setPlayer(currentPlayer);
                    List<Card> cardList = currentPlayer.getUnUsedCardList();
                    if (cardList != null) {
                        Card card = cardList.get(cardList.size()-1);
                        vo.setCard(card);
                    }
                    break;
                }
                case USING_CARD:{
                    Player currentPlayer = context.getPlayerById(userId);
                    vo.setPlayer(currentPlayer);
                    List<Card> cardList = currentPlayer.getUsedCardList();
                    if (cardList != null) {
                        Card card = cardList.get(cardList.size()-1);
                        vo.setCard(card);
                    }
                    break;
                }
                case GAME_END:
                case GAME_START:{
                    vo.setGameContext(context);
                }

            }
            gameActionList.add(vo);
        }

    }

    /**
     * 使用微信avtar作为id
     *
     * @param gameId
     * @param userId
     * @return
     */
    public List<GameActionVo> getGameActionVo(String gameId, String userId) {

        GameCache cache = GameCache.getInstance();
        PlayerActionContext playerContext = cache.getPlayerActionContext(gameId);

        List<GameActionVo> actionVos = playerContext.getPlayerGameContext().remove(userId);
        return actionVos;
    }


}
