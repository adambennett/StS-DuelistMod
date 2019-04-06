package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.patches.*;

public class FairyBox extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("FairyBox");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("FairyBox.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 0;

    // /STAT DECLARATION/

    public FairyBox() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.magicNumber = this.baseMagicNumber = 3;
        this.exhaust = true;
		this.originalName = this.name;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		if (DuelistMod.orbCards.size() > this.magicNumber)
		{
			ArrayList<DuelistCard> orbs = new ArrayList<DuelistCard>();
			for (int i = 0; i < this.magicNumber; i++)
			{
				DuelistCard orbCard = DuelistMod.orbCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.orbCards.size() - 1));
				while (orbs.contains(orbCard)) { orbCard = DuelistMod.orbCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.orbCards.size() - 1)); }
				orbs.add(orbCard);
			}
			
			for (DuelistCard c : orbs)
			{
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(c, false, true, true, true, false, false, false, false, 1, 1, 0, 0, 0, 0));
			}
		}		
	}

	@Override
	public AbstractCard makeCopy() { return new FairyBox(); }

	@Override
	public void upgrade() 
	{
		if (!upgraded) 
		{
			this.upgradeName();
			this.upgradeMagicNumber(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	
	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
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