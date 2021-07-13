package priv.wzb.javabook.innerclasses;

/**
 * @program: Design_Pattern
 * @description:
 * @author: wangzibai
 * @create: 2020-08-31 16:54
 **/

public class Parcel6 {
    private void internalTracking(boolean b){
        if (b){
            class TrackingSlip{
                private String id;

                public TrackingSlip(String s) {
                    id=s;
                }

                String getSlip(){
                    return id;
                }
            }
            TrackingSlip ts = new TrackingSlip("slip");
            String s = ts.getSlip();
        }
        // Can't use it here! Out of scope:
        //- TrackingSlip ts = new TrackingSlip("x");

        {
            class TrackingSlip1{
                private String id;

                public TrackingSlip1(String s) {
                    id=s;
                }

                String getSlip(){
                    return id;
                }
            }
            TrackingSlip1 ts = new TrackingSlip1("slip");
            String s = ts.getSlip();
        }


    }

    public void track(){
        internalTracking(true);
    }

    public static void main(String[] args) {
        Parcel6 p = new Parcel6();
        p.track();
    }
}
