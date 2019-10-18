package duelistmod.cards;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.*;
import duelistmod.variables.*;

public class Ojamuscle extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("Ojamuscle");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.OJAMUSCLE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 0;
    private static final int MIN_CARDS = 1;
    private static final int MAX_CARDS = 3;
    // /STAT DECLARATION/

    public Ojamuscle() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.OJAMA);
        this.tags.add(Tags.REDUCED);
        this.tags.add(Tags.OJAMA_DECK);
		this.ojamaDeckCopies = 1;
		this.cardsToPreview = new OjamaYellow();
		this.originalName = this.name;
		this.exhaust = true;
		this.setupStartingCopies();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		int randomNumCards = 1;
		if (this.upgraded) { randomNumCards = AbstractDungeon.cardRandomRng.random(MIN_CARDS, MAX_CARDS); }
		else { randomNumCards = AbstractDungeon.cardRandomRng.random(MIN_CARDS, MAX_CARDS); }
		
		AbstractCard ojamaCard = new OjamaYellow();
		if (upgraded) 
		{
			ojamaCard.upgrade();
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(ojamaCard, randomNumCards, true, true));
		} 
		else 
		{
			ojamaCard.modifyCostForCombat(0);
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(ojamaCard, randomNumCards, true, true));
		}
	}

	@Override
	public AbstractCard makeCopy() { return new Ojamuscle(); }

	@Override
	public void upgrade() 
	{
		if (!upgraded) 
		{
			upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.cardsToPreview.upgrade();
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