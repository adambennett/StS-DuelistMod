package duelistmod.cards;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.patches.*;

@SuppressWarnings("unused")
public class RainMercy extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("RainMercy");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.RAIN_MERCY);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    private static int MIN_HEAL = 5;
    private static int MAX_HEAL = 20;
	private static final int MIN_HEAL_U = 10;
    private static final int MAX_HEAL_U = 25;
    // /STAT DECLARATION/

    public RainMercy() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
       	this.exhaust = true;
       	this.tags.add(Tags.SPELL);
       	this.tags.add(Tags.PHARAOH_SERVANT);
		this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Heal player for random amount
    	int randomHealNum = AbstractDungeon.cardRandomRng.random(MIN_HEAL, MAX_HEAL); 
    	heal(p, randomHealNum);
    	if (DuelistMod.debug) { System.out.println("theDuelist:RainMercy --- > Healed player for: " + randomHealNum); }
    	
    	// Heal enemies, each for a different random amount
    	ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
    	for (AbstractMonster g : monsters)
    	{
    		int randomHealNumM = AbstractDungeon.cardRandomRng.random(MIN_HEAL, MAX_HEAL); 
    		healMonster(g, randomHealNumM);
    		if (DuelistMod.debug) { System.out.println("theDuelist:RainMercy --- > Healed a monster for: " + randomHealNumM); }
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new RainMercy();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBaseCost(1);
            MIN_HEAL = MIN_HEAL_U;
            MAX_HEAL = MAX_HEAL_U;
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