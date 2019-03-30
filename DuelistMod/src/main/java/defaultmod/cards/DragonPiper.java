package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class DragonPiper extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("DragonPiper");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makeCardPath("Dragon_Piper.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
   // private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public DragonPiper() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.summons = 1;
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.ALL);
        this.originalName = this.name;
        this.isSummon = true;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	for (AbstractCard c : p.hand.group)
    	{
    		if (c.hasTag(DefaultMod.DRAGON) && c.hasTag(DefaultMod.MONSTER))
    		{
    			DuelistCard dC = (DuelistCard)c;
    			if (dC.tributes > 0)
    			{
    				dC.originalDescription = dC.rawDescription;
    				dC.tributes = 1;
    				dC.gotPipered = true;
    				int indexOfTribText = c.rawDescription.indexOf("Tribute");
    				if (indexOfTribText > 0)
    				{
    					String newDesc = c.rawDescription.substring(0, indexOfTribText) + "Tribute 1. NL " + c.rawDescription.substring(indexOfTribText + 14);
    					
    					c.rawDescription = newDesc;
    					if (DefaultMod.debug) { System.out.println("Dragon Piper made a string: " + newDesc); }
    					c.initializeDescription();
    				}
    				else
    				{
    					String newDesc = "Tribute 1. NL " + c.rawDescription.substring(indexOfTribText + 14);
    					
    					c.rawDescription = newDesc;
    					if (DefaultMod.debug) { System.out.println("Dragon Piper made a string: " + newDesc); }
    					c.initializeDescription();
    				}
    			}
    		}
    	}

    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DragonPiper();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
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