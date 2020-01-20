package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.rewards.BoosterPack.PackRarity;
import duelistmod.variables.Tags;

public class BoosterPackCard extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("BoosterPackCard");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BoosterPack.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_CRC;
    private static final int COST = -2;
    private String packName = "";
    private PackRarity packRare = PackRarity.COMMON;
    // /STAT DECLARATION/
    
    private static CardRarity getRarity(PackRarity rare)
    {
    	switch (rare)
    	{
    	case COMMON:
    		return CardRarity.COMMON;
		case RARE:
			return CardRarity.RARE;
		case SPECIAL:
			return CardRarity.RARE;
		case SUPER_RARE:
			return CardRarity.RARE;
		case UNCOMMON:
			return CardRarity.UNCOMMON;
		default:
			return CardRarity.COMMON;
    	}
    }
    
    private static String getDesc(String packName)
    {
    	return packName + " is currently a potential Booster Pack reward.";
    }

    public BoosterPackCard(String packName, PackRarity rare) 
    { 
    	super(ID, packName, IMG, COST, getDesc(packName), TYPE, COLOR, getRarity(rare), TARGET); 
    	this.tags.add(Tags.BOOSTER);
    	this.dontTriggerOnUseCard = true;
    	this.packName = packName;
    	this.packRare = rare;
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	
    }
    @Override public AbstractCard makeCopy() { return new BoosterPackCard(this.packName, this.packRare); }

    
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		
	}
	
	@Override public void onResummon(int summons) 
	{ 
		
	}
	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade() {}
	
	@Override
	public String getID() {
		return ID;
	}
	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}