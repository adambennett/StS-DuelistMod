package duelistmod.cards.nameless.magic;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.interfaces.NamelessTombCard;
import duelistmod.cards.pools.machine.AllyJustice;
import duelistmod.patches.*;
import duelistmod.variables.Tags;

public class AllyJusticeNameless extends DuelistCard implements NamelessTombCard
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Nameless:Magic:AllyJustice");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("AllyJustice.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPECIAL;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public AllyJusticeNameless() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 14;
        this.magicNumber = this.baseMagicNumber = 2 + DuelistMod.namelessTombMagicMod;
        this.tributes = this.baseTributes = 2;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.MACHINE);
        this.originalName = this.name;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute(p, this.tributes, false, this);    	
    	attack(m, AFX, this.damage);
    	applyPowerToSelf(new ArtifactPower(p, this.magicNumber));
    }

    @Override
    public DuelistCard getStandardVersion() { return new AllyJustice(); }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new AllyJusticeNameless();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            if (DuelistMod.hasUpgradeBuffRelic) { this.upgradeMagicNumber(2); }
            else { this.upgradeMagicNumber(1); }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    


	











}
