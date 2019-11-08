package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.GraverobberAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class Graverobber extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Graverobber");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GRAVEROBBER);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public Graverobber() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.PHARAOH_SERVANT);
        this.tags.add(Tags.ALLOYED);
        this.misc = 0;
		this.originalName = this.name;
		this.magicNumber = this.baseMagicNumber = 3;
		this.secondMagic = this.baseSecondMagic = 1;
		this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractCard> drawPowers = new ArrayList<>();
		ArrayList<AbstractCard> chosenCards = new ArrayList<>();
		for (DuelistCard c : DuelistMod.uniqueSpellsThisRun) { drawPowers.add(c.makeStatEquivalentCopy()); }
		for (DuelistCard c : DuelistMod.uniqueTrapsThisRun)  { if (!c.uuid.equals(this.uuid)) { drawPowers.add(c.makeStatEquivalentCopy()); }}

		if (drawPowers.size() >= this.magicNumber)
		{
			while (chosenCards.size() < this.magicNumber)
			{
				int index = AbstractDungeon.cardRandomRng.random(drawPowers.size() - 1);
				AbstractCard rand = drawPowers.get(index);
				chosenCards.add(rand);
				drawPowers.remove(index);
			}
			AbstractDungeon.actionManager.addToTop(new GraverobberAction(this.secondMagic, chosenCards));
		}
		else if (drawPowers.size() > 0)
		{
			AbstractDungeon.actionManager.addToTop(new GraverobberAction(this.secondMagic, drawPowers));
		}
		else { Util.log("graverobber found draw powers size was 0"); }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Graverobber();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSecondMagic(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard)
	{
		
	}


	@Override
	public void onResummon(int summons) 
	{
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		 
		
	}

	@Override
	public String getID() {
		return ID;
	}

}