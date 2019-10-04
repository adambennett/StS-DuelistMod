package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.cards.tempCards.*;
import duelistmod.powers.SummonPower;
import duelistmod.relics.Leafblower;
import duelistmod.variables.Tags;

public class LeavesPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("LeavesPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("LeavesPower.png");
    
	public LeavesPower(int leaves)
	{ 
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
        this.amount = leaves;
        this.amount2 = 0;
        updateDescription(); 
	}
	
	@Override
	public void atStartOfTurn()
	{
		updateDescription();
		if (this.amount2 > 0 && this.amount >= 5)
		{
			ArrayList<DuelistCard> choices = new ArrayList<DuelistCard>();
			choices.add(new VineBlockCard(this.amount2));
			choices.add(new LeafBlockCard(this.amount2/2.0f, this.amount/2.0f));
			choices.add(new SplendidCancel());
			AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(choices, 1));
		}
	}
	
	public void resetLeaves()
	{
		this.amount = 0;
		this.amount2 = 0;
		updateDescription();
	}
	
	public void halfReset(int loss)
	{
		this.amount -= loss;
		updateDescription();
	}

	@Override
	public void onTribute(DuelistCard c, DuelistCard c2) { updateDescription(); }
	
	@Override
	public void onSummon(DuelistCard c, int amt) { updateDescription(); }
	
	@Override
	public void onSynergyTribute() { updateDescription(); }

	@Override
	public void updateDescription()
	{
		calcDamage();
		if (this.amount2 < 0) { this.amount2 = 0; }
		if (this.amount < 0)  { this.amount  = 0; }
		this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1];
	}
	
	private void calcDamage()
	{
		AbstractPlayer p = AbstractDungeon.player;
		int natsSummoned = 0;
		if (p.hasPower(SummonPower.POWER_ID)) { SummonPower pow = (SummonPower)p.getPower(SummonPower.POWER_ID); natsSummoned = pow.getNumberOfTypeSummoned(Tags.NATURIA); }
		if (natsSummoned > 0)
		{
			float mod = natsSummoned * 0.5f;
			int dmg = (int)(mod * this.amount);
			if (p.hasRelic(Leafblower.ID)) { dmg = (int)(dmg * 1.2f); }
			if (p.hasPower(NaturiaForestPower.POWER_ID) && p.hasPower(DexterityPower.POWER_ID)) 
			{
				int amt = p.getPower(DexterityPower.POWER_ID).amount;
				float modi = (amt/20.0f) + 1.0f;
				dmg = (int)(dmg * modi);
			}
			this.amount2 = dmg;
		}
		else { this.amount2 = 0; }
	}
}
