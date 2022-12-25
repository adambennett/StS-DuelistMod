package duelistmod.cards.metronomes.typed;

import java.util.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class StartingDeckMetronome extends MetronomeCard 
{
    // TEXT DECLARATION
    public static final String ID = getCARDID();
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    //public static final String IMG = DuelistMod.makeCardPath("MetronomeAttack.png");		// Attack
    public static final String IMG = DuelistMod.makeCardPath("Metronome.png");				// Skill
    //public static final String IMG = DuelistMod.makeCardPath("MetronomePower.png");		// Power
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public StartingDeckMetronome() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.ALLOYED);
        this.tags.add(Tags.EXEMPT);
        this.tags.add(Tags.METRONOME);
        this.tags.add(Tags.NEVER_GENERATE);
        this.tags.add(Tags.SPELL);
        this.baseMagicNumber = this.magicNumber = 1;
        this.originalName = this.name;       
        this.returnsMultiple = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	metronomeAction(m);
    }
    
    @Override
	public ArrayList<AbstractCard> returnCards()
	{
    	
    	ArrayList<AbstractCard> tmp = generate(this.magicNumber);
		return tmp;
	}
    
    private ArrayList<AbstractCard> generate(int amtNeeded)
    {
    	ArrayList<AbstractCard> insects = new ArrayList<>();
    	ArrayList<CardTags> deckTags = new ArrayList<>();
    	deckTags.add(Tags.AQUA_DECK);
    	deckTags.add(Tags.DRAGON_DECK);
    	deckTags.add(Tags.SPELLCASTER_DECK);
    	deckTags.add(Tags.NATURIA_DECK);
    	if (!DuelistMod.toonBtnBool) {
    		deckTags.add(Tags.TOON_DECK);
		}
    	deckTags.add(Tags.FIEND_DECK);
    	deckTags.add(Tags.ZOMBIE_DECK);
    	deckTags.add(Tags.MACHINE_DECK);
    	deckTags.add(Tags.PLANT_DECK);
    	deckTags.add(Tags.INSECT_DECK);
    	deckTags.add(Tags.WARRIOR_DECK);
    	deckTags.add(Tags.MEGATYPE_DECK);
		for (AbstractCard c : DuelistMod.myCards)
		{
			if (!c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
			{
				if (!(c.hasTag(Tags.EXODIA) && DuelistMod.exodiaBtnBool)) {
					boolean allow = false;
					for (CardTags t : deckTags) {
						if (c.hasTag(t)) {
							allow = true;
							break;
						}
					}
					if (allow) { insects.add(c.makeStatEquivalentCopy()); }
				}
			}
		}

		if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}
		}
		return insects;
    }
    
    public AbstractCard returnCard()
    {
		return new CancelCard();				
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new StartingDeckMetronome();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded)
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    // AUTOSETUP - ID/IMG - Id, Img name, and class name all must match to use this
    public static String getCARDID()
    {
    	return DuelistMod.makeID(getCurClassName());
    }
    
	public static CardStrings getCardStrings()
    {
    	return CardCrawlGame.languagePack.getCardStrings(getCARDID());
    }
    
    public static String getIMG()
    {
    	return DuelistMod.makeCardPath(getCurClassName() + ".png");
    }
    
    public static String getCurClassName()
    {
    	return (new CurClassNameGetter()).getClassName();
    }

    public static class CurClassNameGetter extends SecurityManager{
    	public String getClassName(){
    		return getClassContext()[1].getSimpleName();
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
