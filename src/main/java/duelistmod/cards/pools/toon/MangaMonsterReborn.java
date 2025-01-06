package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MangaMonsterReborn extends DuelistCard {
    public static final String ID = DuelistMod.makeID("MangaMonsterReborn");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("MangaMonsterReborn.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;

    public MangaMonsterReborn() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseMagicNumber = this.magicNumber = 2;
    	this.tags.add(Tags.SPELL);
        this.tags.add(Tags.TOON);
        this.tags.add(Tags.REQUIRES_TOON_WORLD);
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
        if (this.magicNumber < 1) {
            postDuelistUseCard(owner, targets);
            return;
        }
        AnyDuelist duelist = AnyDuelist.from(this);
        CardGroup cardsToChooseFrom = new CardGroup(CardGroup.CardGroupType.DISCARD_PILE);
        cardsToChooseFrom.group = duelist.discardPile().stream()
                .filter(card -> card.hasTag(Tags.MONSTER))
                .collect(Collectors.toCollection(ArrayList::new));
        if (cardsToChooseFrom.isEmpty()) {
            postDuelistUseCard(owner, targets);
            return;
        }

        Consumer<ArrayList<AbstractCard>> resummon = group -> {
            for (int i = 0; i < this.magicNumber; i++) {
                group.forEach(DuelistCard::resummon);
            }
        };
        if (duelist.player()) {
            SelectScreenHelper.open(cardsToChooseFrom, 1, "Special Summon " + this.magicNumber + "cop" + (this.magicNumber == 1 ? "y" : "ies") + " of a Monster", true, resummon);
        } else if (duelist.getEnemy() != null) {
            AbstractCard randomSpellcaster = cardsToChooseFrom.getRandomCard(AbstractDungeon.cardRandomRng);
            for (int i = 0; i < this.magicNumber; i++) {
                DuelistCard.anyDuelistResummon(randomSpellcaster.makeStatEquivalentCopy(), duelist, AbstractDungeon.player);
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new MangaMonsterReborn();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
