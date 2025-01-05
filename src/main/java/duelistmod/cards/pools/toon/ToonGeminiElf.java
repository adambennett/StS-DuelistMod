package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.CardFinderHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;
import java.util.ArrayList;
import java.util.List;

public class ToonGeminiElf extends DuelistCard {

    public static final String ID = duelistmod.DuelistMod.makeID("ToonGeminiElf");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.TOON_GEMINI_ELF);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public ToonGeminiElf() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.summons = this.baseSummons = 2;
        this.baseMagicNumber = this.magicNumber = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.REQUIRES_TOON_WORLD);
        this.tags.add(Tags.TOON);
        this.tags.add(Tags.SPELLCASTER);
        this.tags.add(Tags.FULL);
		this.originalName = this.name;
        this.isSummon = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        summon();
        AnyDuelist duelist = AnyDuelist.from(this);
        List<List<? extends AbstractCard>> allGroups = new ArrayList<>();
        allGroups.add(TheDuelist.cardPool.group);
        allGroups.add(DuelistMod.duelColorlessCards);
        allGroups.add(DuelistMod.myCards);
        ArrayList<AbstractCard> randomCards = CardFinderHelper.find(this.magicNumber, allGroups, (c) ->  c.hasTag(Tags.SPELL) && c.type == CardType.ATTACK && !c.hasTag(Tags.NEVER_GENERATE));
        for (AbstractCard randomMonster : randomCards) {
            if (duelist.player()) {
                boolean isSummon = randomMonster instanceof DuelistCard && ((DuelistCard) randomMonster).isSummonCard();
                this.addToBot(new RandomizedHandAction(randomMonster, false, true, false, true, false, isSummon, false, false, 1, 3, 0, 0, 0, 1));
            } else if (duelist.getEnemy() != null) {
                duelist.addCardToHand(randomMonster.makeStatEquivalentCopy());
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ToonGeminiElf();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
