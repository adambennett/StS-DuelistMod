package defaultmod.cards;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

@SuppressWarnings("unused")
public class BigFire extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("BigFire");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.TREMENDOUS_FIRE);
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
    private static int MIN_HEAL = 0;
    private static int MAX_HEAL = 25;
	private static final int MIN_HEAL_U = 5;
    private static final int MAX_HEAL_U = 30;
    // /STAT DECLARATION/

    public BigFire() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
       	this.tags.add(DefaultMod.SPELL);
       	this.tags.add(DefaultMod.METAL_RAIDERS);
       	this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Damage player for random amount
    	int randomDmgNum = AbstractDungeon.cardRandomRng.random(MIN_HEAL, MAX_HEAL);
    	damageSelfFire(randomDmgNum);
    	System.out.println("theDuelist:BigFire --- > Damaged player for: " + randomDmgNum);
    	
    	// Damage enemies, each for a different random amount
    	ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
    	for (AbstractMonster g : monsters)
    	{
    		int randomDmgNumM = AbstractDungeon.cardRandomRng.random(MIN_HEAL, MAX_HEAL);
    		AbstractDungeon.actionManager.addToTop(new DamageAction(g, new DamageInfo(p, randomDmgNumM, DamageType.THORNS),AbstractGameAction.AttackEffect.FIRE));
    		System.out.println("theDuelist:BigFire --- > Damaged a monster for: " + randomDmgNumM);
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
            MAX_HEAL = MAX_HEAL_U;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}