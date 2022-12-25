package duelistmod.cards.pools.dragons;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class AncientGearGadjiltron extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("AncientGearGadjiltron");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("AncientGearGadjiltron.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 2;
	// /STAT DECLARATION/

	public AncientGearGadjiltron() 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.GOOD_TRIB);
		this.tags.add(Tags.MACHINE);
		this.tags.add(Tags.DRAGON);
		this.tags.add(Tags.ANCIENT_FOR_PIXIE);
		this.tags.add(Tags.ANCIENT_FOR_MACHINE);
		this.originalName = this.name;
		this.tributes = this.baseTributes = 5;
		this.baseDamage = this.damage = 20;
		this.magicNumber = this.baseMagicNumber = 3;
		this.baseAFX = AttackEffect.FIRE;
		this.cardsToPreview = new DragonicToken();
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		tribute();
		attack(m);	
		for (int i = 0; i < this.magicNumber; i++)
		{
	    	DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new DragonicToken());
			addCardToHand(tok);
		}		
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() 
	{
		return new AncientGearGadjiltron();
	}

	// Upgraded stats.
	@Override
	public void upgrade() 
	{
		if (!this.upgraded) 
		{
			this.upgradeName();
			this.upgradeTributes(-1);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
	
	



	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		machineSynTrib(tributingCard);
		dragonSynTrib(tributingCard);
	}


	@Override
	public void onResummon(int summons)
	{

	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		
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
