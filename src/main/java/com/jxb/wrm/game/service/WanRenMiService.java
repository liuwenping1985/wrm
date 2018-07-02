package com.jxb.wrm.game.service;

import com.alibaba.fastjson.JSON;
import com.jxb.wrm.game.constant.*;
import com.jxb.wrm.game.context.BaseCardGenerator;
import com.jxb.wrm.game.context.GameContext;
import com.jxb.wrm.game.dao.WanRenMiDao;
import com.jxb.wrm.game.event.GamingEvent;
import com.jxb.wrm.game.po.Card;
import com.jxb.wrm.game.po.Player;
import com.jxb.wrm.game.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by liuwenping on 2018/6/22.
 */
@Service
public class WanRenMiService {

    @Autowired
    private WanRenMiDao wanRenMiDao;

    @Autowired
    private SocketService socketService;

    @Autowired
    private GameActionContextService gameActionContextService;


    public ResultVo getGameContext(String gameId) {
        GameContext context = wanRenMiDao.getGameContextById(gameId);
        ResultVo vo = new ResultVo();
        vo.setResult(true);
        vo.setMsg("GET_CONTEXT");
        vo.setContext(context);
        return vo;
    }

    public ResultVo createGame(GameCreateVo gameCreateVo) {
        ResultVo resultVo = new ResultVo();
        resultVo.setResult(true);
        GameContext context = wanRenMiDao.getGameContextByCreator(gameCreateVo.getUserId());
        if (context != null) {
            if (context.getGameStatus() == EnumGameStatus.PREPARING) {
                resultVo.setMsg("已经存在返回之前的");
                resultVo.setContext(context);
                return resultVo;
            } else {
                //中断游戏重新创建
                GamingEvent event = genGamingEvent(context, gameCreateVo.getUserId(), gameCreateVo.getUserName(), EnumEventType.GAME_INTERRUPT);
                fireEvent(event);
            }
        }

        context = new GameContext();
        context.setGameId(UUID.randomUUID().toString());
        context.setCreatorId(gameCreateVo.getUserId());
        context.setGameStatus(EnumGameStatus.PREPARING);
        context.setCurrentCardIndex(0);
        SocketInfo info = socketService.createSocket(context);
        //socket 成功
        if (info.getStatus() == EnumSocketStatus.RUNNING) {
            context.setSocketInfo(info);
            wanRenMiDao.saveGameContext(context);
            context.setCardList(genCardList());
            Player player = new Player();
            player.setGameId(context.getGameId());
            player.setCreator(true);
            player.setId(gameCreateVo.getUserId());
            player.setName(gameCreateVo.getUserName());
            player.setState(EnumPlayerState.READY);
            player.setPlayerIndex(1);
            player.setLastOnLine(new Date().getTime());
            List<Player> playerList = context.getPlayerList();
            if (CollectionUtils.isEmpty(playerList)) {
                playerList = new ArrayList<>();
                context.setPlayerList(playerList);
            }
            playerList.add(player);
            context.setPlayerCount(gameCreateVo.getPlayerCount());
        } else {
            context.setSocketInfo(info);
            resultVo.setResult(false);
            context.setGameStatus(EnumGameStatus.ERROR_SOCKET);

        }
        resultVo.setContext(context);
        return resultVo;
    }

    public ResultVo joinGame(GameJoinVo gameJoinVo) {
        GameContext gameContext = wanRenMiDao.getGameContextById(gameJoinVo.getGameId());
        if (gameContext != null) {

            Player player = new Player();
            player.setName(gameJoinVo.getUserName());
            player.setId(gameJoinVo.getUserId());
            player.setState(EnumPlayerState.PREPARING);
            player.setCreator(false);
            player.setLastOnLine(new Date().getTime());
            player.setGameId(gameJoinVo.getGameId());
            player.setPlayerIndex(gameContext.getPlayerList().size() + 1);
            gameContext.getPlayerList().add(player);
        }

        gameActionContextService.addUserGameAction(EnumPlayerActionType.JOIN_GAME, gameContext, gameJoinVo.getUserId());

        ResultVo resultVo = new ResultVo();
        resultVo.setResult(true);
        resultVo.setContext(gameContext);
        return resultVo;
    }


