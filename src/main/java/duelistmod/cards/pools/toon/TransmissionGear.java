package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.enemyDuelist.EnemyMakeTempCardInDrawPileAction;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.CardFinderHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TransmissionGear extends DuelistCard {

    public static final String ID = DuelistMod.makeID("TransmissionGear");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("TransmissionGear.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;

    public TransmissionGear() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseMagicNumber = this.magicNumber = 3;
        this.baseTributes = this.tributes = 1;
    	this.tags.add(Tags.TRAP);
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
        AnyDuelist duelist = AnyDuelist.from(this);
        tribute();
        // Gain energy
        if (this.magicNumber > 0) {
            HashSet<CardTags> uniqueTypesSummonedThisCombat = new HashSet<>();
            for (AbstractCard summoned : duelist.getAllSummonedCardsThisCombat()) {
                if (uniqueTypesSummonedThisCombat.size() == DuelistMod.monsterTypes.size() || uniqueTypesSummonedThisCombat.size() >= this.magicNumber) break;
                for (CardTags tag : summoned.tags) {
                    if (!uniqueTypesSummonedThisCombat.contains(tag) && DuelistMod.monsterTypes.contains(tag)) {
                        uniqueTypesSummonedThisCombat.add(tag);
                    }
                }
            }
            int energyGain = Math.min(this.magicNumber, uniqueTypesSummonedThisCombat.size());
            duelist.gainEnergy(energyGain);
        }

        // Shuffle random machine into draw pile
        AbstractCard randomMachineCard = generateRandomMachineCard();
        if (randomMachineCard != null) {

            if (this.upgraded && randomMachineCard.canUpgrade()) {
                randomMachineCard.upgrade();
            }

            if (duelist.player()) {
                this.addToBot(new MakeTempCardInDrawPileAction(randomMachineCard.makeStatEquivalentCopy(), 1, true, true));
            } else if (duelist.getEnemy() != null) {
                this.addToBot(new EnemyMakeTempCardInDrawPileAction(randomMachineCard.makeStatEquivalentCopy(), 1, true, true));
            }
        }

        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new TransmissionGear();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    private AbstractCard generateRandomMachineCard() {
        List<List<? extends AbstractCard>> pools = Arrays.asList(
//                TheDuelist.cardPool.group,
//                DuelistMod.coloredCards,
                DuelistMod.myCards
        );
        ArrayList<AbstractCard> machines = CardFinderHelper.find(1, pools, c -> !c.hasTag(Tags.TOKEN) && c.hasTag(Tags.MACHINE));
        if (machines.size() > 0) {
            return machines.get(0);
        }
        return null;
    }

}
