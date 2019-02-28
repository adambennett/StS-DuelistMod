package defaultmod.cards;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class SwordDeepSeated extends DuelistCard 
{
	/* 	
	 * Gain X strength this turn. 
	 * the end of the turn, Tribute X and 
	 * place this card on top of your draw pile. 
	 */
    // TEXT DECLARATION 
    public static final String ID = DefaultMod.makeID("SwordDeepSeated");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.SWORD_DEEP_SEATED);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION 	
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 0;
    private static final int STR_GAIN = 5;
    // /STAT DECLARATION/


    public SwordDeepSeated() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.tags.add(DefaultMod.SPELL);
        this.tags.add(DefaultMod.ALL);
        this.tags.add(DefaultMod.METAL_RAIDERS);
        this.originalName = this.name;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	System.out.println("theDuelist:SwordDeepSeated:use() ---> swords played: " + DefaultMod.swordsPlayed);
    	if (GameActionManager.turn <= 1 && AbstractDungeon.player.cardsPlayedThisTurn <= 1) { System.out.println("theDuelist:SwordDeepSeated:use() ---> reset DefaultMod.swordsPlayed to 0."); DefaultMod.swordsPlayed = 0; }
    	applyPowerToSelf(new StrengthPower(p, STR_GAIN));
    	if (!p.hasPower(GravityAxePower.POWER_ID)) { applyPowerToSelf(new SwordDeepPower(p, p, 1, STR_GAIN)); }
    	AbstractCard sword = new SwordDeepSeated();
    	if (upgraded) { sword.upgrade(); }
    	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(sword, this.magicNumber, true, true));
    	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(sword, this.magicNumber * DefaultMod.swordsPlayed, true, true));
    	DefaultMod.swordsPlayed++;
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
}