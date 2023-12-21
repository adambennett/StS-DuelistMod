package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.relics.CardPoolOptionsRelic;
import duelistmod.variables.Strings;

public class CardPoolOptionResetSaveSlot extends DuelistCard {

    public static final String ID = DuelistMod.makeID("CardPoolOptionResetSaveSlot");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GENERIC_RESET_SAVE_SLOT);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public 	static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = -2;
    private final int slot;
    private final String targetId;

    public CardPoolOptionResetSaveSlot(int slot) {
    	super(ID + slot, NAME + slot, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.dontTriggerOnUseCard = true;
        this.slot = slot;
        this.targetId = "theDuelist:CardPoolOptionSaveSlot" + slot;
        this.baseMagicNumber = this.magicNumber = slot;
    }
   
    // Call this when player selects card from Options Relic
    public void loadPool()
    {
        DuelistMod.persistentDuelistData.cardPoolSaveSlotMap.remove(this.targetId);
        DuelistMod.configSettingsLoader.save();
        if (AbstractDungeon.player.hasRelic(CardPoolOptionsRelic.ID)) {
            CardPoolOptionsRelic rel = (CardPoolOptionsRelic)AbstractDungeon.player.getRelic(CardPoolOptionsRelic.ID);
            rel.refreshPool();
        }
    }

    @Override
    public void upgrade() {}

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}

    @Override public AbstractCard makeCopy() { return new CardPoolOptionResetSaveSlot(this.slot); }

    public int getSlot() {
        return slot;
    }

    public String getTargetId() {
        return targetId;
    }
}
