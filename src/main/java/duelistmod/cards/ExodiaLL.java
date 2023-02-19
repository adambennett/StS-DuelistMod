package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

import java.util.List;

public class ExodiaLL extends DuelistCard 
{

    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("ExodiaLL");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.EXODIA_LEFT_LEG);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public ExodiaLL() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.EXODIA);
        this.tags.add(Tags.EXODIA_PIECE);
        this.tags.add(Tags.SPELLCASTER);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.LIMITED);
        this.tags.add(Tags.EXODIA_DECK);
        this.exodiaDeckCopies = 1;
        this.baseBlock = this.block = 5;
        this.summons = this.baseSummons = 1;
        this.damage = this.baseDamage = 1;
        this.exodiaName = "Left Leg";
        this.originalName = this.name;
        this.setupStartingCopies();
        this.enemyIntent = AbstractMonster.Intent.MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
    	duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        AnyDuelist p = AnyDuelist.from(this);
        summon();
        block();
        if (p.hasPower(ExodiaPower.POWER_ID)) {
            if (p.getPower(ExodiaPower.POWER_ID).amount > 0) {
                ExodiaPower power = (ExodiaPower) p.getPower(ExodiaPower.POWER_ID);
                power.addNewPiece(this);
            } else {
                p.applyPowerToSelf(new ExodiaPower(p.creature(), p.creature(), this));
            }
        } else {
            p.applyPowerToSelf(new ExodiaPower(p.creature(), p.creature(), this));
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ExodiaLL();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            //this.exhaust = false;
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
        }
    }




}
