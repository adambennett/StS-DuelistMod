package duelistmod.relics.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class CombatBracelet extends DuelistRelic {

    public static final String ID = DuelistMod.makeID("CombatBracelet");
    public static final String IMG = DuelistMod.makeRelicPath("CombatBracelet.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("CombatBraceletOutline.png");

    public CombatBracelet() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.FLAT);
        this.tips.add(new PowerTip("Special Summon", DuelistMod.specialSummonKeywordDescription));
    }

    private boolean checkedThisCombat = false;

    @Override
    public boolean canSpawn() {
        boolean superCheck = super.canSpawn();
        if (!superCheck) return false;
        return Util.deckIs("Warrior Deck");
    }

    @Override
    public void atBattleStart() {
        checkedThisCombat = false;
        this.grayscale = false;
        this.pulse = true;
        beginPulse();
    }

    @Override
    public void onVictory() {
        checkedThisCombat = false;
        this.grayscale = false;
        this.pulse = false;
        stopPulse();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (checkedThisCombat || card == null || !card.hasTag(Tags.WARRIOR)) return;

        checkedThisCombat = true;
        this.pulse = false;
        this.grayscale = true;
        stopPulse();

        if (AbstractDungeon.cardRandomRng.random(100) < 25) {
            this.flash();
            AnyDuelist duelist = AnyDuelist.from(this);
            AbstractCard c = card.makeStatEquivalentCopy();
            if (duelist.player()) {
                AbstractMonster mon = AbstractDungeon.getRandomMonster();
                if (mon != null && !mon.isDying && !mon.isDead) {
                    DuelistCard.resummon(c, mon, false, true);
                }
            } else if (duelist.getEnemy() != null) {
                DuelistCard.anyDuelistResummon(c, duelist, AbstractDungeon.player, true);
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
