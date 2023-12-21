package duelistmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import com.megacrit.cardcrawl.powers.AngerPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.EnvenomPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.actions.unique.RedMedicineAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.ElectricityPower;
import duelistmod.variables.*;

import java.util.List;


public class RedMedicine extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("RedMedicine");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.RED_MEDICINE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public RedMedicine() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.REDUCED);
        this.tags.add(Tags.GENERATION_DECK);
        this.tags.add(Tags.NEVER_GENERATE);
		this.tags.add(Tags.ARCANE);
		this.generationDeckCopies = 1;
		this.originalName = this.name;
		this.exhaust = true;
		this.setupStartingCopies();
        this.enemyIntent = AbstractMonster.Intent.BUFF;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        if (!this.upgraded || owner instanceof AbstractEnemyDuelist) {
            int randomTurnNum = AbstractDungeon.cardRandomRng.random(1, 4);
            if (owner instanceof AbstractPlayer) {
                applyRandomBuffPlayer((AbstractPlayer)owner, randomTurnNum, false);
            } else {
                int roll = AbstractDungeon.aiRng.random(1, 7);
                int turns = AbstractDungeon.aiRng.random(1, 3);
                switch (roll) {
                    case 2:
                        DuelistCard.applyPower(new DexterityPower(owner, turns), owner);
                        break;
                    case 3:
                        DuelistCard.applyPower(new RegenPower(owner, turns), owner);
                        break;
                    case 4:
                        DuelistCard.applyPower(new IntangiblePower(owner, 1), owner);
                        break;
                    case 5:
                        DuelistCard.applyPower(new ThornsPower(owner, turns), owner);
                        break;
                    case 6:
                        DuelistCard.applyPower(new MetallicizePower(owner, turns), owner);
                        break;
                    case 7:
                        DuelistCard.applyPower(new EnvenomPower(owner, turns), owner);
                        break;
                    default:
                        DuelistCard.applyPower(new StrengthPower(owner, turns), owner);
                        break;
                }
            }
        } else {
            int lowRoll = AbstractDungeon.cardRandomRng.random(1, 2);
            int highRoll = AbstractDungeon.cardRandomRng.random(3, 6);
            AbstractDungeon.actionManager.addToTop(new RedMedicineAction(1, targets.get(0), this.magicNumber, lowRoll, highRoll));
        }
        postDuelistUseCard(owner, targets);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() 
    {
        return new RedMedicine();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (canUpgrade()) 
        {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.magicNumber < 10) { return true; }
    	else { return false; }
    }

	


	








}
