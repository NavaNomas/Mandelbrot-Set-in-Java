
package nav;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author Nava
 */
public class Nav extends JComponent {
    private final int ancho = 1000;
    private final int alto = 700;
    int escala = 250;
    int horizontal = 0;
    int vertical = 0;
    private BufferedImage buffer;
    private final int iteraciones = 10000;

    public Nav(){
        buffer = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
    
        render();
    
        repaint();
    
        JFrame frame = new JFrame("NAAAA");
        frame.setSize(ancho,alto);
        frame.setDefaultCloseOperation(3);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.getContentPane().add(this);
        this.addMouseWheelListener(new MouseWheelListener(){
        @Override
       ///this part is garbage so don't use it
                public void mouseWheelMoved(MouseWheelEvent e) {
                    if (e.getWheelRotation()>0) {
                        escala = escala - 80;
                        render();
                        repaint();
                    }
                    else{
                        escala = escala + 80;
                        render();
                        repaint();
                    }
                }
         });
        this.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("**"+e.getXOnScreen());
                System.out.println("++"+MouseInfo.getPointerInfo().getLocation().x);
                if (MouseInfo.getPointerInfo().getLocation().x > e.getX()) {
                
                }
                if (MouseInfo.getPointerInfo().getLocation().x < e.getX()) {
                
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
         });
     /////////////////////////////

    }
    public float[] set (float x, float y){
        float[] res = new float[3];
        res[0] = x*x - y*y; //real component of the number
        res[1] = 2*x*y; //Imaginary component of the number
        res[2] = 2; //Escape condition
         return res;
    }
    public void render(){
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                buffer.setRGB(i, j, calcularpunto((i-(ancho+horizontal)/2f)/escala , (j-(alto+vertical)/2f)/escala));
               
            }
        }
    
    }
    //calculating if the point is in the set
    public int calcularpunto(float x, float y){
        float real = x;
        float img  = y;
        int i = 0;
       
            while((i < iteraciones) && ((x*x) + (y*y) <= set(x,y)[2])){
                float nx = set(x,y)[0] + real;
                float ny = set(x,y)[1]+ img;
                x = nx;
                y = ny;
                 i++;
            }
        if (i == iteraciones) {
            return 0x00000000;
        }
        return Color.HSBtoRGB((float) i/iteraciones + 0.1f,0.5f,4);
    }
    //painting the buffer
    @Override
    public void paint (Graphics g){
        g.drawImage(buffer,0,0,null);
    }
   
    public static void main(String[] args) {
       new Nav();
    }

  
    
}
