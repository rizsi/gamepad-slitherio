package slitherio;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Controller.Type;

/**
 * My best: 30723
 * @author rizsi
 *
 */
public class Slitherio {
	public static void main(String[] args) throws Exception {
		String xdotool="/usr/bin/xdotool search \"slither.io\" windowmove 0 0 windowsize 940 1010";
		Runtime.getRuntime().exec(xdotool);
		System.out.println(xdotool);
		Rectangle screen=new Rectangle(5,97,943,1039);
		int rad=300;
		Robot r=new Robot();
		int x0=screen.x+screen.width/2;
		int y0=screen.y+screen.height/2-45;

        Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for(int i =0;i<ca.length;i++){

        	if(Type.GAMEPAD.equals(ca[i].getType()))
        	{
                /* Get the name of the controller */
                System.out.println(ca[i].getName());

                System.out.println("Type: "+ca[i].getType().toString());

                /* Get this controllers components (buttons and axis) */
                Component[] components = ca[i].getComponents();
                int xaxis=-1;
                int yaxis=-1;
                int rxaxis=-1;
                int ryaxis=-1;
                int button=-1;
                int bbutton=-1;
                int ybutton=-1;
                System.out.println("Component Count: "+components.length);
                for(int j=0;j<components.length;j++){
                    /* Get the components name */
                    System.out.println("Component "+j+": "+components[j].getName()+" "+components[j].getPollData());
                    if("x".equals(components[j].getName()))
                    {
                    	xaxis=j;
                    }
                    if("y".equals(components[j].getName()))
                    {
                    	yaxis=j;
                    }
                    if("rx".equals(components[j].getName()))
                    {
                    	rxaxis=j;
                    }
                    if("ry".equals(components[j].getName()))
                    {
                    	ryaxis=j;
                    }
                    if("A".equals(components[j].getName()))
                    {
                    	button=j;
                    }
                    if("B".equals(components[j].getName()))
                    {
                    	bbutton=j;
                    }
                    if("Y".equals(components[j].getName()))
                    {
                    	ybutton=j;
                    }
                }
                boolean prepressing=false;
                float deadzone=0.1f;
                boolean preypressing=false;
                float yturn=0;
                int nyiteration=0;
                double preangle=0;
                float autoturn=0;
                while(true)
                {
                	double angle=preangle;
                    ca[i].poll();
                    float x=components[xaxis].getPollData();
                    float y=components[yaxis].getPollData();
                    float rx=components[rxaxis].getPollData();
                    float ry=components[ryaxis].getPollData();
                    float a=components[button].getPollData();
                    boolean bpressing=components[bbutton].getPollData()>deadzone;
                    boolean ypressing=components[ybutton].getPollData()>deadzone;
                    double l=Math.sqrt(x*x+y*y);
                    boolean adjustmouse=false;
                    if(l>0.9)
                    {
                    	angle=Math.atan2(-y, x);
            			adjustmouse=true;
                    }else if(autoturn!=0.0f)
                    {
                    	angle+=autoturn;
            			adjustmouse=true;
                    }
                    if(Math.abs(ry)>deadzone)
                    {
                    	autoturn+=ry*0.001;
                    }
                    if(adjustmouse)
                    {
                    	r.mouseMove((int)(Math.cos(angle)*rad+x0), (int)(-Math.sin(angle)*rad+y0));
                    }
                    boolean pressing=a>deadzone;
                    if(bpressing)
                    {
                    	autoturn=0;
                    }
                    if(pressing&&!prepressing)
                    {
            			r.mousePress(InputEvent.BUTTON1_MASK);
                    }
                    if(prepressing&&!pressing)
                    {
            			r.mouseRelease(InputEvent.BUTTON1_MASK);
                    }
                    if(ypressing)
                    {
                    	nyiteration++;
                    	yturn+=toCircle(angle-preangle);
                    } else if(preypressing)
                    {
                    	if(nyiteration>0)
                    	{
                    		autoturn=yturn/nyiteration;
                    	}
                    	System.out.println("Y released: "+nyiteration+" "+autoturn+" "+yturn);
                    	nyiteration=0;
                    	yturn=0;
                    }
                    prepressing=pressing;
                    preypressing=ypressing;
                    preangle=angle;
	                Thread.sleep(10,0);
                }
        	}
        }
        
//		for(int i=0;i<360;++i)
//		{
//			double angle=((double)i*Math.PI)/180;
//			r.mousePress(InputEvent.BUTTON1_MASK);
//			Thread.sleep(3, 0);
//		}
	}

	private static double toCircle(double d) {
		while(d<-Math.PI)
		{
			d+=Math.PI*2;
		}
		while(d>Math.PI)
		{
			d-=Math.PI*2;
		}
		return d;
	}
}
