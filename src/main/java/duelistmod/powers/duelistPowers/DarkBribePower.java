package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.SummonPower;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.LIGHTNING;
import static com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL;

public class DarkBribePower extends DuelistPower {

	public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("DarkBribePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("DarkBribePower.png");
    private final AnyDuelist duelist;
    private boolean triggeredThisTurn;

	public DarkBribePower(AbstractCreature owner, AbstractCreature source, int amount) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amount;
        this.duelist = AnyDuelist.from(this);
		updateDescription();
	}

    @Override
    public void atStartOfTurnPostDraw() {
        this.triggeredThisTurn = false;
        if ((this.amount <= 0 || (this.duelist.hasPower(SummonPower.POWER_ID) && this.duelist.getPower(SummonPower.POWER_ID).amount >= this.amount))) {
            if (this.duelist.player()) {
                AbstractMonster attacker = AbstractDungeon.getMonsters().getRandomMonster(true);
                if (attacker != null) {
                    DamageInfo info = new DamageInfo(attacker, 1, NORMAL);
                    info.name = "DarkBribe";
                    info.applyPowers(attacker, this.duelist.creature());
                    this.addToBot(new DamageAction(this.duelist.creature(), info, LIGHTNING));
                    this.triggeredThisTurn = true;
                    if (this.amount > 0) {
                        DuelistCard.powerTribute(this.duelist.creature(), this.amount, false);
                    }
                }
            } else if (this.duelist.getEnemy() != null) {
                DamageInfo info = new DamageInfo(AbstractDungeon.player, 1, NORMAL);
                info.applyPowers(AbstractDungeon.player, this.duelist.creature());
                this.addToBot(new DamageAction(this.duelist.creature(), info, LIGHTNING));
                this.triggeredThisTurn = true;
                if (this.amount > 0) {
                    DuelistCard.powerTribute(this.duelist.creature(), this.amount, false);
                }
            }
        }
    }

    public void reduceAmount() {
        this.amount--;
        if (this.amount <= 0) {
            this.amount = 0;
        }
        this.updateDescription();
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (this.triggeredThisTurn) {
            this.addToTop(new HealAction(this.duelist.creature(), this.duelist.creature(), 1));
        }
    }


	@Override
	public void updateDescription() {
        if (this.amount <= 0) {
            this.amount = 0;
            this.description = DESCRIPTIONS[4];
        } else if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
	}

}
