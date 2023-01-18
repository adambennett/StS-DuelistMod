package duelistmod.cards.other.tempCards;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.StrengthDownPower;
import duelistmod.variables.Tags;

public class SplendidConfirmCard extends DuelistCard 
{
	
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("SplendidConfirmCard");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("SplendidRoseSkill.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = -2;
    // /STAT DECLARATION/
    
    public SplendidConfirmCard(int magic)
    {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
       	this.purgeOnUse = true;
    	this.dontTriggerOnUseCard = true;
    	this.magicNumber = this.baseMagicNumber = magic;
		CommonKeywordIconsField.useIcons.set(this, false);
    }


    @Override public AbstractCard makeCopy() { return new SplendidConfirmCard(this.magicNumber); } 
    @Override public AbstractCard makeStatEquivalentCopy() { return new SplendidConfirmCard(this.magicNumber); }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<DuelistCard> plants = new ArrayList<DuelistCard>();
    	for (AbstractCard c : p.discardPile.group)
    	{
    		if (c.hasTag(Tags.PLANT) && allowResummonsWithExtraChecks(c))
    		{
    			plants.add((DuelistCard) c);
    		}
    	}
    	
    	if (plants.size() > 0)
    	{    		
    		AbstractCard randomPlant = plants.get(AbstractDungeon.cardRandomRng.random(plants.size() - 1));
    		AbstractMonster mon = AbstractDungeon.getRandomMonster();
    		if (mon != null)
    		{
	    		AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(randomPlant, p.discardPile));
	    		applyPower(new StrengthDownPower(mon, p, 1, this.magicNumber), mon);
    		}
    	}
    }   
	
	@Override public void upgrade()  {}	
	
}
