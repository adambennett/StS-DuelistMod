package duelistmod.cards.pools.beast;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.duelistPowers.FangsPower;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class GreenBaboon extends DuelistCard {
    public static final String ID = DuelistMod.makeID("GreenBaboon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GreenBaboon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;

    public GreenBaboon() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
        this.tags.add(Tags.TERRITORIAL);
        this.misc = 0;
        this.originalName = this.name;
        this.tributes = this.baseTributes = 4;
        this.baseMagicNumber = this.magicNumber = 8;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        tribute();
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.applyPowerToSelf(new FangsPower(duelist.creature(), duelist.creature(), this.magicNumber));
        if (targets.size() > 0) {
            attack(targets.get(0), this.baseAFX, this.damage);
            ArrayList<AbstractCard> list = findAllOfTypeForResummon(Tags.BEAST, 1);
            for (AbstractCard c : list) {
                if (duelist.player()) {
                    resummon(c, (AbstractMonster) targets.get(0), false, true);
                } else if (duelist.getEnemy() != null) {
                    DuelistCard.anyDuelistResummon(c, duelist, AbstractDungeon.player, true);
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new GreenBaboon();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeTributes(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
