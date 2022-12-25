package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class Gigaplant extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Gigaplant");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GIGAPLANT);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public Gigaplant() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.EXEMPT);
        this.tags.add(Tags.PLANT);
        this.tags.add(Tags.GOOD_TRIB);
        this.tributes = this.baseTributes = 3;
		this.originalName = this.name;
		this.baseMagicNumber = this.magicNumber = 8;
		this.misc = 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute(p, this.tributes, false, this);
    	applyPower(new PoisonPower(m, p, this.magicNumber), m);
    	ArrayList<AbstractCard> discardMons = new ArrayList<>();
    	for (AbstractCard c : p.discardPile.group)
    	{
    		if (c.hasTag(Tags.MONSTER) && allowResummonsWithExtraChecks(c)) { discardMons.add(c.makeStatEquivalentCopy()); }
    	}
    	while (discardMons.size() > 1 && discardMons.size() > 0) {
    		discardMons.remove(AbstractDungeon.cardRandomRng.random(discardMons.size() - 1));
    	}
    	
    	for (AbstractCard c : discardMons)
    	{
    		resummon(c, m, false, true);
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Gigaplant();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    


	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		plantSynTrib(tributingCard);
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
