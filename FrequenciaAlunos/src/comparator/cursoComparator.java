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
public class cursoComparator implements Comparator<Aluno>{
    
    @Override
    public int compare(Aluno a1, Aluno a2) {
        if (a1.getCurso().get().equals(a2.getCurso().get())) {
            return 0;
        }
        if (a1.getCurso().get().compareTo(a2.getCurso().get()) > 0) {
            return 1;
        }
        return -1;
    }
    
}
