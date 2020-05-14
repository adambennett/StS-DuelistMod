package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.RandomActionHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.speedster.SpeedsterUtil.UC;
import duelistmod.speedster.actions.BeginSpeedModeAction;
import duelistmod.speedster.mechanics.*;
import duelistmod.variables.Tags;

public class MegaGlitchToken extends TokenCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("MegaGlitchToken");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("GoldGadget.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.SPECIAL;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST;
	private static final int COST = 2;
	// /STAT DECLARATION/

	public MegaGlitchToken() 
	{ 
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
		this.tags.add(Tags.TOKEN);
		this.tags.add(Tags.MACHINE);
		this.purgeOnUse = true;
		this.summons = this.baseSummons = 1;
	}
	public MegaGlitchToken(String tokenName) 
	{ 
		super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
		this.tags.add(Tags.TOKEN);
		this.tags.add(Tags.MACHINE);
		this.purgeOnUse = true;
		this.summons = this.baseSummons = 1;
	}
	@Override public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon();
		if (DuelistMod.quicktimeEventsAllowed)
		{
			//UC.atb(new BeginSpeedModeAction(new SpeedClickEnemyTime(3.0f, mon -> UC.doDmg(mon, damage, DamageInfo.DamageType.NORMAL, UC.getSpeedyAttackEffect(), true))));
			Runnable myRunnable = () -> {
				System.out.println("Ran");
				//UC.doVfx(new RainbowCardEffect());
				RandomActionHelper.triggerRandomAction(1, false, true, false);
			};
			UC.atb(new BeginSpeedModeAction(new SpeedClickButtonTime(8.0f, myRunnable, new BasicButtonGenerator(0.6f, true)), 2));
		}
		else if (roulette()) 
		{
			RandomActionHelper.triggerRandomAction(1, false, true, true);
			RandomActionHelper.triggerRandomAction(1, false, true, true);
		}
	}
	@Override public AbstractCard makeCopy() { return new MegaGlitchToken(); }



	@Override public void onTribute(DuelistCard tributingCard) 
	{
		machineSynTrib(tributingCard);
	}

	@Override public void onResummon(int summons) 
	{ 

	}

	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }

	@Override public void upgrade() 
	{
		if (canUpgrade()) {
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
			else { this.upgradeName(NAME + "+"); }
			this.upgradeBaseCost(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	@Override
	public String getID() {
		return ID;
	}
	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub

	}
}