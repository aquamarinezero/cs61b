public class Body{
	public double xxPos; //其当前x位置
	public double yyPos; //其当前y位置
	public double xxVel; //其当前x速度
	public double yyVel; //其当前y速度
	public double mass;  //质量
	public String imgFileName;
	public Body(double xP, double yP, double xV,
		double yV, double m, String img){
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}
	public Body(Body b){
		this.xxPos = b.xxPos;
		this.yyPos = b.yyPos;
		this.xxVel = b.xxVel;
		this.yyVel = b.yyVel;
		this.mass = b.mass;
		this.imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b){
		return Math.sqrt((this.xxPos-b.xxPos)*(this.xxPos-b.xxPos)+(this.yyPos-b.yyPos)*(this.yyPos-b.yyPos));
	}

	public double calcForceExertedBy(Body b){
		double G = 6.67E-11;
		double F = G * this.mass * b.mass / calcDistance(b)/calcDistance(b);
		return F;
	}
	public double calcForceExertedByX(Body b) {
		double Fx = calcForceExertedBy(b) * (this.xxPos-b.xxPos) / calcDistance(b);
		if(Fx < 0)
			return -Fx;
		return Fx;
	}
	public double calcForceExertedByY(Body b) {
		double Fy =  calcForceExertedBy(b) * (this.yyPos-b.yyPos) / calcDistance(b);
		if(Fy < 0)
			return -Fy;
		return Fy;
	}

	public double calcNetForceExertedByX(Body[] allBodys){
		double result = 0.0;
		for(Body b:allBodys){
			if(!this.equals(b))
				result += calcForceExertedByX(b);
		}
		return result;
	}
	public double calcNetForceExertedByY(Body[] allBodys){
		double result = 0.0;
		for(Body b:allBodys){
			if(!this.equals(b))
				result += calcForceExertedByY(b);
		}
		return result;
	}
	public void update(double dt,double fX,double fY){
		double ax = fX/this.mass;
		double ay = fY/this.mass;
		this.xxVel += dt * ax;
		this.yyVel += dt * ay;
		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
	}

	public void draw(){
		StdDraw.enableDoubleBuffering();
		StdDraw.picture(this.xxPos,this.yyPos,this.imgFileName);
	}
}