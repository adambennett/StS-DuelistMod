package duelistmod.cards.curses;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.ExhaustSpecificCardSuperFastAction;
import duelistmod.variables.Tags;

import java.util.HashSet;
import java.util.Set;

public class GravekeeperCurse extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("GravekeeperCurse");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GravekeeperCurse.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;
    private static final int COST = -2;
    // /STAT DECLARATION/

    public GravekeeperCurse() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.isEthereal = true;
        this.magicNumber = this.baseMagicNumber = 5;
        this.tags.add(Tags.BAD_MAGIC);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    		
    }
    
    @Override
    public void triggerWhenDrawn() 
    {
        if (AbstractDungeon.player.discardPile.group.size() < 1) {
            return;
        }
        if (AbstractDungeon.player.discardPile.group.size() <= this.magicNumber) {
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                this.exhaust(c);
            }
            return;
        }
        Set<Integer> rolls = new HashSet<>();
        while (rolls.size() < this.magicNumber) {
            rolls.add(AbstractDungeon.cardRandomRng.random(0, AbstractDungeon.player.discardPile.group.size() - 1));
        }
        for (Integer roll : rolls) {
            exhaust(AbstractDungeon.player.discardPile.group.get(roll));
        }
    }

    private void exhaust(AbstractCard c) {
        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardSuperFastAction(c, AbstractDungeon.player.discardPile));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new GravekeeperCurse();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        
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
