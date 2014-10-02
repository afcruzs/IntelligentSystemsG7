package unalcol.agents.examples.labyrinth.multeseo;



import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.SimpleLanguage;
import unalcol.types.collection.vector.*;
import unalcol.agents.Action;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: Universidad Nacional de Colombia</p>
 *
 * @author Andrés Donato
 * @author Fabian Rodriguez
 * @version 1.0
 */
public class SimpleTeseoAgentProgramPerceptron  implements AgentProgram{
  protected SimpleLanguage language;
    protected Vector<String> cmd = new Vector<>();
    protected  Box actually;// Box actually agent
    protected  Box temp;//
    static int xPosition=0;// position x in the map
    static int yPosition=0;// position y in the map
    static int brujulaAgent=0;//denote the direction
    static int relevantNodes=0;
    public static int mov;
    Random r=new Random();
    
     SparceMatrix sparceMatrix;

    
    public void setLanguage(  SimpleLanguage _language ){
    language = _language;
  }

    @Override
  public void init(){
    cmd.clear();
  }
    
    @Override
    public Action compute(Percept p){
    if( cmd.size() == 0 ){
       
      boolean PF = ( (Boolean) p.getAttribute(language.getPercept(0))).
          booleanValue();
      boolean PD = ( (Boolean) p.getAttribute(language.getPercept(1))).
          booleanValue();
      boolean PA = ( (Boolean) p.getAttribute(language.getPercept(2))).
          booleanValue();
      boolean PI = ( (Boolean) p.getAttribute(language.getPercept(3))).
          booleanValue();
      boolean MT = ( (Boolean) p.getAttribute(language.getPercept(4))).
          booleanValue();
      boolean AF =( (Boolean) p.getAttribute(language.getPercept(5))).
          booleanValue();
      boolean AD =( (Boolean) p.getAttribute(language.getPercept(6))).
          booleanValue();
      boolean AA =( (Boolean) p.getAttribute(language.getPercept(7))).
          booleanValue();
      boolean AI =( (Boolean) p.getAttribute(language.getPercept(8))).
          booleanValue();
      int d = accion(PF, PD, PA, PI, MT,AF, AD, AA, AI);
      
      
      if(d==6){
          cmd.add(language.getAction(1));
      }
      if(d==8){
          cmd.add(language.getAction(0));
      }
      else if (0 <= d && d < 4) {
        for (int i = 1; i <= d; i++) {
          cmd.add(language.getAction(3)); //rotate
        }
        cmd.add(language.getAction(2)); // advance
      }
      else{
        cmd.add(language.getAction(0)); // die
      }
    }
    String x = cmd.get(0);
    cmd.remove(0);
    return new Action(x);
  }

  
   public void addToCmd(int d){
      
        if(d==6){
          cmd.add(language.getAction(1));
      }
      
      else if (0 <= d && d < 4) {
        for (int i = 1; i <= d; i++) {
          cmd.add(language.getAction(3)); //rotate
        }
        cmd.add(language.getAction(2)); // advance
      }
      else{
        cmd.add(language.getAction(0)); // die
      }
   }

  
    
    
    public static class SparceMatrix{
        
        private TreeMap<Integer,TreeMap<Integer, Box>> row;
        private TreeMap<Integer,TreeMap<Integer, Box>> col;
        
        public SparceMatrix(){
           row=new TreeMap<>();
           col=new TreeMap<>();
        }
        public void addNode(int x,int y, Box box){
            if(!row.containsKey(x))
                row.put(x, new TreeMap<Integer, Box>());
            if(!col.containsKey(y)){
                col.put(y, new TreeMap<Integer, Box>());
            }
            row.get(x).put(y, box);
            col.get(y).put(x, box);
        }
        
        public void removeNode(int x,int y){
            if(row.get(x).get(y)!=null && row.get(x).get(y).interesting==false){
                row.get(x).remove(y);
                col.get(y).remove(x);
            }
        }
        
        public  Box existNode(int x,int y){
            if(row.containsKey(x)){
                return row.get(x).get(y);
            }
            if(col.containsKey(y)){
                return col.get(y).get(x);
            }
            return null;
        }
        
