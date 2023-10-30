package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.LavaOrbEruptionResult;
import duelistmod.patches.*;
import duelistmod.variables.*;

@SuppressWarnings("unused")
public class BigFire extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("BigFire");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.TREMENDOUS_FIRE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    private static int MIN_HEAL = 0;
    private static int MAX_HEAL = 25;
	private static final int MIN_HEAL_U = 5;
    private static final int MAX_HEAL_U = 25;
    // /STAT DECLARATION/

    public BigFire() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
       	this.tags.add(Tags.SPELL);
       	this.tags.add(Tags.METAL_RAIDERS);
       	this.secondMagic = this.baseSecondMagic = 15;
       	this.originalName = this.name;
    }
    
    @Override
    public LavaOrbEruptionResult lavaEvokeEffect() {
        damageAllEnemiesThornsFire(this.secondMagic);
        return new LavaOrbEruptionResult();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Damage enemies, each for a different random amount
    	ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
    	for (AbstractMonster g : monsters)
    	{
    		if (!g.isDead && !g.isDying && !g.isDeadOrEscaped() && !g.halfDead)
    		{
	    		int randomDmgNumM = AbstractDungeon.cardRandomRng.random(MIN_HEAL, MAX_HEAL);
	    		int randomDmgNumM_Upgrade = AbstractDungeon.cardRandomRng.random(MIN_HEAL, MAX_HEAL_U);
	    		if (!upgraded) { AbstractDungeon.actionManager.addToTop(new DamageAction(g, new DamageInfo(p, randomDmgNumM, DamageType.THORNS),AbstractGameAction.AttackEffect.FIRE)); }
	    		else  { AbstractDungeon.actionManager.addToTop(new DamageAction(g, new DamageInfo(p, randomDmgNumM_Upgrade, DamageType.THORNS),AbstractGameAction.AttackEffect.FIRE)); }
	    		if (DuelistMod.debug) { System.out.println("theDuelist:BigFire --- > Damaged a monster for: " + randomDmgNumM); }
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BigFire();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

	

	










}
