/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Znoque
 */
public class ManipulandoDataHora {
    private Date data = new Date();
    private String dia = "";

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getDataFormatada(){
        SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formatar.format(getData());
        return dataFormatada;
    }
                
    public String getDiaSemana(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getData());
        int day = cal.get(Calendar.DAY_OF_WEEK);
        if(day==1){
            setDia("Domingo");
        }else if(day==2){
            setDia("Segunda");
        }else if(day==3){
            setDia("Terça");
        }else if(day==4){
            setDia("Quarta");
        }else if(day==5){
            setDia("Quinta");
        }else if(day==6){
            setDia("Sexta");
        }else if(day==7){
            setDia("Sábado");
        }
        return getDia();
    }
    
    public String getHoraFormatada(){
        Date hora = new Date();
        SimpleDateFormat formatarHora = new SimpleDateFormat("HH:mm");
        String horaFormatada = formatarHora.format(hora);
        return horaFormatada;
    }
}
