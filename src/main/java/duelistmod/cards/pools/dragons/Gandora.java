package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DynamicDamageCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class Gandora extends DynamicDamageCard
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Gandora");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GANDORA);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.FIRE;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public Gandora() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.GOOD_TRIB);
        this.tags.add(Tags.NO_CARD_FOR_RANDOM_DECK_POOLS);
        this.tags.add(Tags.NO_CREATOR);
        this.tags.add(Tags.FULL);
        this.originalName = this.name;
        this.tributes = this.baseTributes = 3;
        this.baseMagicNumber = this.magicNumber = 5;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Tribute
    	tribute(p, this.tributes, false, this);

        // Deal base damage + extra damage for every card exhausted
        attack(m, AFX, this.damage);

    	// Record hand size
    	int handSize = AbstractDungeon.player.hand.size() - 1;
    	if (handSize <= 0) { handSize = 0; }
    	
    	// Exhaust all cards
    	AbstractDungeon.actionManager.addToTop(new ExhaustAction(handSize, true));

    	// Add random 0 cost, upgraded dragons to draw pile
        int loopCount = upgraded ? 4 : 3;
    	for (int i = 0; i < loopCount; i++)
    	{
	    	AbstractCard randomDragon = returnTrulyRandomFromSet(Tags.DRAGON);
	    	randomDragon.upgrade();
	    	randomDragon.updateCost(0);
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(randomDragon, 1, false));
    	}
    }

    @Override
    public int damageFunction() {
        int handSize = AbstractDungeon.player.hand.size() - 1;
        if (handSize <= 0) { handSize = 0; }
        return this.magicNumber * handSize;
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Gandora();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }




	








}
