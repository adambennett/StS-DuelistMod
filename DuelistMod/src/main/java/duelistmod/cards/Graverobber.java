package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.actions.unique.GraverobberAction;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;

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
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;
    private static ArrayList<AbstractCard> drawPowers = new ArrayList<AbstractCard>();
    private static ArrayList<String> drawPowerNames = new ArrayList<String>();
    // /STAT DECLARATION/

    public Graverobber() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.PHARAOH_SERVANT);
        this.misc = 0;
		this.originalName = this.name;
		this.magicNumber = this.baseMagicNumber = 1;
		this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
		drawPowers = new ArrayList<AbstractCard>();
		drawPowerNames = new ArrayList<String>();
		for (DuelistCard c : DuelistMod.spellsThisRun) { if (!drawPowerNames.contains(c.originalName)) { drawPowers.add(c.makeStatEquivalentCopy()); drawPowerNames.add(c.originalName); }}
		for (DuelistCard c : DuelistMod.trapsThisRun)  { if (!drawPowerNames.contains(c.originalName) && !c.originalName.equals(this.originalName)) { drawPowers.add(c.makeStatEquivalentCopy()); drawPowerNames.add(c.originalName); }}

		if (drawPowers.size() >= 0)
		{
				if (DuelistMod.debug) { System.out.println("graverobber found choose cards to be > 0"); }
				AbstractDungeon.actionManager.addToTop(new GraverobberAction(this.magicNumber, drawPowers));
		}
		else { if (DuelistMod.debug) { System.out.println("graverobber found draw powers size was 0"); }}
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
            this.upgradeMagicNumber(-1);
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

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) 
	{
		if (DuelistMod.debug) { System.out.println("theDuelist:Invigoration:optionSelected() ---> can I see the card we selected? the card is: " + drawPowers.get(arg2).originalName); }
	}
}