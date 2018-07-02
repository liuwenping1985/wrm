package com.jxb.wrm.game.context;

import com.jxb.wrm.game.constant.EnumCardType;
import com.jxb.wrm.game.po.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by liuwenping on 2018/6/25.
 */
public final class BaseCardGenerator {


    private static List<Card> CARD_LIST = new ArrayList<>();


    public static List<Card> genBase12Cards() {

        if (CARD_LIST.size() == 12) {
            return CARD_LIST;
        }
        /**
         * 录像机、摄像机、厕所牌和摸鼻子可以不立即使用
         */
        Random rand = new Random(System.currentTimeMillis());

        Card bgx = new Card();
        bgx.setCardType(EnumCardType.TYPE_BEI_GUO_XIA);
        bgx.setCardName("背锅侠");
        bgx.setUrl("https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardbgx.png");
        bgx.setCardNo("1");
        bgx.setRandomSeed(rand.nextInt(48));
        CARD_LIST.add(bgx);

        Card csp = new Card();
        csp.setCardType(EnumCardType.TYPE_CE_SUO_PAI);
        csp.setCardName("厕所牌");
        csp.setUrl("https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardcsp.png");
        csp.setCardNo("2");
        csp.setRandomSeed(rand.nextInt(48));
        csp.setCanUseLater(true);
        CARD_LIST.add(csp);

        Card gsy = new Card();
        gsy.setCardType(EnumCardType.TYPE_GUANG_SAN_YUAN);
        gsy.setCardName("逛三园");
        gsy.setUrl("https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardgsy.png");
        gsy.setCardNo("3");
        gsy.setRandomSeed(rand.nextInt(48));
        CARD_LIST.add(gsy);

        Card hj = new Card();
        hj.setCardType(EnumCardType.TYPE_HE_JIU_PAI);
        hj.setCardName("喝酒牌");
        hj.setUrl("https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardhj.png");
        hj.setCardNo("4");
        hj.setRandomSeed(rand.nextInt(48));
        CARD_LIST.add(hj);

        Card j7p = new Card();
        j7p.setCardType(EnumCardType.TYPE_JIN_QI_PAI);
        j7p.setCardName("禁7牌");
        j7p.setUrl("https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardj7p.png");
        j7p.setCardNo("5");
        j7p.setRandomSeed(rand.nextInt(48));
        CARD_LIST.add(j7p);

        Card lxj = new Card();
        lxj.setCardType(EnumCardType.TYPE_LU_XIANG_JI);
        lxj.setCardName("录像机");
        lxj.setUrl("https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardlxj.png");
        lxj.setCardNo("6");
        lxj.setCanUseLater(true);
        lxj.setRandomSeed(rand.nextInt(48));
        CARD_LIST.add(lxj);


        Card mbz = new Card();
        mbz.setCardType(EnumCardType.TYPE_MO_BI_ZI);
        mbz.setCardName("摸鼻子");
        mbz.setUrl("https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardmbz.png");
        mbz.setCardNo("7");
        mbz.setCanUseLater(true);
        mbz.setRandomSeed(rand.nextInt(48));
        CARD_LIST.add(mbz);
        //https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardj7p.png

        Card zxj = new Card();
        zxj.setCardType(EnumCardType.TYPE_ZHAO_XIANG_JI);
        zxj.setCardName("照相机");
        zxj.setUrl("https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardphoto.png");
        zxj.setCardNo("8");
        zxj.setRandomSeed(rand.nextInt(48));
        zxj.setCanUseLater(true);
        CARD_LIST.add(zxj);

        Card sjb = new Card();
        sjb.setCardType(EnumCardType.TYPE_SHEN_JING_BIN);
        sjb.setCardName("深井冰");
        sjb.setUrl("https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardsjb.png");
        sjb.setCardNo("9");
        sjb.setRandomSeed(rand.nextInt(48));
        CARD_LIST.add(sjb);

        //https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardtjp.png
        Card tjp = new Card();
        tjp.setCardType(EnumCardType.TYPE_TI_JIU_PAI);
        tjp.setCardName("替酒牌");
        tjp.setUrl("https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardtjp.png");
        tjp.setCardNo("10");
        tjp.setRandomSeed(rand.nextInt(48));
        CARD_LIST.add(tjp);

        Card tqx = new Card();
        tqx.setCardType(EnumCardType.TYPE_TANG_QIANG_XIA);
        tqx.setCardName("躺枪侠");
        tqx.setUrl("https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardtqx.png");
        tqx.setCardNo("11");
        tqx.setRandomSeed(rand.nextInt(48));
        CARD_LIST.add(tqx);

        Card wrm = new Card();
        wrm.setCardType(EnumCardType.TYPE_WAN_REN_MI);
        wrm.setCardName("万人迷");
        wrm.setUrl("https://h5static.oss-cn-shenzhen.aliyuncs.com/xzwrm/cardwrm.png");
        wrm.setCardNo("12");
        wrm.setRandomSeed(rand.nextInt(48));
        CARD_LIST.add(wrm);
        return CARD_LIST;
    }

}
