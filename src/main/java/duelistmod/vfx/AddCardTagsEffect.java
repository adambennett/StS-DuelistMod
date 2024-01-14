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

import java.util.ArrayList;
import java.util.List;

public class AddCardTagsEffect extends AbstractGameEffect {


    private final Color screenColor;
    private final CardTags tag;
    private final List<AbstractCard> selected;

    public AddCardTagsEffect(CardTags tag, ArrayList<AbstractCard> selected) {
        this.tag = tag;
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.5f;
        this.screenColor.a = 0.0f;
        this.selected = new ArrayList<>();
        this.selected.addAll(selected);
        if (AbstractDungeon.overlayMenu != null && AbstractDungeon.overlayMenu.proceedButton != null) {
            AbstractDungeon.overlayMenu.proceedButton.hide();
        }
    }

    @Override
    public void update() {
        if (this.tag == null) {
            this.isDone = true;
            return;
        }

        /*if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            this.updateBlackScreenColor();
        }*/
        if (!this.selected.isEmpty()) {
            for (final AbstractCard c : this.selected) {
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                addTagToCard(c, this.tag);
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            }
            DuelistMod.duelistCardSelectScreen.selectedCards.clear();
            this.isDone = true;
            return;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
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
        if (!card.hasTag(tag) && card instanceof DuelistCard) {
            DuelistCard dc = (DuelistCard)card;
            if (dc.notAddedTagToDescription(DuelistMod.typeCardMap_NAME.get(tag))) {
                dc.tags.add(tag);
                dc.rawDescription = DuelistMod.typeCardMap_NAME.get(tag) + " NL " + dc.rawDescription;
                dc.originalDescription = DuelistMod.typeCardMap_NAME.get(tag) + " NL " + dc.originalDescription;
                dc.isTypeAddedPerm = true;
                dc.savedTypeMods.add(DuelistMod.typeCardMap_NAME.get(tag));
                dc.addTagToAddedTypeMods(DuelistMod.typeCardMap_NAME.get(tag));
                dc.fixUpgradeDesc();
                if (tag.equals(Tags.MEGATYPED)) {
                    dc.makeMegatyped();
                }
                dc.initializeDescription();
            }
        }
    }
}
