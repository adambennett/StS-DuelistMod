package duelistmod.cards.pools.dragons;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.KuribohToken;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class Scapegoat extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("Scapegoat");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SCAPEGOAT);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public Scapegoat() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.KURIBOH);
		this.originalName = this.name;
		this.summons = this.baseSummons = 4;
		this.cardsToPreview = new KuribohToken();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	incMaxSummons(p, this.magicNumber);
    	DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new KuribohToken());
    	summon(p, this.summons, tok);
    }

    @Override
    public int addToMaxSummonsDuringSummonZoneChecks() {
        return this.magicNumber;
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Scapegoat();
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
