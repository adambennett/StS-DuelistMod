package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class ValkMagnet extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = duelistmod.DuelistMod.makeID("ValkMagnet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.VALK_MAGNET);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public ValkMagnet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 30;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.MAGNET);
        this.tags.add(Tags.ROCK);
        this.tags.add(Tags.EXEMPT);
		this.originalName = this.name;
		this.isMultiDamage = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (p.hasPower(AlphaMagPower.POWER_ID) && p.hasPower(BetaMagPower.POWER_ID) && p.hasPower(GammaMagPower.POWER_ID))
    	{
    		this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AttackEffect.SLASH_DIAGONAL));
	    	AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, AlphaMagPower.POWER_ID, 1));
	    	AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, BetaMagPower.POWER_ID, 1));
	    	AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, GammaMagPower.POWER_ID, 1));
        	ArrayList<DuelistCard> stances = Util.getStanceChoices(true, true, true);
        	ArrayList<AbstractCard> abTypes = new ArrayList<>();
        	abTypes.addAll(stances);
        	AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(abTypes, 1, false, false, false, true));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ValkMagnet();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); 
            this.upgradeDamage(10);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

	public String failedCardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) { return "Need all 3 Magnets"; }

	public boolean cardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) {
		return p.hasPower(AlphaMagPower.POWER_ID) && p.hasPower(BetaMagPower.POWER_ID) && p.hasPower(GammaMagPower.POWER_ID);
	}
    


	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		// TODO Auto-generated method stub
		
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
