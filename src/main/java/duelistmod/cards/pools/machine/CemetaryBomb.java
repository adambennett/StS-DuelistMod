package duelistmod.cards.pools.machine;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.ExplosiveToken;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class CemetaryBomb extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("CemetaryBomb");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CemetaryBomb.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public CemetaryBomb() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);   
        this.summons = this.baseSummons = 4;	
        this.baseMagicNumber = this.magicNumber = 3;
        this.originalName = this.name;
        this.cardsToPreview = new ExplosiveToken();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new ExplosiveToken());
    	summon(p, this.summons, tok);
    	ArrayList<AbstractCard> discardMons = new ArrayList<>();
    	for (AbstractCard c : p.discardPile.group)
    	{
    		if (c.hasTag(Tags.MONSTER) && allowResummonsWithExtraChecks(c)) { discardMons.add(c.makeStatEquivalentCopy()); }
    	}
    	while (discardMons.size() > this.magicNumber && discardMons.size() > 0) {
    		discardMons.remove(AbstractDungeon.cardRandomRng.random(discardMons.size() - 1));
    	}
    	
    	for (AbstractCard c : discardMons)
    	{
    		resummon(c, m);
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CemetaryBomb();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.upgradeSummons(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }






	






}
