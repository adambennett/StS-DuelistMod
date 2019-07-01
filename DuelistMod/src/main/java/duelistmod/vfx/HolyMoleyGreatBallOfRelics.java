package duelistmod.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class HolyMoleyGreatBallOfRelics extends AbstractGameEffect {
    public static final float bounceplanemin = 300 * Settings.scale;
    public static final float bounceplanemax = 500 * Settings.scale;
    public static final float ballradius = 3 * Settings.scale; //Adds some randomness to the relic ball based on how many relics you have.
    public static final int maxdelay = 20; //Puts a bit of randomness on when the relics start flying towards you for forming the ball.
    public static final int dispersalspeed = 1;
    public static final float gravity = 0.5F * Settings.scale;
    public static final float frictionX = 0.2F * Settings.scale;
    public static final float frictionY = 0.2F * Settings.scale;
    public static float CHAOS = 1.5F; //Determins the velocity at which the relics disperse after impact. No scaling necessary.
    public static int gatherspeed = 50; //Over how much time do the relics fly towards you
    public static int flighttime = 20;
    public boolean finishedAction;
    ArrayList<BallRelicData> relics = new ArrayList<>();
    private Phase phase;
    private int frames;

    public HolyMoleyGreatBallOfRelics(AbstractCreature ac, boolean vexOnly, float CHAOS, int gatherspeed, int flighttime) {
        HolyMoleyGreatBallOfRelics.CHAOS = CHAOS;
        HolyMoleyGreatBallOfRelics.gatherspeed = gatherspeed;
        HolyMoleyGreatBallOfRelics.flighttime = flighttime;
        for (final AbstractRelic ar : AbstractDungeon.player.relics) {
            if (vexOnly) {
                if (ar.relicId.startsWith("vexMod:")) {
                    relics.add(new BallRelicData(ar, ac));
                }
            } else {
                relics.add(new BallRelicData(ar, ac));
            }
        }
        for (final BallRelicData brd : relics) {
            brd.setBallradius(ballradius * relics.size());
        }
        phase = Phase.gathering;

        finishedAction = false;
    }

    public HolyMoleyGreatBallOfRelics(AbstractCreature ac, boolean vexOnly) {
        for (final AbstractRelic ar : AbstractDungeon.player.relics) {
            if (vexOnly) {
                if (ar.relicId.startsWith("vexMod:")) {
                    relics.add(new BallRelicData(ar, ac));
                }
            } else {
                relics.add(new BallRelicData(ar, ac));
            }
        }
        for (final BallRelicData brd : relics) {
            brd.setBallradius(ballradius * relics.size());
        }
        phase = Phase.gathering;

        finishedAction = false;
    }

    public void render(SpriteBatch sb) {
        for (final BallRelicData rd : relics) {
            rd.render(sb);
        }
        sb.setColor(Color.WHITE);
    }

    public void update() {
        boolean phasefinished = true;
        switch (phase) {
            case gathering:
                for (final BallRelicData rd : relics) {
                    phasefinished &= rd.updateOne();
                }
                if (phasefinished) {
                    phase = Phase.gathered;
                }
                break;
            case gathered:
                /*
                 * Any special effects that you want while the ball is hovering there.
                 */
                if (phasefinished) {
                    phase = Phase.shooting;
                    frames = 0;
                    AbstractDungeon.player.useFastAttackAnimation();
                }
                break;
            case shooting:
                for (final BallRelicData rd : relics) {
                    rd.updateTwo();
                }
                if (frames++ == flighttime) {
                    phase = Phase.dispersing;
                    for (final BallRelicData rd : relics) {
                        rd.setupDispersal();
                    }
                    finishedAction = true;

                }
                break;
            case dispersing:
                for (final BallRelicData rd : relics) {
                    phasefinished &= rd.updateThree();
                }
                if (phasefinished) {
                    this.isDone = true;
                }
                break;
        }
    }

    public void dispose() {

    }

    enum Phase {
        gathering,
        gathered,
        shooting,
        dispersing
    }
}