package com.jxb.wrm.game.context;

import com.jxb.wrm.game.constant.EnumGameStatus;
import com.jxb.wrm.game.po.Card;
import com.jxb.wrm.game.po.Player;
import com.jxb.wrm.game.service.SocketInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwenping on 2018/6/22.
 */
@Data
public class GameContext {

    private EnumGameStatus gameStatus;

    private String gameId;

    private String creatorId;

    private SocketInfo socketInfo;

    private String currentUserId;

    private String currentUserName;

    private Integer playerCount;

    private List<Player> playerList = new ArrayList<>();

    private Integer currentCardIndex;

    private List<Card> cardList;


    public Player getPlayerById(String userId){

        List<Player> players = this.getPlayerList();
        if(players == null){
            return null;
        }
        for(Player player:players){
            if(player.getId().equals(userId)){
                return player;
            }
        }
        return null;
    }


}
