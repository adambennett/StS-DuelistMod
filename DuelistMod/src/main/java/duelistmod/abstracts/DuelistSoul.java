package duelistmod.abstracts;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Pool;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.CardTrailEffect;
import com.megacrit.cardcrawl.vfx.combat.EmpowerEffect;

import duelistmod.characters.TheDuelist;

public class DuelistSoul
{
    public AbstractCard card;
    public CardGroup group;
    private CatmullRomSpline<Vector2> crs;
    private ArrayList<Vector2> controlPoints;
    private Vector2[] points;
    private Vector2 pos;
    private Vector2 target;
    private float backUpTimer;
    private float vfxTimer;
    private float spawnStutterTimer;
    private boolean isInvisible;
    public static final Pool<CardTrailEffect> trailEffectPool;
    private static final float DISCARD_X;
    private static final float DISCARD_Y;
    private static final float DRAW_PILE_X;
    private static final float DRAW_PILE_Y;
    private static final float MASTER_DECK_X;
    private static final float MASTER_DECK_Y;
    private float currentSpeed;
    private static final float START_VELOCITY;
    private static final float MAX_VELOCITY;
    private static final float VELOCITY_RAMP_RATE;
    public boolean isReadyForReuse;
    public boolean isDone;
    private static final float DST_THRESHOLD;
    private static final float HOME_IN_THRESHOLD;
    private float rotation;
    private boolean rotateClockwise;
    private boolean stopRotating;
    private float rotationRate;
    private static final float ROTATION_RATE;
    private Vector2 tmp;
    private final AbstractMonster cardTarget;
    
    public DuelistSoul(AbstractMonster target) {
        this.crs = new CatmullRomSpline<Vector2>();
        this.controlPoints = new ArrayList<Vector2>();
        this.points = new Vector2[20];
        this.vfxTimer = 0.015f;
        this.spawnStutterTimer = 0.0f;
        this.isInvisible = false;
        this.currentSpeed = 0.0f;
        this.rotateClockwise = true;
        this.stopRotating = false;
        this.tmp = new Vector2();
        this.crs.controlPoints = new Vector2[1];
        this.isReadyForReuse = true;
        this.cardTarget = target;
    }
    
    public void discard(final AbstractCard card, final boolean visualOnly) {
        this.card = card;
        this.group = AbstractDungeon.player.discardPile;
        if (!visualOnly) {
            this.group.addToTop(card);
        }
        this.pos = new Vector2(card.current_x, card.current_y);
        this.target = new Vector2(DuelistSoul.DISCARD_X, DuelistSoul.DISCARD_Y);
        this.setSharedVariables();
        this.rotation = card.angle + 270.0f;
        this.rotateClockwise = false;
        if (Settings.FAST_MODE) {
            this.currentSpeed = DuelistSoul.START_VELOCITY * MathUtils.random(4.0f, 6.0f);
        }
        else {
            this.currentSpeed = DuelistSoul.START_VELOCITY * MathUtils.random(1.0f, 4.0f);
        }
    }
    
    public void discard(final AbstractCard card) {
        this.discard(card, false);
    }
    
    public void shuffle(final AbstractCard card, final boolean isInvisible) {
        this.isInvisible = isInvisible;
        this.card = card;
        (this.group = AbstractDungeon.player.drawPile).addToTop(card);
        this.pos = new Vector2(DuelistSoul.DISCARD_X, DuelistSoul.DISCARD_Y);
        this.target = new Vector2(DuelistSoul.DRAW_PILE_X, DuelistSoul.DRAW_PILE_Y);
        this.setSharedVariables();
        this.rotation = MathUtils.random(260.0f, 310.0f);
        if (Settings.FAST_MODE) {
            this.currentSpeed = DuelistSoul.START_VELOCITY * MathUtils.random(8.0f, 12.0f);
        }
        else {
            this.currentSpeed = DuelistSoul.START_VELOCITY * MathUtils.random(2.0f, 5.0f);
        }
        this.rotateClockwise = true;
        this.spawnStutterTimer = MathUtils.random(0.0f, 0.12f);
    }
    
    public void onToDeck(final AbstractCard card, final boolean randomSpot, final boolean visualOnly) {
        this.card = card;
        this.group = AbstractDungeon.player.drawPile;
        if (!visualOnly) {
            if (randomSpot) {
                this.group.addToRandomSpot(card);
            }
            else {
                this.group.addToTop(card);
            }
        }
        this.pos = new Vector2(card.current_x, card.current_y);
        this.target = new Vector2(DuelistSoul.DRAW_PILE_X, DuelistSoul.DRAW_PILE_Y);
        this.setSharedVariables();
        this.rotation = card.angle + 270.0f;
        this.rotateClockwise = true;
    }
    
    public void onToDeck(final AbstractCard card, final boolean randomSpot) {
        this.onToDeck(card, randomSpot, false);
    }
    
