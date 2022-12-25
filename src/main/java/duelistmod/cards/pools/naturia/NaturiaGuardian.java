package duelistmod.cards.pools.naturia;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class NaturiaGuardian extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("NaturiaGuardian");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.NATURIA_GUARDIAN);
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

    public NaturiaGuardian() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 10;
        this.summons = this.baseSummons = 2;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.NATURIA);
        this.tags.add(Tags.PLANT);
        this.originalName = this.name;
        this.isSummon = true;
        this.baseMagicNumber = this.magicNumber = 1;
        this.secondMagic = this.baseSecondMagic = 8;
        this.cardsToPreview = new NaturiaHorneedle();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	attack(m, this.baseAFX, this.damage);
    	for (int i = 0; i < this.magicNumber; i++)
    	{
			DuelistCard uNH = new NaturiaHorneedle();
			if (AbstractDungeon.cardRandomRng.random(1, this.secondMagic) == 1) { uNH.upgrade(); }
			this.addToBot(new MakeTempCardInDrawPileAction(uNH, 1, true, true));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new NaturiaGuardian();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.upgradeSecondMagic(-2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		naturiaSynTrib(tributingCard);
		plantSynTrib(tributingCard);
	}

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{

	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
	
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
