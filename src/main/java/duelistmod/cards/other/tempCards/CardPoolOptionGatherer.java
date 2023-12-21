package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.crossover.GathererHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.relics.CardPoolRelic;
import duelistmod.variables.Strings;

public class CardPoolOptionGatherer extends CardPoolOptionTypeCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CardPoolOptionGatherer");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GENERIC_TOKEN);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public 	static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    // /STAT DECLARATION/

    public CardPoolOptionGatherer() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.dontTriggerOnUseCard = true;
    	this.canAdd = DuelistMod.isGatherer;
    }
   
    // Call this when player selects card from Options Relic
    @Override
    public void loadPool()
    {
    	DuelistMod.coloredCards.clear();
		DuelistMod.toReplacePoolWith.clear();
		DuelistMod.toReplacePoolWith.addAll(GathererHelper.getAllCards());
		DuelistMod.toReplacePoolWith.addAll(TheDuelist.cardPool.group);
		DuelistMod.shouldReplacePool = true;
		DuelistMod.relicReplacement = true;
		if (AbstractDungeon.player.hasRelic(CardPoolRelic.ID)) { ((CardPoolRelic)AbstractDungeon.player.getRelic(CardPoolRelic.ID)).setDescription(); }
		CardCrawlGame.dungeon.initializeCardPools();
    }
    
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	
    }
    @Override public AbstractCard makeCopy() { return new CardPoolOptionGatherer(); }

    
    
	
	@Override public void upgrade() {}
	


}
