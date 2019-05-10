package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.actions.common.ModifyMagicNumberAction;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;

public class OjamaPajama extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("OjamaPajama");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("OjamaPajama.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 0;

    // /STAT DECLARATION/

    public OjamaPajama() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.OJAMA);
		this.originalName = this.name;
		this.magicNumber = this.baseMagicNumber = 4;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		for (AbstractCard c : player().drawPile.group)
		{
			int roll = AbstractDungeon.cardRandomRng.random(c.magicNumber - this.magicNumber, c.magicNumber + this.magicNumber);
			AbstractDungeon.actionManager.addToTop(new ModifyMagicNumberAction(c, roll));
		}
		
		for (AbstractCard c : player().discardPile.group)
		{
			int roll = AbstractDungeon.cardRandomRng.random(c.magicNumber - this.magicNumber, c.magicNumber + this.magicNumber);
			AbstractDungeon.actionManager.addToTop(new ModifyMagicNumberAction(c, roll));
		}
		
		for (AbstractCard c : player().hand.group)
		{
			int roll = AbstractDungeon.cardRandomRng.random(c.magicNumber - this.magicNumber, c.magicNumber + this.magicNumber);
			AbstractDungeon.actionManager.addToTop(new ModifyMagicNumberAction(c, roll));
		}
	}

	@Override
	public AbstractCard makeCopy() { return new OjamaPajama(); }

	@Override
	public void upgrade() 
	{
		if (!upgraded) 
		{
			this.upgradeName();
			this.upgradeMagicNumber(3);
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