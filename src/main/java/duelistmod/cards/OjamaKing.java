package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.*;
import duelistmod.helpers.DebuffHelper;
import duelistmod.interfaces.*;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class OjamaKing extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("OjamaKing");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.OJAMA_KING);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    private static int MIN_BUFF_TURNS_ROLL = 1;
    private static int MAX_BUFF_TURNS_ROLL = 3;
    private static int MIN_DEBUFF_TURNS_ROLL = 1;
    private static int MAX_DEBUFF_TURNS_ROLL = 6;
    private static int RAND_CARDS = 2;
    private static int RAND_BUFFS = 1;
    private static int RAND_DEBUFFS = 2;
    // /STAT DECLARATION/

    public OjamaKing() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.OJAMA);
        this.tags.add(Tags.BEAST);
        this.misc = 0;
		this.originalName = this.name;
		this.tributes = this.baseTributes = 3;
    }

    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Tribute
		tribute(p, this.tributes, false, this);

		// Add 5 random cards to hand, set cost to 0
		for (int i = 0; i < RAND_CARDS; i++)
		{
			AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat().makeStatEquivalentCopy();
			AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(card, false, true, true, false, false, false, true, true, 1, 3, 0, 1, 1, 2));
			if (DuelistMod.debug) { DuelistMod.logger.info("Calling RandomizedAction from: " + this.originalName); }
		}
		
		// Give self 3 random buffs
		for (int i = 0; i < RAND_BUFFS; i++)
		{
			int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_BUFF_TURNS_ROLL, MAX_BUFF_TURNS_ROLL);
			applyRandomBuffPlayer(p, randomTurnNum, false);
		}
		
		// Give 3 random debuffs to enemy
		for (int i = 0; i < RAND_DEBUFFS; i++)
		{
			int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_DEBUFF_TURNS_ROLL, MAX_DEBUFF_TURNS_ROLL);
			applyPower(DebuffHelper.getRandomDebuff(p, m, randomTurnNum), m);
		}
		
		// Give 3 Spell Counters to enemy
		//applyPower(new SpellCounterPower(p, p, COUNTERS), m);

    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new OjamaKing();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    


	















}
