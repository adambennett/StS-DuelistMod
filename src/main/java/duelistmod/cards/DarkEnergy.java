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
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.patches.*;
import duelistmod.variables.*;

public class DarkEnergy extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("DarkEnergy");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.DARK_ENERGY);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public DarkEnergy() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.baseDamage = this.damage = 6;
        this.magicNumber = this.baseMagicNumber = 2;
		this.originalName = this.name;
		this.exhaust = true;
		this.setupStartingCopies();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		attack(m);
		ArrayList<DuelistCard> aquas = new ArrayList<DuelistCard>();
		for (int i = 0; i < this.magicNumber + 2; i++)
		{
			DuelistCard random = (DuelistCard) returnTrulyRandomFromSet(Tags.FIEND, false);
			while (aquas.contains(random)) { random = (DuelistCard) returnTrulyRandomFromSet(Tags.FIEND, false); }
			aquas.add(random);
		}
		
		if (aquas.size() >= this.magicNumber)
		{
			for (int i = 0; i < this.magicNumber; i++)
			{
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(aquas.get(i), this.upgraded, true, false, true, false, aquas.get(i).isSummonCard(), false, false, 0, 3, 0, 0, 0, 1));
				if (DuelistMod.debug) { DuelistMod.logger.info("Calling RandomizedAction from: " + this.originalName); }
			}
		}
	}

	@Override
	public AbstractCard makeCopy() { return new DarkEnergy(); }

	@Override
	public void upgrade() 
	{
		if (!upgraded) 
		{
			upgradeName();
			this.upgradeDamage(3);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
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
