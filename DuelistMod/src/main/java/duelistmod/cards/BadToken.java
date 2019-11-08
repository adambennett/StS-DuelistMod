package duelistmod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.speedster.SpeedsterUtil.UC;
import duelistmod.speedster.actions.BeginSpeedModeAction;
import duelistmod.speedster.mechanics.*;
import duelistmod.variables.*;

public class BadToken extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("BadToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GREEDPOT_AVATAR);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public BadToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.damage = this.baseDamage = 1;
    	this.baseMagicNumber = this.magicNumber = 3;
    	this.tags.add(Tags.NEVER_GENERATE);
    	//makeMegatyped();
    }
    public BadToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.damage = this.baseDamage = 1; 
    	this.baseMagicNumber = this.magicNumber = 3;
    	this.tags.add(Tags.NEVER_GENERATE);
    	//makeMegatyped();
    }
    
    
    @Override public void use (AbstractPlayer p, AbstractMonster m) 
    {
    	if(Settings.isDebug) {
            UC.doDmg(m, this.damage, MathUtils.randomBoolean() ? AbstractGameAction.AttackEffect.SLASH_VERTICAL : AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        } else {
            //UC.atb(new BeginSpeedModeAction(new SpeedClickEnemyTime(3.0f, mon -> UC.doDmg(mon, damage, DamageInfo.DamageType.NORMAL, UC.getSpeedyAttackEffect(), true))));
            Runnable myRunnable = () -> {
                System.out.println("Ran");
                UC.doVfx(new CleaveEffect());
                UC.doAllDmg(damage, AbstractGameAction.AttackEffect.NONE, false);
            };
            UC.atb(new BeginSpeedModeAction(new SpeedClickButtonTime(10.0f, myRunnable, new BasicButtonGenerator(1f, true)), 0));
            //UC.atb(new BeginSpeedModeAction(new SpeedHoverZoneTime(10.0f, myRunnable, true, 10)));
        }

    	/*
    	this.addToBot(new FetchAction(p.exhaustPile, 1));
    	if (DuelistMod.debug)
    	{
    		Debug.printTributeInfo();
        	Debug.printRarityInfo();
        	Debug.printTypedRarityInfo();
        	BoosterPackHelper.debugCheckLists();
    	}
    	*/
    }
   

    @Override public AbstractCard makeCopy() { return new BadToken(); }
	@Override public void onTribute(DuelistCard tributingCard) {}
	@Override public void onResummon(int summons) { }
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade() {}
	@Override
	public String getID() {
		return ID;
	}
	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}