    public ResultVo exitGame(BaseGameVo baseGameVo) {
        ResultVo vo = new ResultVo();
        GameContext context = wanRenMiDao.getGameContextById(baseGameVo.getGameId());
        List<Player> players = context.getPlayerList();
        Iterator<Player> it = players.iterator();
        while (it.hasNext()) {
            Player player = it.next();
            if (player.getId().equals(baseGameVo.getUserId())) {
                it.remove();
                vo.setResult(true);
                vo.setContext(context);
                vo.setMsg("成员离开游戏成功");
                return vo;
            }
        }
        vo.setResult(false);
        vo.setContext(context);
        vo.setMsg("成员离开游戏失败");
        return vo;
    }

    public ResultVo restartGame(String gameId) {

        return null;
    }

    public ResultVo destroyGame(String gameId) {
        ResultVo vo = new ResultVo();
        GameContext context = wanRenMiDao.removeGameContextById(gameId);
        vo.setContext(context);
        vo.setResult(true);
        return vo;
    }

    public ResultVo startGame(String gameId) {
        ResultVo vo = new ResultVo();
        GameContext context = wanRenMiDao.getGameContextById(gameId);
        List<Player> players = context.getPlayerList();
        for (Player player : players) {

            if (player.getState() != EnumPlayerState.READY) {
                vo.setResult(false);
                vo.setContext(context);
                vo.setMsg("有的玩家还没准备好");
                return vo;
            }
        }
        vo.setContext(context);
        vo.setResult(true);
        context.setGameStatus(EnumGameStatus.RUNNING);
        for (Player player : players) {
            player.setState(EnumPlayerState.GAMING);
        }
        gameActionContextService.addUserGameAction(EnumPlayerActionType.GAME_START, context, context.getCreatorId());
        return vo;
    }

    public ResultVo usingCard(GamePlayVo gamePlayVo) {
        GameContext context = wanRenMiDao.getGameContextById(gamePlayVo.getGameId());
        Integer cardSequence = gamePlayVo.getCardSequence();
        ResultVo vo = new ResultVo();
        vo.setResult(true);
        if (cardSequence == null) {
            vo.setResult(false);
        }

        List<Player> players = context.getPlayerList();

        for (Player player : players) {

            if (player.getId().equals(gamePlayVo.getUserId())) {

                List<Card> unusedCardList = player.getUnUsedCardList();
                Iterator<Card> cardIt = unusedCardList.iterator();
                int i = 0;
                while (cardIt.hasNext()) {
                    Card card = cardIt.next();
                    if (i == cardSequence) {

                        cardIt.remove();
                        List<Card> usedList = player.getUsedCardList();
                        if (usedList == null) {
                            usedList = new ArrayList<>();
                            player.setUsedCardList(usedList);
                        }
                        usedList.add(card);
                        gameActionContextService.addUserGameAction(EnumPlayerActionType.USING_CARD, context, gamePlayVo.getUserId());

                        break;
                    }
                    i++;
                }
            }
        }
        return vo;


    }

