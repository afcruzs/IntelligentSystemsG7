package unalcol.agents.examples.labyrinth.multeseo.cai;

import java.util.ArrayList;
import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;

public class TeseoCai extends SimpleTeseoAgentProgram {

    ArrayList<String> coor = new ArrayList<>();
    ArrayList<Integer> ac = new ArrayList<>();
    ArrayList<Integer> giros = new ArrayList<>();
    int giro = 0;

    public TeseoCai() {
        coor.add("0,0");
        giros.add(0);
        
    }

    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT) {
        //retorna la Posición del vector que se ha repetido el movimiento
        int pos_rep = memoria();
        if (pos_rep == -1) {
            if (MT) {
                return 4;
            }
            if (!PD) {
                actualizar(1);
                return 1;
            }
            if (!PF) {
                actualizar(0);
                return 0;
            }
            if (!PI) {
                actualizar(3);
                return 3;
            }

            actualizar(2);
            return 2;

        } else {

            int aca = ac.get(pos_rep);
            switch (aca) {
                case 0:
                    if (MT) {
                        return 4;
                    }
                    if (!PD) {
                        actualizar(1);
                        return 1;
                    }
                    if (!PI) {
                        actualizar(3);
                        return 3;
                    }
                    if (!PA) {
                        actualizar(2);
                        return 2;
                    }
                    if (!PF) {
                        actualizar(0);
                        return 0;
                    }
                    break;

                case 1:
                    if (MT) {
                        return 4;
                    }
                    if (!PF) {
                        actualizar(0);
                        return 0;
                    }
                    if (!PI) {
                        actualizar(3);
                        return 3;
                    }
                    if (!PA) {
                        actualizar(2);
                        return 2;
                    }
                    if (!PD) {
                        actualizar(1);
                        return 1;
                    }
                    break;

                case 2:
                    if (MT) {
                        return 4;
                    }
                    if (!PD) {
                        actualizar(1);
                        return 1;
                    }
                    if (!PF) {
                        actualizar(0);
                        return 0;
                    }
                    if (!PI) {
                        actualizar(3);
                        return 3;
                    }
                    if (!PA) {
                        actualizar(2);
                        return 2;
                    }
                    break;

                case 3:
                    if (MT) {
                        return 4;
                    }
                    if (!PD) {
                        actualizar(1);
                        return 1;
                    }
                    if (!PF) {
                        actualizar(0);
                        return 0;
                    }
                    if (!PA) {
                        actualizar(2);
                        return 2;
                    }
                    if (!PI) {
                        actualizar(3);
                        return 3;
                    }
                    break;
            }
        }

        return -2;



    }

    public int memoria() {
        
        //for (int i = 0; i < coor.size() - 1; i++) {
        for (int i = coor.size()-2; i >= 0 ; i--) {
            if ((coor.get(i).equals(coor.get(coor.size()-1)))  && (giros.get(i) == giro)) {
                return i;
            }
        }
        return -1;
    }

    public void actualizar(int decision) {
        giro = (giro + decision) % 4;
        giros.add(giro);
        ac.add(decision);
        String coordenada = coor.get(coor.size()-1);
        String[] xy =coordenada.split(",");
        int coor_act_x = Integer.parseInt(xy[0]);
        int coor_act_y = Integer.parseInt(xy[1]);
        if (giro == 0) {
            coor_act_y++;
        } else if (giro == 1) {
            coor_act_x++;
        } else if (giro == 2) {
            coor_act_y--;
        } else if (giro == 3) {
            coor_act_x--;
        }
         coordenada = Integer.toString(coor_act_x).concat(",").concat(Integer.toString(coor_act_y));
         //for(int j = 0 ;;j++)
         coor.add(coordenada);
         //ac.add(decision);

    }
}
