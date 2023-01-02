package duelistmod.cards;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.RandomizedDrawPileAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class SwordDeepSeated extends DuelistCard 
{
	/* 	
	 * Gain X strength this turn. 
	 * the end of the turn, Tribute X and 
	 * place this card on top of your draw pile. 
	 */
    // TEXT DECLARATION 
    public static final String ID = DuelistMod.makeID("SwordDeepSeated");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SWORD_DEEP_SEATED);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION 	
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 0;
    private static final int STR_GAIN = 5;
    // /STAT DECLARATION/


    public SwordDeepSeated() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.METAL_RAIDERS);
        this.tags.add(Tags.BAD_MAGIC);
        this.originalName = this.name;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (GameActionManager.turn <= 1 && AbstractDungeon.actionManager.cardsPlayedThisTurn.size() <= 1) {
    		DuelistMod.swordsPlayed = 0; 
    	}
    	applyPowerToSelf(new StrengthPower(p, STR_GAIN));
    	if (!p.hasPower(GravityAxePower.POWER_ID)) { applyPowerToSelf(new SwordDeepPower(p, p, 1, STR_GAIN)); }
    	AbstractCard sword = new SwordDeepSeated();
    	if (upgraded) { sword.upgrade(); }
    	for (int i = 0; i < this.magicNumber; i++) { AbstractDungeon.actionManager.addToTop(new RandomizedDrawPileAction(sword, this.upgraded, true)); }
    	for (int i = 0; i < this.magicNumber * DuelistMod.swordsPlayed; i++) { AbstractDungeon.actionManager.addToTop(new RandomizedDrawPileAction(sword, this.upgraded, true)); }
    	DuelistMod.swordsPlayed++;
    }


    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SwordDeepSeated();
    }
    
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-1);
            this.exhaust = true;
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