    public ResultVo drawCard(GamePlayVo gamePlayVo) {
        ResultVo vo = new ResultVo();
        vo.setResult(true);

        GameContext context = wanRenMiDao.getGameContextById(gamePlayVo.getGameId());
        if (context.getCurrentCardIndex() == 48) {
            vo.setResult(false);
            vo.setMsg("没有牌了");
            gameActionContextService.addUserGameAction(EnumPlayerActionType.GAME_END, context, gamePlayVo.getUserId());
            return vo;
        }
        List<Player> players = context.getPlayerList();
        for (Player player : players) {

            if (player.getId().equals(gamePlayVo.getUserId())) {

                Card card = context.getCardList().get(context.getCurrentCardIndex() + 1);
                context.setCurrentCardIndex(context.getCurrentCardIndex() + 1);
                List<Card> list = player.getUnUsedCardList();
                if (list == null) {
                    list = new ArrayList<>();
                    player.setUnUsedCardList(list);
                }
                list.add(card);
                gameActionContextService.addUserGameAction(EnumPlayerActionType.DRAW_CARD, context, gamePlayVo.getUserId());
                vo.setPlayer(player);
                vo.setCard(card);
                break;
            }
        }

        return vo;

    }

    public ResultVo playerReady(BaseGameVo baseGameVo) {
        ResultVo vo = new ResultVo();
        GameContext context = wanRenMiDao.getGameContextById(baseGameVo.getGameId());
        if (context.getGameStatus() != EnumGameStatus.PREPARING) {
            vo.setResult(false);
            vo.setMsg("啊哦。太久没操作了，游戏已经开始");
            vo.setContext(context);
            return vo;
        }
        List<Player> players = context.getPlayerList();
        for (Player player : players) {
            if (player.getId().equals(baseGameVo.getUserId())) {
                player.setState(EnumPlayerState.READY);
            }

        }
        gameActionContextService.addUserGameAction(EnumPlayerActionType.GAME_READY, context, baseGameVo.getUserId());

        vo.setResult(true);
        vo.setMsg("准备成功");
        vo.setContext(context);

        return vo;

    }

    public boolean heartBeats(BaseGameVo baseGameVo) {

        return true;

    }


    public void fireEvent(GamingEvent event) {
        processEvent(event);

    }

    public GamingEvent genGamingEvent(GameContext context, String triggerUserId, String triggerUserName, EnumEventType type) {

        GamingEvent event = new GamingEvent();
        event.setBindObject(context);
        event.setGameId(context.getGameId());
        event.setTriggerUserId(triggerUserId);
        event.setTriggerUserName(triggerUserName);
        event.setType(type);
        return event;
    }

    //异步的线程处理
    public void processEvent(GamingEvent event) {
        EnumEventType type = event.getType();
        if (type == null) {
            //not valid event
            return;
        }
        switch (type) {

            case PLAYER_DRAW_CARD: {
                String gameId = event.getGameId();
                // this.getGameWrmDao().
                return;
            }
            case GAME_INTERRUPT: {
                //客户端处理 退出游戏
                socketService.notifyCustomThroughEvent(event);
                return;
            }
            //游戏开始
            case GAME_START: {
                //客户端处理 进行页面跳转
                socketService.notifyCustomThroughEvent(event);
                return;
            }
            case GAME_OVER: {
                socketService.notifyCustomThroughEvent(event);
                return;
            }
            case GAME_NOTICE: {
                socketService.notifyCustomThroughEvent(event);
                return;
            }


        }


    }


    private List<Card> genCardList() {

        List<Card> baseCardList = BaseCardGenerator.genBase12Cards();
        List<Card> gameCardList = new ArrayList<>();
        Random rand = new Random(System.currentTimeMillis());
        Integer bound = 250 * 3;
        for (int i = 0; i < 4; i++) {
            for (Card cardTemplate : baseCardList) {
                String json = JSON.toJSONString(cardTemplate);
                Card card = JSON.parseObject(json, Card.class);
                card.setRandomSeed(rand.nextInt(bound));
                gameCardList.add(card);
            }
        }
        Collections.sort(gameCardList, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.getRandomSeed() - o2.getRandomSeed();
            }
        });
        for (int i = 0; i < gameCardList.size(); i++) {
            Card card = gameCardList.get(i);
            card.setCardSequence(i + 1);
        }

        return gameCardList;

    }

    public List<GameActionVo> getGameActionVo(String gameId, String userId) {

        return gameActionContextService.getGameActionVo(gameId, userId);
    }
}
