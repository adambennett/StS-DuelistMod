package duelistmod.cards.incomplete;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.GlowColor;
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
import duelistmod.variables.Tags;

public class Reload extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Reload");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Reload.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public Reload() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.NEVER_GENERATE);
        this.makeFleeting();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<String> deckCards = new ArrayList<String>();
    	boolean foundDupe = false;
    	for (AbstractCard c : p.masterDeck.group)
    	{
    		String name = c.makeCopy().name;
    		if (deckCards.contains(name)) { foundDupe = true; Util.log("Reload found DUPLICATE of " + name); }
    		else { deckCards.add(name); Util.log("Reload found only 1 copy so far of " + name); }
    	}
    	if (!foundDupe && p.maxHealth - p.currentHealth > 0) { DuelistCard.heal(p, p.maxHealth - p.currentHealth); }
    	else { Util.log("Reload found duplicates of something, so you didn't heal buddy. Upgrading a copy of a card doesn't get you around this! NO DUPLICATES"); }
    }
    
    @Override
    public void triggerOnGlowCheck()
    {
    	ArrayList<String> deckCards = new ArrayList<String>();
    	boolean foundDupe = false;
    	for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
    	{
    		String name = c.makeCopy().name;
    		if (deckCards.contains(name)) { foundDupe = true;  }
    		else { deckCards.add(name);  }
    	}
        if (!foundDupe) {
            this.gColor = GlowColor.GOLD;
        }
        else {
            this.gColor = GlowColor.BLUE;
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Reload();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public boolean canUpgrade()
    {
    	return false;
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		// TODO Auto-generated method stub
		
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