        public int countNode(){
            int counter=0;
            Set<Integer> set=row.keySet();
            Iterator<Integer> it=set.iterator();
            while(it.hasNext()){
                counter +=row.get(it.next()).size();
            }   
            return counter;
        }
        public String printMatrix(){
            String cad="";
            Set<Integer> set=col.keySet();
            Iterator<Integer> it=set.iterator();
            while(it.hasNext()){
                int k=it.next();
                Set<Integer> set2=col.get(k).keySet();
                 
                Iterator<Integer> it2=set2.iterator();
                while(it2.hasNext()){
                    cad+="("+k+","+it2.next()+") ";
                }
                if(set2.size()>0)
                    cad+="\n";
            } 
            return cad;
        }
    }
    
    

    
    public SimpleTeseoAgentProgramPerceptron(){
        
        this.setLanguage(new SimpleLanguage(new String[]{"front", "right", "back", "left", "exit",
        "afront", "aright", "aback", "aleft"},
                                   new String[]{"no_op", "die", "advance", "rotate"}
                                   ));
  
                                  
        sparceMatrix = new  SparceMatrix();
        actually=new  Box(null,0); 
        
        actually.setNextEdge(new  Edge());
        sparceMatrix.addNode(0, 0, actually);
        
    }
    public boolean isThereAnAgent(boolean AF, boolean AD, boolean AA, boolean AI ){
            if(AF ||  AD || AA || AI) return true;
            return false;
        }
    public static class Edge{
        private Stack<Integer> pila;
        
        public Edge(){
            pila=new Stack<Integer>();
        }
        public void setPila(Stack<Integer> pila){
            this.pila=pila;
        }
        public void addAction(int x){
            pila.add(x);
        }
        
        public boolean isEmpty(){
            return pila.isEmpty();
        }
        public String toString(){
            String cad="";
            Iterator <Integer> it = pila.iterator();
            while (it.hasNext()) {
                cad+=it.next()+",";
            }
            return cad;
        }
        
    }
    public static class Box{
        // 
        int brujula;// Denotes the direction in which the agent is seeing 
        boolean PF,PD,PA,PI;//perceptions of the agent
        Box previous;//The box where the agent come from 
        boolean visited;//if the box has been viseted previously
        Edge nextEdge;
        boolean interesting;
        byte sum=0;
        int xP,yP=0;
        //constructor
        public Box( Box previous,int brujula){//constructor init
            sum+=PF?0:1;
            sum+=PD?0:1;
            sum+=PI?0:1;
            sum+=PA?0:1;
            this.previous=previous;
            this.brujula=brujula;
            visited=false;
            interesting=false;
        }

        public int getxP() {
            return xP;
        }

        public int getyP() {
            return yP;
        }
        
        
        
        public void setxP(int xP) {
            this.xP = xP;
        }

        public void setyP(int yP) {
            this.yP = yP;
        }
        
        
        
        public boolean isInteresting(){
            byte falseCounter =0;
            if(!PF)
                falseCounter++;
            if(!PD)
                falseCounter++;
            if(!PI)
                falseCounter++;
            if(!PA)
                falseCounter++;
            if(this.getPrevious()!=null)
                return (falseCounter>1)?true:false;
            else
                return true; 
        }
        
        public void setNextEdge( Edge e){
            this.nextEdge=e;
        }
        public void rotatePerceptions(boolean PF, boolean PD, boolean PA, boolean PI){            
            switch(brujulaAgent){

                case 1:boolean aux;aux =PI;
                    this.PI=PA;//Left
                    this.PA=PD;//Back
                    this.PD=PF;//Right
                    this.PF=aux;//Front
                    break;
                case 2:

                    boolean aux2;
                    aux = PF;
                    aux2 = PD;
                    this.PF=PA;//Front
                    this.PD=PI;//Right
                    this.PI=aux2;//Left
                    this.PA=aux;//Back
                    break;
                case 3:

                    aux =PI;
                    this.PI=PF;//Left
                    this.PF=PD;//Front
                    this.PD=PA;//Right
                    this.PA=aux;//Back
                    break;
                default:
                    break;

            }
        
    }
        
