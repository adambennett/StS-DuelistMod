package duelistmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Tags;

public class TypeConversionEffect extends AbstractGameEffect {


    private final Color screenColor;
    private final CardTags tag;

    public TypeConversionEffect(CardTags tag) {
        this.tag = tag;
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.5f;
        this.screenColor.a = 0.0f;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    @Override
    public void update() {
        if (this.tag == null) {
            this.isDone = true;
            return;
        }

        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            this.updateBlackScreenColor();
        }
        if (!AbstractDungeon.isScreenUp && !DuelistMod.duelistCardSelectScreen.selectedCards.isEmpty()) {
            for (final AbstractCard c : DuelistMod.duelistCardSelectScreen.selectedCards) {
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                addTagToCard(c, this.tag);
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            }
            DuelistMod.duelistCardSelectScreen.selectedCards.clear();
        }
        /*if (this.duration < 1.0f && !this.openedScreen) {
            this.openedScreen = true;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, CampfireSmithEffect.TEXT[0], true, false, true, false);
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                r.onSmith();
            }
        }*/
        if (this.duration < 0.0f) {
            this.isDone = true;
        }
    }

    private void updateBlackScreenColor() {
        if (this.duration > 1.0f) {
            this.screenColor.a = Interpolation.fade.apply(1.0f, 0.0f, (this.duration - 1.0f) * 2.0f);
        }
        else {
            this.screenColor.a = Interpolation.fade.apply(0.0f, 1.0f, this.duration / 1.5f);
        }
    }

    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0f, 0.0f, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }
    }

    @Override
    public void dispose() {}

    private void addTagToCard(AbstractCard card, CardTags tag) {
        if (!card.hasTag(tag)) {
            card.tags.add(tag);
            card.rawDescription = DuelistMod.typeCardMap_NAME.get(tag) + " NL " + card.rawDescription;
            if (card instanceof DuelistCard && tag.equals(Tags.MEGATYPED)) {
                DuelistCard dc = (DuelistCard)card;
                dc.makeMegatyped();
                dc.fixUpgradeDesc();
            } else if (card instanceof DuelistCard) {
                DuelistCard dc = (DuelistCard)card;
                dc.fixUpgradeDesc();
                dc.originalDescription = card.rawDescription;
                dc.isTypeAddedPerm = true;
                dc.savedTypeMods.add(DuelistMod.typeCardMap_NAME.get(tag));
            }
            card.initializeDescription();
        }
    }
}
