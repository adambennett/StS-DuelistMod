package duelistmod.speedster.SpeedsterUtil;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//I am terrible at math so I lifted this from Alchyr's puppeteer mod, I hope he'll forgive me.
public class AlchHelper {
    //Hitbox
    public static boolean testHitboxLine(float x1, float y1, float x2, float y2, Hitbox hb) {
        float hbRightX = hb.x + hb.width;
        float hbTopY = hb.y + hb.height;

        return (testLineIntersect(x1, y1, x2, y2, hb.x, hb.y, hb.x, hbTopY)) ||
                (testLineIntersect(x1, y1, x2, y2, hb.x, hbTopY, hbRightX, hbTopY)) ||
                (testLineIntersect(x1, y1, x2, y2, hbRightX, hb.y, hbRightX, hbTopY)) ||
                (testLineIntersect(x1, y1, x2, y2, hb.x, hb.y, hbRightX, hb.y));
    }


    public static boolean testLineIntersect(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y) {
        float s1_x, s1_y, s2_x, s2_y;
        s1_x = p1_x - p0_x;
        s1_y = p1_y - p0_y;
        s2_x = p3_x - p2_x;
        s2_y = p3_y - p2_y;

        float s, t;
        float u = -s2_x * s1_y + s1_x * s2_y;
        s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / u;
        t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / u;

        return (s >= 0 && s <= 1 && t >= 0 && t <= 1);
    }


    public static AbstractMonster getClosestMonster(float x, float y, float limit) {
        AbstractMonster closest = null;
        if (AbstractDungeon.getCurrMapNode() == null || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getMonsters() == null) {
            return closest;
        }
        ArrayList<AbstractMonster> monsters = new ArrayList<>(AbstractDungeon.getMonsters().monsters);
        monsters.removeIf(AbstractCreature::isDeadOrEscaped);

        float minDist = -1;
        for (AbstractMonster m : monsters) {
            if (closest == null) {
                closest = m;
                minDist = dist(x, m.hb.cX, y, m.hb.cY);
            } else if (Math.abs(m.hb.cX - x) < minDist && Math.abs(m.hb.cY - y) < minDist) {
                float dist = dist(x, m.hb.cX, y, m.hb.cY);
                if (dist < minDist) {
                    closest = m;
                    minDist = dist;
                }
            }
        }

        if(limit != -1) {
            if(minDist > limit) {
                return null;
            }
        }

        return closest;
    }

    public static AbstractMonster getClosestMonster(float x, float y) {
        return getClosestMonster(x, y, -1);
    }

    //Math
    public static float dist(float x1, float x2, float y1, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static float angle(float x1, float x2, float y1, float y2) {
        return MathUtils.atan2(y2 - y1, x2 - x1) * 180.0f / MathUtils.PI;
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
