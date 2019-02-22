package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import defaultmod.DefaultMod;
import defaultmod.actions.common.ModifyMagicNumberAction;
import defaultmod.orbs.Buffer;
import defaultmod.patches.*;

public class DarkMagicianGirl extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = defaultmod.DefaultMod.makeID("DarkMagicianGirl");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DefaultMod.makePath(DefaultMod.DARK_MAGICIAN_GIRL);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
	private static final int COST = 1;
	private static final int OVERFLOW_AMT = 3;
	private static final int U_OVERFLOW = 2;
	private static int MIN_TURNS_ROLL = 1;
	private static int MAX_TURNS_ROLL = 4;
	// /STAT DECLARATION/

	public DarkMagicianGirl() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.magicNumber = this.baseMagicNumber = OVERFLOW_AMT;
		this.tags.add(DefaultMod.MONSTER);
		this.tags.add(DefaultMod.MAGICIANS_FORCE);
		this.misc = 0;
		this.originalName = this.name;
		this.summons = 1;
		this.isSummon = true;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon(p, this.summons, this);
		AbstractOrb buffer = new Buffer();
		channel(buffer);
	}

	@Override
	public void triggerOnEndOfPlayerTurn() 
	{
		// If overflows remaining
		if (this.magicNumber > 0) 
		{
			// Get player reference
			AbstractPlayer p = AbstractDungeon.player;

			// Remove 1 overflow
			AbstractDungeon.actionManager.addToBottom(new ModifyMagicNumberAction(this, -1));

			// Get random number of turns for buff to apply for
			int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_TURNS_ROLL, MAX_TURNS_ROLL);

			applyRandomBuffPlayer(p, randomTurnNum, true);
		}
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new DarkMagicianGirl();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(U_OVERFLOW);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		
		
	}



	@Override
	public void onSummon(int summons)
	{
		
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		summon(player(), this.summons, this);
		AbstractOrb buffer = new Buffer();
		channel(buffer);
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		summon(player(), this.summons, this);
		AbstractOrb buffer = new Buffer();
		channel(buffer);
	}

	@Override
	public String getID() {
		return ID;
	}

}