/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import java.util.Comparator;
import model.Aluno;

/**
 *
 * @author Znoque
 */
public class turnoComparator implements Comparator<Aluno>{
    
    @Override
    public int compare(Aluno a1, Aluno a2) {
        if (a1.getTurno().get().equals(a2.getTurno().get())) {
            return 0;
        }
        if (a1.getTurno().get().compareTo(a2.getTurno().get()) > 0) {
            return 1;
        }
        return -1;
    }
    
}
