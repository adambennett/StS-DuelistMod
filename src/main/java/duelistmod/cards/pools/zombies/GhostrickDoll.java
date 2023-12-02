package duelistmod.cards.pools.zombies;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCardWithAltVersions;
import duelistmod.enums.StartingDeck;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class GhostrickDoll extends DuelistCardWithAltVersions {

    private static final CardStrings cardStrings = getCardStrings();
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;

    public GhostrickDoll() {
        this(null, null);
    }

    public GhostrickDoll(StartingDeck deck, String key) {
        super(deck, key, getCARDID(), NAME, getIMG(), deck == StartingDeck.PHARAOH_III ? 1 : 2, deck == StartingDeck.PHARAOH_III ? UPGRADE_DESCRIPTION : DESCRIPTION, TYPE, COLOR, deck == StartingDeck.PHARAOH_III ? CardRarity.COMMON : CardRarity.UNCOMMON, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SPELLCASTER);
        this.tags.add(Tags.GHOSTRICK);
        this.tags.add(Tags.PHARAOH_THREE_DECK);
        this.p3DeckCopies = 1;
        this.misc = 0;
        this.originalName = this.name;
        this.baseSummons = this.summons = deck == StartingDeck.PHARAOH_III ? 1 : 5;
        this.specialCanUseLogic = true;
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.setupStartingCopies();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	if (upgraded || (this.getDeckVersionKey() != null && this.getDeckVersionKey() == StartingDeck.PHARAOH_III)) {
            evokeMult(2);
        } else {
            evoke(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new GhostrickDoll(this.getDeckVersionKey(), this.getGeneralVersionKey());
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            if (this.getDeckVersionKey() != null && this.getDeckVersionKey() == StartingDeck.PHARAOH_III) {
                this.upgradeBaseCost(0);
            }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription(); 
        }
    }

    @Override
    public GhostrickDoll getSpecialVersion(StartingDeck deck, String key) {
        return new GhostrickDoll(deck, key);
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
    // END AUTOSETUP
}