    public void onToBottomOfDeck(final AbstractCard card) {
        this.card = card;
        (this.group = AbstractDungeon.player.drawPile).addToBottom(card);
        this.pos = new Vector2(card.current_x, card.current_y);
        this.target = new Vector2(DuelistSoul.DRAW_PILE_X, DuelistSoul.DRAW_PILE_Y);
        this.setSharedVariables();
        this.rotation = card.angle + 270.0f;
        this.rotateClockwise = true;
    }
    
    public void empower(final AbstractCard card) {
        CardCrawlGame.sound.play("CARD_POWER_WOOSH", 0.1f);
        this.card = card;
        this.group = null;
        this.pos = new Vector2(card.current_x, card.current_y);
        this.target = new Vector2(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY);
        this.setSharedVariables();
    }
    
    public void empowerResummon(final AbstractCard card) {
        CardCrawlGame.sound.play("theDuelist:ResummonWhoosh", 0.1f);
        this.card = card;
        this.group = TheDuelist.resummonPile;
        this.pos = new Vector2(card.current_x, card.current_y);
        this.target = new Vector2(cardTarget.healthHb.cX, cardTarget.healthHb.cY);
        this.setSharedVariables();
    }
    
    public void obtain(final AbstractCard card) {
        this.card = card;
        (this.group = AbstractDungeon.player.masterDeck).addToTop(card);
        if (ModHelper.isModEnabled("Hoarder")) {
            this.group.addToTop(card.makeStatEquivalentCopy());
            this.group.addToTop(card.makeStatEquivalentCopy());
        }
        this.pos = new Vector2(card.current_x, card.current_y);
        this.target = new Vector2(DuelistSoul.MASTER_DECK_X, DuelistSoul.MASTER_DECK_Y);
        this.setSharedVariables();
    }
    
    private void setSharedVariables() {
        this.controlPoints.clear();
        if (Settings.FAST_MODE) {
            this.rotationRate = DuelistSoul.ROTATION_RATE * MathUtils.random(4.0f, 6.0f);
            this.currentSpeed = DuelistSoul.START_VELOCITY * MathUtils.random(1.0f, 1.5f);
            this.backUpTimer = 0.5f;
        }
        else {
            this.rotationRate = DuelistSoul.ROTATION_RATE * MathUtils.random(1.0f, 2.0f);
            this.currentSpeed = DuelistSoul.START_VELOCITY * MathUtils.random(0.2f, 1.0f);
            this.backUpTimer = 1.5f;
        }
        this.stopRotating = false;
        this.rotateClockwise = MathUtils.randomBoolean();
        this.rotation = (float)MathUtils.random(0, 359);
        this.isReadyForReuse = false;
        this.isDone = false;
    }
    
    public void update() {
        if (this.isCarryingCard()) {
            this.card.update();
            this.card.targetAngle = this.rotation + 90.0f;
            this.card.current_x = this.pos.x;
            this.card.current_y = this.pos.y;
            this.card.target_x = this.card.current_x;
            this.card.target_y = this.card.current_y;
            if (this.spawnStutterTimer > 0.0f) {
                this.spawnStutterTimer -= Gdx.graphics.getDeltaTime();
                return;
            }
            this.updateMovement();
            this.updateBackUpTimer();
        }
        else {
            this.isDone = true;
        }
        if (this.isDone) {
            if (this.group == null) {
                AbstractDungeon.effectList.add(new EmpowerEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
                this.isReadyForReuse = true;
                return;
            }
            switch (this.group.type) {
                case MASTER_DECK: {
                    this.card.setAngle(0.0f);
                    this.card.targetDrawScale = 0.75f;
                    break;
                }
                case DRAW_PILE: {
                    this.card.targetDrawScale = 0.75f;
                    this.card.setAngle(0.0f);
                    this.card.lighten(false);
                    this.card.clearPowers();
                    AbstractDungeon.overlayMenu.combatDeckPanel.pop();
                    break;
                }
                case DISCARD_PILE: {
                    this.card.targetDrawScale = 0.75f;
                    this.card.setAngle(0.0f);
                    this.card.lighten(false);
                    this.card.clearPowers();
                    this.card.teleportToDiscardPile();
                    AbstractDungeon.overlayMenu.discardPilePanel.pop();
                }
			case CARD_POOL:
				break;
			case EXHAUST_PILE:
				break;
			case HAND:
				break;
			case UNSPECIFIED:
				AbstractDungeon.effectList.add(new EmpowerEffect(cardTarget.healthHb.cX, cardTarget.healthHb.cY));
	            this.isReadyForReuse = true;
				return;
			default:
				break;
            }
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AbstractDungeon.player.hand.applyPowers();
            }
            this.isReadyForReuse = true;
        }
    }
    
    private boolean isCarryingCard() {
        if (this.group == null) {
            return true;
        }
        switch (this.group.type) {
            case DRAW_PILE: {
                return AbstractDungeon.player.drawPile.contains(this.card);
            }
            case DISCARD_PILE: {
                return AbstractDungeon.player.discardPile.contains(this.card);
            }
            default: {
                return true;
            }
        }
    }
    
