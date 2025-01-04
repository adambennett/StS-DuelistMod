package duelistmod.cards.pools.toon;

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
import duelistmod.interfaces.RevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mimiclay extends DuelistCard {

    public static final String ID = DuelistMod.makeID("Mimiclay");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Mimiclay.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public Mimiclay() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseMagicNumber = this.magicNumber = 2;
    	this.tags.add(Tags.SPELL);
    	this.misc = 0;
    	this.originalName = this.name;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        if (targets.size() > 0 && this.magicNumber > 0) {

            AnyDuelist duelist = AnyDuelist.from(this);
            List<AbstractCard> revengeCardsPlayedThisCombat = duelist.getCardsPlayedCombat().stream().filter(c -> c instanceof RevengeCard).collect(Collectors.toList());
            ArrayList<DuelistCard> revengeCardsTriggeredThisCombat = duelist.getRevengeCardsTriggeredThisCombat();

            AbstractCard lastRevengeCard = null;
            if (this.upgraded && !revengeCardsPlayedThisCombat.isEmpty()) {
                lastRevengeCard = revengeCardsPlayedThisCombat.get(revengeCardsPlayedThisCombat.size() - 1);
            } else if (!this.upgraded && !revengeCardsTriggeredThisCombat.isEmpty()) {
                lastRevengeCard = revengeCardsTriggeredThisCombat.get(revengeCardsTriggeredThisCombat.size() - 1);
            }

            if (lastRevengeCard != null) {
                for (int i = 0; i < this.magicNumber; i++) {
                    if (duelist.player()) {
                        resummon(lastRevengeCard, (AbstractMonster) targets.get(0));
                    } else if (duelist.getEnemy() != null) {
                        anyDuelistResummon(lastRevengeCard, duelist, AbstractDungeon.player);
                    }
                }
            }

        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new Mimiclay();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
