package com.luzi82.gdx;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Vector;

public abstract class OnScaleChange extends Group {

    private Vector<Vector2[]> vectorPairList = new Vector<Vector2[]>();
    private float[] dstAry;
    private float epsilon = 0.1f / 1000;

    public void addVectorPair(Vector2 a, Vector2 b) {
        Vector2[] ab = new Vector2[]{a, b};
        vectorPairList.add(ab);
        dstAry = null;
    }

    // tmp var for reuse
    private float[] dstAryTmp;
    Vector2 tmpAPt = new Vector2();
    Vector2 tmpBPt = new Vector2();

    public void act(float delta) {
        super.act(delta);

        Stage stage = getStage();
        if (stage == null) return;

        int vectorPairListSize = vectorPairList.size();
        if ((dstAryTmp == null) || (dstAryTmp.length != vectorPairListSize)) {
            dstAryTmp = new float[vectorPairListSize];
        }
        for (int i = 0; i < vectorPairListSize; ++i) {
            Vector2[] ab = vectorPairList.get(i);
            tmpAPt.set(ab[0]);
            tmpAPt = localToStageCoordinates(tmpAPt);
            tmpAPt = stage.stageToScreenCoordinates(tmpAPt);
            tmpBPt.set(ab[1]);
            tmpBPt = localToStageCoordinates(tmpBPt);
            tmpBPt = stage.stageToScreenCoordinates(tmpBPt);
            dstAryTmp[i] = tmpAPt.dst(tmpBPt);
        }

        boolean same = true;

        if (same && (dstAry == null)) {
            same = false;
        }

        if (same && (dstAry.length != vectorPairListSize)) {
            same = false;
        }

        if (same) {
            for (int i = 0; i < vectorPairListSize; ++i) {
                if (Math.abs(dstAry[i] - dstAryTmp[i]) < epsilon) {
                    continue;
                }
                same = false;
                break;
            }
        }

        if (same)
            return;

        float[] tmp = dstAry;
        dstAry = dstAryTmp;
        dstAryTmp = tmp;
        onScaleChange();
    }

    public abstract void onScaleChange();

}
