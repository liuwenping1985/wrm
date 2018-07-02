package com.jxb.wrm.game.controller;

import com.jxb.wrm.game.context.BaseCardGenerator;
import com.jxb.wrm.game.po.Card;
import com.jxb.wrm.game.service.WanRenMiService;
import com.jxb.wrm.game.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwenping on 2018/6/22.
 */
@RestController
@RequestMapping({"/v0.1/gateway/wrm", "/v0.2/gwc/wrm"})
public class WanRenMiController {

    @Autowired
    private WanRenMiService wanRenMiService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultVo getGame(@RequestParam String gameId) {
        ResultVo rv = wanRenMiService.getGameContext(gameId);
        return rv;
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultVo createGame(@RequestBody GameCreateVo gameCreateVo) {
        ResultVo rv = wanRenMiService.createGame(gameCreateVo);

        return rv;
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResultVo joinGame(@RequestBody GameJoinVo gameJoinVo) {

        ResultVo rv = wanRenMiService.joinGame(gameJoinVo);
        return rv;
    }

    @RequestMapping(value = "/restart", method = RequestMethod.POST)
    public ResultVo restartGame(@RequestBody BaseGameVo baseGameVo) {

        ResultVo rv = wanRenMiService.restartGame(baseGameVo.getGameId());


        return rv;
    }

    @RequestMapping(value = "/destroy", method = RequestMethod.POST)
    public ResultVo destroyGame(@RequestBody BaseGameVo baseGameVo) {

        return wanRenMiService.destroyGame(baseGameVo.getGameId());

    }

    @RequestMapping(value = "/play/action/game/start", method = RequestMethod.POST)
    public ResultVo startGame(@RequestBody BaseGameVo baseGameVo) {

        return wanRenMiService.startGame(baseGameVo.getGameId());

    }


    @RequestMapping(value = "/play/action/card/using", method = RequestMethod.POST)
    public ResultVo usingCard(@RequestBody GamePlayVo gamePlayVo) {


        return wanRenMiService.usingCard(gamePlayVo);

    }

    @RequestMapping(value = "/play/action/card/draw", method = RequestMethod.POST)
    public ResultVo drawCard(@RequestBody GamePlayVo gamePlayVo) {

        return wanRenMiService.drawCard(gamePlayVo);

    }

    @RequestMapping(value = "/player/heart/beats", method = RequestMethod.POST)
    public Map heartBeats(@RequestBody BaseGameVo baseGameVo) {
        Map<String, Object> data = new HashMap<String, Object>();
        boolean isOk = wanRenMiService.heartBeats(baseGameVo);
        data.put("result", isOk);

        return data;
    }


    @RequestMapping(value = "/player/ready", method = RequestMethod.POST)
    public ResultVo playerReady(@RequestBody BaseGameVo baseGameVo) {
        return wanRenMiService.playerReady(baseGameVo);

    }

    @RequestMapping(value = "/player/check", method = RequestMethod.GET)
    public ResultVo checkRequest(@RequestParam String userId) {
        ResultVo vo = new ResultVo();
        vo.setMsg("yes");
        vo.setResult(true);
        return vo;

    }

    @RequestMapping(value = "/play/card/base", method = RequestMethod.GET)
    public Map getBaseCardList() {
        HashMap<String, Object> data = new HashMap<>();
        List<Card> cardList = BaseCardGenerator.genBase12Cards();
        data.put("items", cardList);
        return data;

    }

    /**
     * 获取用户动作列表--采用轮询
     * -- todo:改进采用socket
     *
     * @param gameId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/play/action/fetch", method = RequestMethod.GET)
    public Map getActionList(@RequestParam String gameId, @RequestParam String userId) {
        HashMap<String, Object> data = new HashMap<>();

        List<GameActionVo> actionVoList = wanRenMiService.getGameActionVo(gameId, userId);
        if (actionVoList == null || actionVoList.isEmpty()) {
            data.put("needUpdate", false);
        } else {
            data.put("needUpdate", true);
        }
        data.put("actions", actionVoList);

        return data;

    }


}
