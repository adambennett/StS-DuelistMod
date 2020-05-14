package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.*;
import duelistmod.orbs.*;


@SuppressWarnings("unused")
public class StatueAnguishPatternPower extends DuelistPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("StatueAnguishPatternPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("StatueAnguishPatternPower.png");
	private boolean finished = false;
	private AbstractOrb orb = new AirOrb();
	private ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
	
	public StatueAnguishPatternPower(final AbstractCreature owner, final AbstractCreature source) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = 0;
		this.orbs = statueOrbs();
		updateDescription();
	}
	
	public StatueAnguishPatternPower(final AbstractCreature owner, final AbstractCreature source, AbstractOrb orb) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = 0;
		this.orb = orb;
		this.orbs = statueOrbs();
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		if (this.orb != null)
		{
			// Professional Vowel Handler
			if (this.orb instanceof AirOrb || this.orb instanceof Earth) { this.description = DESCRIPTIONS[2] + this.orb.name + DESCRIPTIONS[1]; }
			else { this.description = DESCRIPTIONS[0] + this.orb.name + DESCRIPTIONS[1]; }
		}
		else
		{
			this.description = "Uh-oh. Null orb. Report me to Nyoxide immediately!!"; 
		}		
	}

	@Override
	public void atStartOfTurn() 
	{
		if (this.orb != null)
		{
			AbstractOrb toChannel = this.orb.makeCopy();
			DuelistCard.channel(toChannel);	
		}	
		else { Util.log("Statue of Anguish Pattern failed to channel because this.orb was null"); }
	}

	@Override
	public void onPlayCard(AbstractCard card, AbstractMonster m) 
	{
		if (this.orbs.size() > 0)
		{
			this.orbs = statueOrbs();
			AbstractOrb o = this.orbs.get(AbstractDungeon.cardRandomRng.random(this.orbs.size() - 1));
			while (o.name.equals(this.orb.name)) { o = this.orbs.get(AbstractDungeon.cardRandomRng.random(this.orbs.size() - 1)); }
			this.orb = o.makeCopy();
			updateDescription();
		}
		else { Util.log("Statue of Anguish Pattern failed to channel because this.orbs.size() < 1"); }
	}
	
    @Override
    public void onEnemyUseCard(AbstractCard card)
    {
    	onPlayCard(card, null);
    }
	
	private ArrayList<AbstractOrb> statueOrbs()
	{
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ return RandomOrbHelperDualMod.channelStatue(); }
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ return RandomOrbHelperCon.channelStatue(); }
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { return RandomOrbHelperRep.channelStatue(); }
		else { return RandomOrbHelper.channelStatue(); }
	}
}
