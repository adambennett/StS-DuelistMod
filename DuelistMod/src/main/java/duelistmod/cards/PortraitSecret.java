package duelistmod.cards;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.*;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;

public class PortraitSecret extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("PortraitSecret");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("PortraitsSecret.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public PortraitSecret() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.summons = this.baseSummons = 1;
		this.magicNumber = this.baseMagicNumber = 2;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.FIEND);
		this.misc = 0;
		this.originalName = this.name;
		this.exhaust = true;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon();
		ArrayList<DuelistCard> handTribs = new ArrayList<DuelistCard>();
		for (AbstractCard c : p.hand.group)
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dC = (DuelistCard)c;
				if (dC.tributes > 0)
				{
					handTribs.add(dC);
				}
			}
		}
		
		if (handTribs.size() > 0)
		{
			DuelistCard pick = handTribs.get(AbstractDungeon.cardRandomRng.random(handTribs.size() - 1));
			AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(pick, this.magicNumber, false));
		}
		
		applyPowerToSelf(new StrengthPower(p, this.magicNumber));
	}
	
	@Override
    public void atTurnStart() 
    {
    	
    }

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new PortraitSecret();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeSummons(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	@Override
	public void onTribute(DuelistCard tributingCard)
	{
		// Fiend Tribute
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower(DoomdogPower.POWER_ID)) { int dmgAmount = p.getPower(DoomdogPower.POWER_ID).amount; damageAllEnemiesThornsNormal(dmgAmount); }
		if (p.hasPower(RedMirrorPower.POWER_ID)) { for (AbstractCard c : p.discardPile.group) { if (c.cost > 0)	{ c.modifyCostForTurn(-p.getPower(RedMirrorPower.POWER_ID).amount);	c.isCostModifiedForTurn = true;	}}}
		if (tributingCard.hasTag(Tags.FIEND)) { AbstractDungeon.actionManager.addToBottom(new FetchAction(p.discardPile, DuelistMod.fiendDraw)); }
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