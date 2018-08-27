package com.quadx.webtest.shapes1_5_1;

import com.badlogic.gdx.math.Vector2;

import static com.quadx.webtest.Game.rn;


/**
 * Created by Chris Cavazos on 9/16/2016.
 */
@SuppressWarnings("WeakerAccess")
public class EMath {
    public static float pathag(Vector2 a, Vector2 b){
        return (float) Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2));
    }
    public static float pathag(double x1, double y1, double x2, double y2){
        return (float) Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
    public static float pathag(double a, double b){
        return (float) Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
    }
    public static float angle(Vector2 a, Vector2 b){
        float angx= b.x-a.x;
        float angy= b.y-a.y;
        boolean cox,coy;
        float initdeg=(float) Math.toDegrees(Math.atan(angy/angx));
        cox = dx(b, a) >= 0;
        coy = dy(b, a) >= 0;

        if(!cox && coy){
            initdeg+=180;
        }
        if(!cox && !coy){
            initdeg+=180;
        }
        if(cox && !coy){
            initdeg+=360;
        }
        return initdeg;
    }
    public static float angleRad(Vector2 a, Vector2 b){
        return (float) Math.toRadians(angle(a,b));
    }
    public static float dx(Vector2 a, Vector2 b){
        return dx(b.x,a.x);
    }
    public static float dx(float a, float b){
        return b-a;
    }

    public static double round(double d){
        double rem=d-Math.floor(d);
        if(rem<.5f){
            return Math.floor(d);
        }else{
            return Math.ceil(d);
        }
    }
    public static Vector2 round(Vector2 v){
        Vector2 pos=new Vector2(v);
        double x =EMath.round(pos.x);
        double y =EMath.round(pos.y);
        return new Vector2((float)x,(float)y);
    }

    public static int roundToNearest45(int ang){
        int[] arr= new int[]{0,45,90,135,180,225,270,315,360};
        int index=0;
        int min=10000;
        for(int i=0;i<arr.length;i++){
            double d=dx(ang,arr[i]);
            if(d<=min){
                min= (int) d;
                index=i;
            }
        }
        return arr[index];
    }
    public static float dy(Vector2 a, Vector2 b){
        return b.y-a.y;
    }
    public static float arcL(float theta ,float r,float dl){
        return (float) (((2*Math.PI*r)/360)*dl);
    }
    public static float average(float[] f){
        float sum=0;
        for (float aF : f) {
            sum += aF;
        }
        return sum/f.length;
    }
    public static double randomGaussianAverage(double a, double b){
        double mid= (a+b)/2;
        double r=rn.nextGaussian();
        double max=1, min=1;
        if(a>b){
            min=b/mid;
            max=a/mid;
        }
        if(b>a){
            min=a/mid;
            max=b/mid;
        }
        for(int i=0;i<10 && !(r<min ||r>max);i++)
            r=rn.nextGaussian();

        return (int)mid*r;
    }
/*    public static double randomAverage(double a, double b){
        double dif=a-b;
        if(b>a) dif=b-a;
        double step=dif/100;
        double fill=step* rn.nextInt(100)+1;
        if(a>b) return b+fill;
        else return a+fill;
    }
    public static double randomGaussianAverage(double a, double b){
        double mid= (a+b)/2;
        double r=rn.nextGaussian();
        double max=1, min=1;
        if(a>b){
            min=b/mid;
            max=a/mid;
        }
        if(b>a){
            min=a/mid;
            max=b/mid;
        }
        while(r<min ||r>max)
           r=rn.nextGaussian();

        return (int)mid*r;
    }*/
}
