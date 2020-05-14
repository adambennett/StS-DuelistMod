package duelistmod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class Ojamagic extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("Ojamagic");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.OJAMAGIC);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    private static final int MIN_CARDS = 1;
    private static final int MAX_CARDS = 4;
    // /STAT DECLARATION/

    public Ojamagic() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.OJAMA);
        this.tags.add(Tags.REDUCED);
        this.cardsToPreview = new RedMedicine();
		this.originalName = this.name;
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		int randomNumCards = 1;
		if (this.upgraded) { randomNumCards = AbstractDungeon.cardRandomRng.random(MIN_CARDS, MAX_CARDS); }
		else { randomNumCards = AbstractDungeon.cardRandomRng.random(MIN_CARDS, MAX_CARDS); }
		
		AbstractCard redMedicine = new RedMedicine();
		if (upgraded) 
		{
			redMedicine.upgrade();
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(redMedicine, randomNumCards, true, true));
		} 
		else 
		{
			redMedicine.modifyCostForCombat(0);
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(redMedicine, randomNumCards, true, true));
		}
	}

	@Override
	public AbstractCard makeCopy() { return new Ojamagic(); }

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