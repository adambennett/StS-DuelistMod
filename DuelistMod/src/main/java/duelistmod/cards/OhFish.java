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
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

public class OhFish extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("OhFish");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.OH_FISH);
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

    public OhFish() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);
		this.originalName = this.name;
		this.magicNumber = this.baseMagicNumber = 1;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		ArrayList<DuelistCard> aquas = new ArrayList<DuelistCard>();
		for (int i = 0; i < this.magicNumber + 2; i++)
		{
			DuelistCard random = (DuelistCard) returnTrulyRandomFromSet(Tags.AQUA, false);
			while (aquas.contains(random)) { random = (DuelistCard) returnTrulyRandomFromSet(Tags.AQUA, false); }
			aquas.add(random);
		}
		
		if (aquas.size() >= this.magicNumber)
		{
			for (int i = 0; i < this.magicNumber; i++)
			{
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(aquas.get(i), this.upgraded, true, true, true, false, aquas.get(i).baseSummons > 0, false, false, 1, 3, 0, 0, 0, 1));
				if (DuelistMod.debug) { DuelistMod.logger.info("Calling RandomizedAction from: " + this.originalName); }
			}
		}
		
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower) AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			int aquaDmg = instance.getNumberOfTypeSummoned(Tags.AQUA) * this.magicNumber;
			damageAllEnemiesThornsNormal(aquaDmg);
		}
	}

	@Override
	public AbstractCard makeCopy() { return new OhFish(); }

	@Override
	public void upgrade() 
	{
		if (!upgraded) 
		{
			upgradeName();
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