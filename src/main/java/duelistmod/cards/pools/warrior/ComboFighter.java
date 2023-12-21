package duelistmod.cards.pools.warrior;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DynamicDamageCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class ComboFighter extends DynamicDamageCard
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("ComboFighter");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ComboFighter.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    public Boolean wasRetained = false;
    // /STAT DECLARATION/

    public ComboFighter() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);  
        this.tags.add(Tags.WARRIOR); 
        this.summons = this.baseSummons = 1;	
        this.baseDamage = this.damage = this.originalDamage = 7;
        this.originalName = this.name;
    }

	@Override
	public int damageFunctionMultiplier() {
		return this.wasRetained ? 2 : 1;
	}
    
    @Override
	public AbstractCard makeStatEquivalentCopy() {
		ComboFighter ret = (ComboFighter)super.makeStatEquivalentCopy();
		ret.wasRetained = this.wasRetained;
		return ret;
	}

	public void onMoveToDiscard() {
		super.onMoveToDiscard();
		this.wasRetained = false;
	}

	public void update() {
		super.update();
		if (this.retain) { this.wasRetained = true; }
	}

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
    
    @Override
    public void triggerOnGlowCheck()
    {
    	super.triggerOnGlowCheck();
        if (this.wasRetained) {
        	 this.glowColor = Color.GOLD;
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ComboFighter();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }












}
