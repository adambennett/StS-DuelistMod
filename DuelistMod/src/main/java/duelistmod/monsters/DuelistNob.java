package duelistmod.monsters;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.badlogic.gdx.math.*;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.DuelistMod;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.core.*;

public class DuelistNob extends AbstractMonster
{
    public static final String ID = DuelistMod.makeID("DuelistNob");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 82;
    private static final int HP_MAX = 86;
    private static final int A_2_HP_MIN = 85;
    private static final int A_2_HP_MAX = 90;
    private static final int BASH_DMG = 6;
    private static final int RUSH_DMG = 14;
    private static final int A_2_BASH_DMG = 8;
    private static final int A_2_RUSH_DMG = 16;
    private static final int DEBUFF_AMT = 2;
    private int bashDmg;
    private int rushDmg;
    private static final byte BULL_RUSH = 1;
    private static final byte SKULL_BASH = 2;
    private static final byte BELLOW = 3;
    private static final int ANGRY_LEVEL = 2;
    private boolean usedBellow;
    private boolean canVuln;
    
    public DuelistNob(final float x, final float y) {
        this(x, y, true);
    }
    
    public DuelistNob(final float x, final float y, final boolean setVuln) {
        super(DuelistNob.NAME, "DuelistNob", 86, -70.0f, -10.0f, 270.0f, 380.0f, null, x, y);
        this.usedBellow = false;
        this.intentOffsetX = -30.0f * Settings.scale;
        this.type = EnemyType.ELITE;
        this.dialogX = -60.0f * Settings.scale;
        this.dialogY = 50.0f * Settings.scale;
        this.canVuln = setVuln;
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(85, 90);
        }
        else {
            this.setHp(82, 86);
        }
        if (AbstractDungeon.ascensionLevel >= 3) {
            this.bashDmg = 8;
            this.rushDmg = 16;
        }
        else {
            this.bashDmg = 6;
            this.rushDmg = 14;
        }
        this.damage.add(new DamageInfo(this, this.rushDmg));
        this.damage.add(new DamageInfo(this, this.bashDmg));
        this.loadAnimation("duelistModResources/images/monsters/DuelistNob/skeleton.atlas", "duelistModResources/images/monsters/DuelistNob/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 3: {
                this.playSfx();
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DuelistNob.DIALOG[0], 1.0f, 3.0f));
                if (AbstractDungeon.ascensionLevel >= 18) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AngerPower(this, 3), 3));
                    break;
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AngerPower(this, 2), 2));
                break;
            }
            case 2: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                if (this.canVuln) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                    break;
                }
                break;
            }
            case 1: {
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    private void playSfx() {
        final int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1A"));
        }
        else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1B"));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1C"));
        }
    }
    
    @Override
    protected void getMove(final int num) {
        if (!this.usedBellow) {
            this.usedBellow = true;
            this.setMove((byte)3, Intent.BUFF);
            return;
        }
        if (AbstractDungeon.ascensionLevel >= 18) {
            if (!this.lastMove((byte)2) && !this.lastMoveBefore((byte)2)) {
                if (this.canVuln) {
                    this.setMove(DuelistNob.MOVES[0], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
                }
                else {
                    this.setMove((byte)2, Intent.ATTACK, this.damage.get(1).base);
                }
                return;
            }
            if (this.lastTwoMoves((byte)1)) {
                if (this.canVuln) {
                    this.setMove(DuelistNob.MOVES[0], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
                }
                else {
                    this.setMove((byte)2, Intent.ATTACK, this.damage.get(1).base);
                }
            }
            else {
                this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
            }
        }
        else {
            if (num < 33) {
                if (this.canVuln) {
                    this.setMove(DuelistNob.MOVES[0], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
                }
                else {
                    this.setMove((byte)2, Intent.ATTACK, this.damage.get(1).base);
                }
                return;
            }
            if (this.lastTwoMoves((byte)1)) {
                if (this.canVuln) {
                    this.setMove(DuelistNob.MOVES[0], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
                }
                else {
                    this.setMove((byte)2, Intent.ATTACK, this.damage.get(1).base);
                }
            }
            else {
                this.setMove((byte)1, Intent.ATTACK, this.damage.get(0).base);
            }
        }
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = DuelistNob.monsterStrings.NAME;
        MOVES = DuelistNob.monsterStrings.MOVES;
        DIALOG = DuelistNob.monsterStrings.DIALOG;
    }
}
