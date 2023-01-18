package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.relics.CardPoolOptionsRelic;
import duelistmod.variables.Strings;

public class CardPoolOptionResetSave extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CardPoolOptionResetSave");
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

    public CardPoolOptionResetSave() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.dontTriggerOnUseCard = true;
    }
   
    // Call this when player selects card from Options Relic
    public void loadPool()
    {
    	if (AbstractDungeon.player.hasRelic(CardPoolOptionsRelic.ID))
    	{
    		CardPoolOptionsRelic rel = (CardPoolOptionsRelic)AbstractDungeon.player.getRelic(CardPoolOptionsRelic.ID);
    		for (AbstractCard c : rel.pool.group)
    		{
	    		if (c instanceof CardPoolOptionSaveA) { 
					CardPoolOptionSaveA ca = (CardPoolOptionSaveA)c;
					ca.resetPool();
				}
				else if (c instanceof CardPoolOptionSaveB) { 
					CardPoolOptionSaveB ca = (CardPoolOptionSaveB)c;
					ca.resetPool();
				}
				else if (c instanceof CardPoolOptionSaveC) { 
					CardPoolOptionSaveC ca = (CardPoolOptionSaveC)c;
					ca.resetPool();
				}
    		}
    	}
    }
    
    /*@Override
    public void update()
    {
    	super.update();
    	if (this.savedPool != null) { this.magicNumber = this.baseMagicNumber = this.savedPool.size(); }
    }*/

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	
    }
    @Override public AbstractCard makeCopy() { return new CardPoolOptionResetSave(); }

    
    
	
	@Override public void upgrade() {}
	


}
