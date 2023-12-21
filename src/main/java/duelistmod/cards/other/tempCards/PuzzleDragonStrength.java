package duelistmod.cards.other.tempCards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class PuzzleDragonStrength extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("PuzzleDragonStrength");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("PuzzleDragon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    // /STAT DECLARATION/

    public PuzzleDragonStrength() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.dontTriggerOnUseCard = true;
		this.tags.add(Tags.ALLOYED);
    	this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
    	for (AbstractMonster mon : mons)
    	{
    		applyPower(new StrengthPower(mon, -this.magicNumber), mon);
    	}
    }
    @Override public AbstractCard makeCopy() { return new PuzzleDragonStrength(); }

    
    
	
	@Override public void upgrade() {}
	


}
