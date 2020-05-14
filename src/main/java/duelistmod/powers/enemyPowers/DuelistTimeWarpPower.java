package duelistmod.powers.enemyPowers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;

import duelistmod.abstracts.DuelistPower;

public class DuelistTimeWarpPower extends DuelistPower
{
    public static final String POWER_ID = "Time Warp";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;
    private final int str;
    private final int cards;
    
    public DuelistTimeWarpPower(final AbstractCreature owner, int cards, int str) {
        this.name = DuelistTimeWarpPower.NAME;
        this.ID = "Time Warp";
        this.owner = owner;
        this.amount = 0;
        this.str = str;
        this.cards = cards;
        this.updateDescription();
        this.loadRegion("time");
        this.type = PowerType.BUFF;
    }
    
  
    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05f);
    }
    
    @Override
    public void updateDescription() {
        this.description = DuelistTimeWarpPower.DESC[0] + this.cards + DuelistTimeWarpPower.DESC[1] + this.str + DuelistTimeWarpPower.DESC[2];
    }
    
    @Override
    public void onAfterUseCard(final AbstractCard card, final UseCardAction action) {
        this.flashWithoutSound();
        ++this.amount;
        if (this.amount == this.cards) {
            this.amount = 0;
            this.playApplyPowerSfx();
            AbstractDungeon.actionManager.callEndTurnEarlySequence();
            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05f);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
            AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
            for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, this.str), this.str));
            }
        }
        this.updateDescription();
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Time Warp");
        NAME = DuelistTimeWarpPower.powerStrings.NAME;
        DESC = DuelistTimeWarpPower.powerStrings.DESCRIPTIONS;
    }
}
