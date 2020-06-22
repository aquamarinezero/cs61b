public class NBody {
    public static double readRadius(String a){
        In in = new In(a);
        int count = in.readInt();
        double radius = in.readDouble();
        return radius;
    }
    public static Body[] readBodies(String a){
        In in = new In(a);
        int count = in.readInt();
        double radius = in.readDouble();
        Body[] result = new Body[count];
        for(int i=0;i<count;i++)
            result[i] = new Body(in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readString());
        return result;
    }

    public static void main(String[] args){
        double T =  Double.parseDouble(args[0].toString());
        double dt = Double.parseDouble(args[1].toString());
        String filename = args[2];
        Body[] body = readBodies(filename);
        double radius = readRadius(filename);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0,0,"images/starfield.jpg");
        for(Body b:body){
            b.draw();
        }
        StdDraw.show();
        StdDraw.enableDoubleBuffering(); //防止闪烁
        for(double time = 0.0;time<=T;time+=dt){
            double[] xForses = new double[body.length];
            double[] yForses = new double[body.length];
            for(int i = 0;i < body.length;i++){
                xForses[i] = body[i].calcNetForceExertedByX(body);
                yForses[i] = body[i].calcNetForceExertedByY(body);
            }
            for(int i = 0;i < body.length;i++) {
                body[i].update(dt,xForses[i],yForses[i]);
            }
            StdDraw.clear();
            StdDraw.picture(0,0,"images/starfield.jpg");
            for(Body b:body){
                b.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", body.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < body.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    body[i].xxPos, body[i].yyPos, body[i].xxVel,
                    body[i].yyVel, body[i].mass, body[i].imgFileName);
        }
    }
}
