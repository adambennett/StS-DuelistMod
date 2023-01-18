package duelistmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

public class InjectionFairy extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = duelistmod.DuelistMod.makeID("InjectionFairy");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.INJECTION_FAIRY);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public InjectionFairy() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.summons = 1;
		this.baseMagicNumber = this.magicNumber = 4;
		this.baseDamage = this.damage = 6;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.LEGACY_DARKNESS);
		this.tags.add(Tags.SPELLCASTER);
		this.tags.add(Tags.HEAL_DECK);
		this.tags.add(Tags.ORIGINAL_HEAL_DECK);
        this.tags.add(Tags.NEVER_GENERATE);
	    this.startingOPHDeckCopies = 1;
        this.healDeckCopies = 1;
		this.originalName = this.name;
		this.summons = this.baseSummons = 1;
		this.isSummon = true;
		this.setupStartingCopies();
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower power = (SummonPower) p.getPower(SummonPower.POWER_ID);
			if (power.isEveryMonsterCheck(Tags.SPELLCASTER, false))
			{
				heal(p, this.magicNumber);
			}
		}
		attack(m, AFX, this.damage); 
		summon(p, this.summons, this);
	}
	
    @Override
    public void triggerOnGlowCheck()
    {
    	super.triggerOnGlowCheck();
    	boolean dealExtra = false;
    	if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
    	{
    		SummonPower instance = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
    		dealExtra = instance.isEveryMonsterCheck(Tags.SPELLCASTER, false);
    	}
        if (dealExtra) {
        	 this.glowColor = Color.GOLD;
        }
    }

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new InjectionFairy();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(3);
			this.upgradeMagicNumber(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
	






	

	






}