        //just set the perceptions
        public void setPerceptions(boolean PF, boolean PD, boolean PA, boolean PI){//change the perceptions for the Box
           
            this.PF=PF;//Front
            this.PD=PD;//Right
            this.PA=PA;//Back
            this.PI=PI;//Left
        }
        //setters and getters
        public  Box getPrevious(){
            return previous;
        }
        
        public int getBrujula(){
            return brujula;
        }
        
        public void setVisited(boolean v){
            visited=v;
        }
        
        public void setBrujula(int brujula){
            this.brujula=brujula;
        }
        
        public void closePath(){
            sum--;
        }
    }
    
 
    public int calcuateNeededRotations (int movimientoDeseado){
        if(brujulaAgent<=movimientoDeseado){
            return movimientoDeseado - brujulaAgent;
        }
        else{
           return  4 - brujulaAgent + movimientoDeseado;
        }
    }
    
    public void evaluatePath(){
            
    }
    
    public int solveX(int x){
        if(x==1)return -1;
        if(x==3)return 1;
        return 0;
    }
    
    public int solveY(int x){
        if(x==2)return 1;
        if(x==0)return -1;
        return 0;
    }
    public void deleteNodes(Stack<Integer> stack,int t){
        try {
            
        if(stack!=null)
            if(stack.size()>1){
                Stack <Integer>pile =(Stack <Integer>) stack.clone();                             
                int x=xPosition;
                int y=yPosition;
                //mov=pile.pop();
                //x+=solveX(mov);
                //y+=solveY(mov);                
                while(pile.size()>t){
                    mov=pile.pop();
                    x+=solveX(mov);
                    y+=solveY(mov);   
                    
                    sparceMatrix.removeNode(x, y);
                }
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "muere");
        }
    }
    public void mark(Box actually){
        if(actually.brujula==0)actually.PA=true;
        if(actually.brujula==1)actually.PI=true;
        if(actually.brujula==2)actually.PF=true;
        if(actually.brujula==3)actually.PD=true;
    }
    
