package duelistmod.speedster.SpeedsterUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.BurstMechanics;
import duelistmod.speedster.actions.DamageAllAction;

public class UC {
    //Common references
    public static AbstractPlayer p() {
        return AbstractDungeon.player;
    }
    private static DecimalFormat twoDecFormat = new DecimalFormat("#0.00");
    public static GlyphLayout layout = new GlyphLayout();

    //Checks
    public static boolean checkBurst() {
        boolean tmp = BurstMechanics.PlayerBurstField.isBurst.get(p());
        if(tmp) {
            incrementTurnBurstAmount();
        }
        return tmp;
    }

    public static boolean anonymousCheckBurst() {
        return BurstMechanics.PlayerBurstField.isBurst.get(p());
    }

    //Actionmanager
    public static void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
    
    public static void doMegatype()
    {
    	DuelistCard.runRandomTributeSynergy();
    }

    //Do common effect
    public static void doDmg(AbstractCreature target, int amount) {
        doDmg(target, amount, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt) {
        doDmg(target, amount, dt, AbstractGameAction.AttackEffect.NONE);
    }

    public static void doDmg(AbstractCreature target, int amount, AbstractGameAction.AttackEffect ae) {
        doDmg(target, amount, DamageInfo.DamageType.NORMAL, ae);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt, AbstractGameAction.AttackEffect ae) {
        doDmg(target, amount, dt, ae, false);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt, AbstractGameAction.AttackEffect ae, boolean fast) {
        doDmg(target, amount, dt, ae, fast, false);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt, AbstractGameAction.AttackEffect ae, boolean fast, boolean top) {
        if (top) {
            att(new DamageAction(target, new DamageInfo(p(), amount, dt), ae, fast));
        } else {
            atb(new DamageAction(target, new DamageInfo(p(), amount, dt), ae, fast));
        }
    }

    public static void doAllDmg(int amount, AbstractGameAction.AttackEffect ae, boolean top) {
        if (top) {
            att(new DamageAllAction(p(), amount, false, DamageInfo.DamageType.NORMAL, ae, false));
        } else {
            atb(new DamageAllAction(p(), amount, false, DamageInfo.DamageType.NORMAL, ae, false));
        }
    }

    public static void doDef(int amount) {
        atb(new GainBlockAction(p(), p(), amount));
    }

    public static void doPow(AbstractCreature target, AbstractPower p) {
        doPow(target, p, false);
    }

    public static void doPow(AbstractCreature target, AbstractPower p, boolean top) {
        doPow(UC.p(), target, p, top);
    }

    public static void doPow(AbstractCreature source, AbstractCreature target, AbstractPower p, boolean top) {
        if (top) {
            att(new ApplyPowerAction(target, source, p, p.amount));
        } else {
            atb(new ApplyPowerAction(target, source, p, p.amount));
        }
    }

    public static void doVfx(AbstractGameEffect gameEffect) {
        atb(new VFXAction(gameEffect));
    }

    public static void doVfx(AbstractGameEffect gameEffect, float duration) {
        atb(new VFXAction(gameEffect, duration));
    }

    //Getters
    public static AbstractGameAction.AttackEffect getSpeedyAttackEffect() {
        int effect = MathUtils.random(0, 4);
        switch(effect) {
            case 0:
                return AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
            case 1:
                return AbstractGameAction.AttackEffect.SLASH_VERTICAL;
            case 2:
                return AbstractGameAction.AttackEffect.BLUNT_LIGHT;
            default:
                return AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
        }
    }

    public static Color getRandomFireColor() {
        int i = MathUtils.random(3);
        switch (i) {
            case 0:
                return Color.ORANGE;
            case 1:
                return Color.YELLOW;
            default:
                return Color.RED;
        }
    }

    public static ArrayList<AbstractMonster> getAliveMonsters() {
        return AbstractDungeon.getMonsters().monsters.stream().filter(m -> !m.isDeadOrEscaped()).collect(Collectors.toCollection(ArrayList::new));
    }

    public static int getLogicalCardCost(AbstractCard c) {
        if (c.costForTurn > 0 && !c.freeToPlayOnce) {
            return c.costForTurn;
        }
        return 0;
    }

    public static int getTurnBurstAmount() {
        return BurstMechanics.PlayerBurstField.turnBurstAmount.get(p());
    }

    public static String get2DecString(float num) {
        if(num <0) {
            num = 0;
        }
        return twoDecFormat.format(AlchHelper.round(num, 2));
    }

    public static float gt() {
        return Gdx.graphics.getRawDeltaTime();
    }

    //Setters
    public static void incrementTurnBurstAmount() {
        BurstMechanics.PlayerBurstField.turnBurstAmount.set(p(), getTurnBurstAmount() + 1);
    }

    public static <T> boolean True(T t) {
        return true;
    }

    //Display
    public static void displayTimer(SpriteBatch sb, String msg, float y, Color color) {
        String tmp = msg.replaceAll("\\d", "0");
        layout.setText(FontHelper.SCP_cardEnergyFont, tmp);
        float baseBox = layout.width;
        layout.setText(FontHelper.SCP_cardEnergyFont, msg);
        sb.setColor(Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR);
        //sb.draw(ImageMaster.WHITE_SQUARE_IMG, Settings.WIDTH / 2.0F - baseBox / 2.0F - 12.0F * Settings.scale, y - 24.0F * Settings.scale, baseBox + 24.0F * Settings.scale, layout.height * Settings.scale);
        FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, msg, (Settings.WIDTH / 2.0F) - baseBox / 2.0F, y + layout.height / 2.0F, color);
        //FontHelper.renderFontCentered(sb, FontHelper.SCP_cardEnergyFont, msg, Settings.WIDTH / 2.0F, y, color);
    }
}
