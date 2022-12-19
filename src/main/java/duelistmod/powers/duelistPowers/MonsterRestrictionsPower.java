package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.*;
import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.variables.*;

public class MonsterRestrictionsPower extends DuelistPower
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("MonsterRestrictionsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("NoResummonPower.png");

    private final int typeKey;
    private final int typeKeyMod;
    private final CostType calculatedType;
    private final CostMod calculatedMod;
    private final int costAmount;

    public MonsterRestrictionsPower(final AbstractCreature owner, final AbstractCreature source, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amount;
        this.typeKey = AbstractDungeon.cardRandomRng.random(4, 6);
        this.typeKeyMod = this.typeKey == 6 ? 7 : AbstractDungeon.cardRandomRng.random(7, 8);
        this.costAmount = AbstractDungeon.cardRandomRng.random(1, 3);
        switch (this.typeKey) {
            case 4:
                this.calculatedType = CostType.ENERGY;
                break;
            case 5:
                this.calculatedType = CostType.SUMMON;
                break;
            default:
                this.calculatedType = CostType.TRIBUTE;
                break;
        }
        this.calculatedMod = this.typeKeyMod == 7 ? CostMod.MORE_THAN : CostMod.LESS_THAN;
        this.updateDescription();
    }

    @Override
    public boolean modifyCanUse(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) {
        if (!card.hasTag(Tags.MONSTER)) {
            return true;
        }
        boolean moreThan = this.calculatedMod == CostMod.MORE_THAN;
        switch (this.calculatedType) {
            case ENERGY:
                return ((moreThan) && card.costForTurn < this.costAmount) || (card.costForTurn > this.costAmount);
            case SUMMON:
                return ((moreThan) && card.summonsForTurn < this.costAmount) || (card.summonsForTurn > this.costAmount);
            default:
                return ((moreThan) && card.tributesForTurn < this.costAmount) || (card.tributesForTurn > this.costAmount);
        }
    }

    @Override
    public String cannotUseMessage(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) {
        String costType = DESCRIPTIONS[typeKey];
        String costMod  = DESCRIPTIONS[typeKeyMod];
        return "Cannot play monsters " + costType + costMod + this.costAmount + (amount == 1 ? " until next turn." : " for the next " + amount + " turns");
    }
    
    @Override
	public void atEndOfRound()
	{
		if (this.amount > 0) { this.amount--; }
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
		updateDescription();
	}
    
    @Override
	public void updateDescription() 
    {
        String baseDescription = DESCRIPTIONS[0];
        String turns = this.amount == 1 ? DESCRIPTIONS[2] : (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3]);
        String costType = DESCRIPTIONS[typeKey];
        String costMod  = DESCRIPTIONS[typeKeyMod];
        this.description = baseDescription + costType + costMod + this.costAmount + turns;
    }

    private enum CostType {
        ENERGY,
        SUMMON,
        TRIBUTE
    }

    private enum CostMod {
        MORE_THAN,
        LESS_THAN
    }
}