    public void rotatePerceptions(boolean PF, boolean PD, boolean PA, boolean PI,boolean arr[]){            
            switch(brujulaAgent){

                case 1:boolean aux;aux =PI;
                    arr[3]=PA;//Left
                    arr[2]=PD;//Back
                    arr[1]=PF;//Right
                    arr[0]=aux;//Front
                    break;
                case 2:

                    boolean aux2;
                    aux = PF;
                    aux2 = PD;
                    arr[0]=PA;//Front
                    arr[1]=PI;//Right
                    arr[2]=aux2;//Left
                    arr[3]=aux;//Back
                    break;
                case 3:

                    aux =PI;
                    arr[3]=PF;//Left
                    arr[0]=PD;//Front
                    arr[1]=PA;//Right
                    arr[2]=aux;//Back
                    break;
                default:
                    break;

            }
        
    }
    public int changeMov(boolean AF,boolean AD,boolean AA, boolean AI){
        int i=0;
        boolean arr[]=new boolean[4];
        if(actually.PD==false && sparceMatrix.existNode(xPosition+1, yPosition)==null && !AD)arr[0]=true;
        if(actually.PF==false && sparceMatrix.existNode(xPosition, yPosition+1)==null && !AF)arr[1]=true;
        if(actually.PA==false && sparceMatrix.existNode(xPosition, yPosition-1)==null && !AA)arr[2]=true;
        if(actually.PI==false && sparceMatrix.existNode(xPosition-1, yPosition)==null && !AI)arr[3]=true;

        if(!arr[0] && !arr[1] && !arr[2] && !arr[3])
            return 4;
        do{
            i=r.nextInt(4);
        }while(!arr[i]);
        
        return i;
    }
    
    
    public static boolean agents[]= new boolean[4];
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean AF, boolean AD, boolean AA, boolean AI ) {
        //JOptionPane.showMessageDialog(null, AF+" "+AD+" "+AA+" "+AI);
        actually.setxP(xPosition);
        actually.setyP(yPosition);
         
        rotatePerceptions(AF, AD, AA, AI, agents);
        AF=agents[0];
        AD=agents[1];
        AA=agents[2];
        AI=agents[3];
        if(actually.getPrevious()!=null){
            //JOptionPane.showMessageDialog(null,actually.getPrevious().nextEdge+"\n" + sparceMatrix.printMatrix());
            //JOptionPane.showMessageDialog(null, sparceMatrix.countNode());
            
        }
        //JOptionPane.showMessageDialog(null, xPosition+ " "+yPosition);
            //If the agent is already in the exit of the labyrinth
        if(MT){
            //JOptionPane.showMessageDialog(null,actually.getPrevious().nextEdge+"\n" + sparceMatrix.printMatrix());
            return 6;
        }
        //If the box doesn't have been visited previously, the agent set the box perceptions  
        if(actually.visited==false){
            
            actually.setNextEdge(new  Edge());
            actually.setPerceptions(PF,PD,PA,PI);//get perceptions for actually cell
            actually.rotatePerceptions(PF, PD, PA, PI);
            if (actually.getPrevious()!=null)
                mark(actually);
        }
            int change=changeMov(AF, AD, AA, AI);
            //JOptionPane.showMessageDialog(null,actually.PF +" "+ actually.PD + " "+actually.PA +" "+ actually.PI);
            //If front free and the box doesn't exist then, advance the front
           if(change==0 ){
                
                int mov=calcuateNeededRotations(1);
                brujulaAgent=1;
                deleteNodes(actually.getPrevious()!=null?actually.getPrevious().nextEdge.pila:null,1);
                if(!actually.isInteresting()){
                    actually.PD=true;          
                    actually.getPrevious().nextEdge.addAction(brujulaAgent);
                    temp=new  Box( actually.getPrevious(), brujulaAgent);
                    
                }
                else{
                    actually.PD=true;
                    actually.interesting=true;
                    actually.nextEdge.addAction(brujulaAgent);
                    temp=new  Box( actually, brujulaAgent);
                }
                
                sparceMatrix.addNode(++xPosition,yPosition,temp);
                actually.setVisited(true);               
                
                actually=temp;
                //JOptionPane.showMessageDialog(null,actually.PF +" "+ actually.PD + " "+actually.PA +" "+ actually.PI);
                return mov;
            }else if(change==1){
               
                 //marked perception front as true 
                
                int mov=calcuateNeededRotations(0);                
                brujulaAgent=0;            
                deleteNodes(actually.getPrevious()!=null?actually.getPrevious().nextEdge.pila:null,1);
                if(!actually.isInteresting()){
                    actually.PF=true;
                    
                    actually.getPrevious().nextEdge.addAction(brujulaAgent);
                    temp=new  Box( actually.getPrevious(), brujulaAgent);
                }
                else{
                    actually.PF=true;
                    actually.interesting=true;
                    actually.nextEdge.addAction(brujulaAgent);
                    temp=new  Box( actually, brujulaAgent);
                }                 
                
                sparceMatrix.addNode(xPosition,++yPosition,temp);                
                actually.setVisited(true);    
                actually=temp;  
                //JOptionPane.showMessageDialog(null,actually.PF +" "+ actually.PD + " "+actually.PA +" "+ actually.PI);
                return  mov;
                
            }else if(change==2){
                 
                 int mov=calcuateNeededRotations(2);
                 brujulaAgent=2;
                 deleteNodes(actually.getPrevious()!=null?actually.getPrevious().nextEdge.pila:null,1);
                 if(!actually.isInteresting()){
                     actually.PA=true;
                    actually.getPrevious().nextEdge.addAction(brujulaAgent);
                    temp=new  Box( actually.getPrevious(), brujulaAgent);
                }
                else{
                     actually.PA=true;
                     actually.interesting=true;
                    actually.nextEdge.addAction(brujulaAgent);
                    temp=new  Box( actually, brujulaAgent);
                }
                 sparceMatrix.addNode(xPosition,--yPosition,temp);
                 
                 actually.setVisited(true);
                 
                 actually=temp;  
                 return mov;             
            }else if(change==3){
                 
                 int mov=calcuateNeededRotations(3);
                 brujulaAgent=3;
                 deleteNodes(actually.getPrevious()!=null?actually.getPrevious().nextEdge.pila:null,1);
                 if(!actually.isInteresting()){
                     actually.PI=true;
                    actually.getPrevious().nextEdge.addAction(brujulaAgent);
                    temp=new  Box( actually.getPrevious(), brujulaAgent);
                }
                else{
                     actually.PI=true;
                     actually.interesting=true;
                    actually.nextEdge.addAction(brujulaAgent);
                    temp=new  Box( actually, brujulaAgent);
                }
                 
                 sparceMatrix.addNode(--xPosition,yPosition,temp);
                 
                 actually.setVisited(true);
                 
                 actually=temp;          
                 return mov; 
            }else{// path record
                //JOptionPane.showMessageDialog(null, isThereAnAgent(AF, AD, AA, AI));
                if(isThereAnAgent(AF, AD, AA, AI))
                {
                   // JOptionPane.showMessageDialog(null, "Not supported yet");
                    return 8;
                }
                else{
                sparceMatrix.removeNode(xPosition, yPosition);
                deleteNodes(actually.getPrevious()!=null?actually.getPrevious().nextEdge.pila:null,0);
                return pathRecord();
                }
            }
           
    }
    
    
    public int pathRecord(){

    if(actually.getPrevious()==null){
                    
                    return 6;
                }else{
                   if(actually.getxP()-actually.getPrevious().getxP()==-1 && actually.getyP()==actually.getPrevious().getyP() && !actually.PD){
                    //JOptionPane.showMessageDialog(null, "me quiero devolver a la derecha");
                    int mov=calcuateNeededRotations(1) ;
                        brujulaAgent=((mov+brujulaAgent)%4);
                            xPosition++;
                        
                        actually.getPrevious().nextEdge.pila= new Stack<>();
                        actually= actually.getPrevious();
                        actually.PI=true;
                                
                    return mov;

                   }
                   if(actually.getxP()-actually.getPrevious().getxP()==1 && actually.getyP()==actually.getPrevious().getyP() && !actually.PI){
                       //JOptionPane.showMessageDialog(null, "me quiero devolver a la izquierda");
                       int mov=calcuateNeededRotations(3) ;
                        brujulaAgent=((mov+brujulaAgent)%4);
                            xPosition--;
                        
                        actually.getPrevious().nextEdge.pila= new Stack<>();
                        actually= actually.getPrevious();
                        actually.PD=true;
                    return mov;
                   }
                   if(actually.getyP()-actually.getPrevious().getyP()==-1 && actually.getxP()==actually.getPrevious().getxP() && !actually.PF){
                       //JOptionPane.showMessageDialog(null, "me quiero devolver arriba");
                       int mov=calcuateNeededRotations(0) ;
                        brujulaAgent=((mov+brujulaAgent)%4);
                        
                            yPosition++;
                        
                       
                        actually.getPrevious().nextEdge.pila= new Stack<>();
                        actually= actually.getPrevious();
                        actually.PA=true;
                    return mov;
                   }
                   if(actually.getyP()-actually.getPrevious().getyP()==1 && actually.getxP()==actually.getPrevious().getxP() && !actually.PA){
                       //JOptionPane.showMessageDialog(null, "me quiero devolver abajo");
                       int mov=calcuateNeededRotations(2) ;
                        brujulaAgent=((mov+brujulaAgent)%4);
                            yPosition--;
                        
                        actually.getPrevious().nextEdge.pila= new Stack<>();
                        actually= actually.getPrevious();
                        actually.PF=true;
                    return mov;
                   }
                   
                   if(!actually.PF && customDijkstra(xPosition, yPosition+1, actually.getPrevious())){
                       addToCmd(calcuateNeededRotations(0));
                       brujulaAgent=((calcuateNeededRotations(0)+brujulaAgent)%4);
                       change();
                       return pathDijkstra(xPosition, yPosition,actually.getPrevious());
                   }
                   if(!actually.PD && customDijkstra(xPosition+1, yPosition, actually.getPrevious())){
                       addToCmd(calcuateNeededRotations(1));
                       brujulaAgent=((calcuateNeededRotations(1)+brujulaAgent)%4);
                       change();
                       return pathDijkstra(xPosition, yPosition,actually.getPrevious());
                   }
                   if(!actually.PA && customDijkstra(xPosition, yPosition-1, actually.getPrevious())){
                       addToCmd(calcuateNeededRotations(2));
                       brujulaAgent=((calcuateNeededRotations(2)+brujulaAgent)%4);
                       change();
                       return pathDijkstra(xPosition, yPosition,actually.getPrevious());
                   }
                   if(!actually.PI && customDijkstra(xPosition-1, yPosition, actually.getPrevious())){
                       addToCmd(calcuateNeededRotations(3));
                       brujulaAgent=((calcuateNeededRotations(3)+brujulaAgent)%4);
                       change();
                       return pathDijkstra(xPosition, yPosition,actually.getPrevious());
                   }
                   
                   
                   
                   
                   
                    while(actually.getPrevious().nextEdge.pila.size()>1){
                        
                        int mov=calcuateNeededRotations((returnPath(actually.getPrevious().nextEdge.pila.pop()))) ;
                        addToCmd(mov);
                        brujulaAgent=((mov+brujulaAgent)%4);
                        if(brujulaAgent==0){
                            yPosition++;
                        }else if(brujulaAgent==1){
                            xPosition++;
                        }else if(brujulaAgent==2){
                            yPosition--;
                        }else{
                            xPosition--;
                        }
                    }
                    int mov =calcuateNeededRotations((returnPath(actually.getPrevious().nextEdge.pila.pop()))) ;
                    brujulaAgent=((mov+brujulaAgent)%4);
                    if(brujulaAgent==0){
                        yPosition++;
                    }else if(brujulaAgent==1){
                        xPosition++;
                    }else if(brujulaAgent==2){
                        yPosition--;
                    }else{
                        xPosition--;
                    }
                    if(actually.getPrevious().nextEdge.pila.empty()){
                        actually=actually.getPrevious();
                       
                    }
                    return mov;
                }
    }
   public int returnPath(int brujula){
       if(brujula==0){
           return 2;
       }else if(brujula==1)
           return 3;
       else if(brujula==2){
           return 0;
       }
       else{
           return 1;
       }
   }
    public boolean goalAchieved( Percept p ){
    return (((Boolean)p.getAttribute(language.getPercept(4))).booleanValue());
  }
   
    public boolean customDijkstra(int x, int y, Box previous){
    int normalPath=previous.nextEdge.pila.size();
    Box adj = sparceMatrix.col.get(y).get(x);
    int counter=1;
    while(counter<normalPath && previous!=adj && previous.getPrevious()!=null){
        counter+=previous.getPrevious().nextEdge.pila.size();
        previous=previous.getPrevious();
    }
    if(previous==adj)
    return true;
    
    return false;
    }
    public int pathDijkstra(int x,int y,Box previous){
        Box adj = sparceMatrix.col.get(y).get(x);
        String cad="";
        Stack <Integer>movs=new Stack<Integer>();
        //JOptionPane.showMessageDialog(null, x+" "+y);
        while(previous!=adj){
            Stack <Integer>pile =(Stack <Integer>) previous.getPrevious().nextEdge.pila.clone(); 
            while(pile.size()>0){
                movs.add(pile.pop());
            }
            previous=previous.getPrevious();
        }
        
        while(!movs.isEmpty()){
            cad+=movs.peek()+" ";
            int mov=calcuateNeededRotations(movs.pop());
            addToCmd(mov);
            brujulaAgent=((mov+brujulaAgent)%4);
            change();
        }
        
        
        //JOptionPane.showMessageDialog(null, cad);
      //  actually.getPrevious().nextEdge.pila.clear();
        actually=actually.getPrevious();
        return 8;
    }
   
    public void change(){
        if(brujulaAgent==0){
                            yPosition++;
                        }else if(brujulaAgent==1){
                            xPosition++;
                        }else if(brujulaAgent==2){
                            yPosition--;
                        }else{
                            xPosition--;
                        }
    }
}
