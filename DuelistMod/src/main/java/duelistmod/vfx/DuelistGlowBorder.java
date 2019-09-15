package duelistmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;

import duelistmod.abstracts.DuelistCard;

public class DuelistGlowBorder extends CardGlowBorder
{
    private DuelistCard card;
    private TextureAtlas.AtlasRegion img;
    private float scale;
    
    public DuelistGlowBorder(final DuelistCard card) {
        this(card, DuelistCard.GlowColor.BLUE);
    }
    
    public DuelistGlowBorder(final DuelistCard card, final DuelistCard.GlowColor gColor) {
        super(card, AbstractCard.GlowColor.BLUE);
    	this.card = card;
        switch (card.type) {
            case POWER: {
                this.img = ImageMaster.CARD_POWER_BG_SILHOUETTE;
                break;
            }
            case ATTACK: {
                this.img = ImageMaster.CARD_ATTACK_BG_SILHOUETTE;
                break;
            }
            default: {
                this.img = ImageMaster.CARD_SKILL_BG_SILHOUETTE;
                break;
            }
        }
        this.duration = 1.2f;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            switch (gColor) {
                case BLUE: {
                    this.color = Color.valueOf("30c8dcff");
                    break;
                }
                case GOLD: {
                    this.color = Color.GOLD.cpy();
                    break;
                }
                case RED: {
                    this.color = Color.GOLD.cpy();
                    break;
                }
                case GREEN: {
                    this.color = Color.GREEN.cpy();
                    break;
                }
                case PURPLE: {
                    this.color = Color.PURPLE.cpy();
                    break;
                }
                case ORANGE: {
                    this.color = Color.ORANGE.cpy();
                    break;
                }
                case WHITE: {
                    this.color = Color.WHITE.cpy();
                    break;
                }
                default: {
                    this.color = Color.PURPLE.cpy();
                    break;
                }
            }
        }
        else {
            this.color = Color.GREEN.cpy();
        }
    }
    
    @Override
    public void update() {
        this.scale = (1.0f + Interpolation.pow2Out.apply(0.03f, 0.11f, 1.0f - this.duration)) * this.card.drawScale * Settings.scale;
        this.color.a = this.duration / 2.0f;
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            this.isDone = true;
            this.duration = 0.0f;
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.card.current_x + this.img.offsetX - this.img.originalWidth / 2.0f, this.card.current_y + this.img.offsetY - this.img.originalHeight / 2.0f, this.img.originalWidth / 2.0f - this.img.offsetX, this.img.originalHeight / 2.0f - this.img.offsetY, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.card.angle);
    }
    
    @Override
    public void dispose() {
    }
}
