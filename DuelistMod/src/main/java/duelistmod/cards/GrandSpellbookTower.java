package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;

public class GrandSpellbookTower extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("GrandSpellbookTower");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("GrandSpellbookTower.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
	private static final int COST = 0;
	// /STAT DECLARATION/

	public GrandSpellbookTower() 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.SPELL);
		this.exhaust = true;
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		if (!upgraded)
		{
			ArrayList<DuelistCard> spellbooks = new ArrayList<DuelistCard>();
			DuelistCard uSK = new SpellbookKnowledge();
			DuelistCard uPK = new SpellbookPower();
			DuelistCard uLK = new SpellbookLife();
			DuelistCard uMK = new SpellbookMiracle();
			spellbooks.add(new SpellbookKnowledge());
			spellbooks.add(new SpellbookKnowledge());
			spellbooks.add(new SpellbookKnowledge());
			spellbooks.add(new SpellbookLife());
			spellbooks.add(new SpellbookLife());
			spellbooks.add(new SpellbookLife());
			spellbooks.add(new SpellbookPower());
			spellbooks.add(new SpellbookPower());
			spellbooks.add(new SpellbookPower());
			spellbooks.add(new SpellbookMiracle());	
			spellbooks.add(new SpellbookMiracle());	
			spellbooks.add(new SpellbookMiracle());	
			uSK.upgrade(); 
			spellbooks.add(uSK);
			uPK.upgrade();
			spellbooks.add(uPK);
			uLK.upgrade();
			spellbooks.add(uLK);
			uMK.upgrade();
			spellbooks.add(uMK);
	
			DuelistCard randomSpellbook = spellbooks.get(AbstractDungeon.cardRandomRng.random(spellbooks.size() - 1));
			addCardToHand((DuelistCard)randomSpellbook.makeStatEquivalentCopy());
		}
		else
		{
			ArrayList<DuelistCard> spellbooks = new ArrayList<DuelistCard>();
			spellbooks.add(new SpellbookKnowledge());
			spellbooks.add(new SpellbookLife());
			spellbooks.add(new SpellbookPower());
			spellbooks.add(new SpellbookMiracle());	
			spellbooks.add(new SpellbookKnowledge());
			spellbooks.add(new SpellbookLife());
			spellbooks.add(new SpellbookPower());
			spellbooks.add(new SpellbookMiracle());
			DuelistCard uSK = new SpellbookKnowledge();
			DuelistCard uPK = new SpellbookPower();
			DuelistCard uLK = new SpellbookLife();
			DuelistCard uMK = new SpellbookMiracle();
			uSK.upgrade(); 
			spellbooks.add(uSK);
			uPK.upgrade();
			spellbooks.add(uPK);
			uLK.upgrade();
			spellbooks.add(uLK);
			uMK.upgrade();
			spellbooks.add(uMK);
			ArrayList<AbstractCard> abstractSpellbooks = new ArrayList<AbstractCard>();
			ArrayList<String> addedSpellbooks = new ArrayList<String>();
			for (int i = 0; i < 3; i++)
			{
				DuelistCard randSpellbook = spellbooks.get(AbstractDungeon.cardRandomRng.random(spellbooks.size() - 1));
				while (addedSpellbooks.contains(randSpellbook.name)) { randSpellbook = spellbooks.get(AbstractDungeon.cardRandomRng.random(spellbooks.size() - 1)); }
				abstractSpellbooks.add(randSpellbook.makeStatEquivalentCopy());
			}
			AbstractDungeon.actionManager.addToTop(new CardSelectScreenIntoHandAction(false, false, 1, abstractSpellbooks));
		}
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() 
	{
		return new GrandSpellbookTower();
	}

	// Upgraded stats.
	@Override
	public void upgrade() 
	{
		if (!this.upgraded) 
		{
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
	

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		
	}


	@Override
	public void onResummon(int summons)
	{

	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		
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