    private void updateMovement() {
        this.tmp.x = this.pos.x - this.target.x;
        this.tmp.y = this.pos.y - this.target.y;
        this.tmp.nor();
        final float targetAngle = this.tmp.angle();
        this.rotationRate += Gdx.graphics.getDeltaTime() * 800.0f;
        if (!this.stopRotating) {
            if (this.rotateClockwise) {
                this.rotation += Gdx.graphics.getDeltaTime() * this.rotationRate;
            }
            else {
                this.rotation -= Gdx.graphics.getDeltaTime() * this.rotationRate;
                if (this.rotation < 0.0f) {
                    this.rotation += 360.0f;
                }
            }
            this.rotation %= 360.0f;
            if (!this.stopRotating) {
                if (this.target.dst(this.pos) < DuelistSoul.HOME_IN_THRESHOLD) {
                    this.rotation = targetAngle;
                    this.stopRotating = true;
                }
                else if (Math.abs(this.rotation - targetAngle) < Gdx.graphics.getDeltaTime() * this.rotationRate) {
                    this.rotation = targetAngle;
                    this.stopRotating = true;
                }
            }
        }
        this.tmp.setAngle(this.rotation);
        final Vector2 tmp = this.tmp;
        tmp.x *= Gdx.graphics.getDeltaTime() * this.currentSpeed;
        final Vector2 tmp2 = this.tmp;
        tmp2.y *= Gdx.graphics.getDeltaTime() * this.currentSpeed;
        this.pos.sub(this.tmp);
        if (this.stopRotating && this.backUpTimer < 1.3499999f) {
            this.currentSpeed += Gdx.graphics.getDeltaTime() * DuelistSoul.VELOCITY_RAMP_RATE * 3.0f;
        }
        else {
            this.currentSpeed += Gdx.graphics.getDeltaTime() * DuelistSoul.VELOCITY_RAMP_RATE * 1.5f;
        }
        if (this.currentSpeed > DuelistSoul.MAX_VELOCITY) {
            this.currentSpeed = DuelistSoul.MAX_VELOCITY;
        }
        if (this.target.x < Settings.WIDTH / 2.0f && this.pos.x < 0.0f) {
            this.isDone = true;
        }
        else if (this.target.x > Settings.WIDTH / 2.0f && this.pos.x > Settings.WIDTH) {
            this.isDone = true;
        }
        if (this.target.dst(this.pos) < DuelistSoul.DST_THRESHOLD) {
            this.isDone = true;
        }
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (!this.isDone && this.vfxTimer < 0.0f) {
            this.vfxTimer = 0.015f;
            if (!this.controlPoints.isEmpty()) {
                if (!this.controlPoints.get(0).equals(this.pos)) {
                    this.controlPoints.add(this.pos.cpy());
                }
            }
            else {
                this.controlPoints.add(this.pos.cpy());
            }
            if (this.controlPoints.size() > 10) {
                this.controlPoints.remove(0);
            }
            if (this.controlPoints.size() > 3) {
                final Vector2[] vec2Array = new Vector2[0];
                this.crs.set(this.controlPoints.toArray(vec2Array), false);
                for (int i = 0; i < 20; ++i) {
                    if (this.points[i] == null) {
                        this.points[i] = new Vector2();
                    }
                    final Vector2 derp = this.crs.valueAt(this.points[i], i / 19.0f);
                    final CardTrailEffect effect = DuelistSoul.trailEffectPool.obtain();
                    effect.init(derp.x, derp.y);
                    AbstractDungeon.topLevelEffects.add(effect);
                }
            }
        }
    }
    
    private void updateBackUpTimer() {
        this.backUpTimer -= Gdx.graphics.getDeltaTime();
        if (this.backUpTimer < 0.0f) {
            this.isDone = true;
        }
    }
    
    public void render(final SpriteBatch sb) {
        if (!this.isInvisible) {
            this.card.renderOuterGlow(sb);
            this.card.render(sb);
        }
    }
    
    static {
        trailEffectPool = new Pool<CardTrailEffect>() {
            @Override
            protected CardTrailEffect newObject() {
                return new CardTrailEffect();
            }
        };
        DISCARD_X = Settings.WIDTH * 0.96f;
        DISCARD_Y = Settings.HEIGHT * 0.06f;
        DRAW_PILE_X = Settings.WIDTH * 0.04f;
        DRAW_PILE_Y = Settings.HEIGHT * 0.06f;
        MASTER_DECK_X = Settings.WIDTH - 96.0f * Settings.scale;
        MASTER_DECK_Y = Settings.HEIGHT - 32.0f * Settings.scale;
        START_VELOCITY = 200.0f * Settings.scale;
        MAX_VELOCITY = 6000.0f * Settings.scale;
        VELOCITY_RAMP_RATE = 3000.0f * Settings.scale;
        DST_THRESHOLD = 36.0f * Settings.scale;
        HOME_IN_THRESHOLD = 72.0f * Settings.scale;
        ROTATION_RATE = 150.0f * Settings.scale;
    }
}
