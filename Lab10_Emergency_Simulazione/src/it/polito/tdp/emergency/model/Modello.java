package it.polito.tdp.emergency.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.polito.tdp.emergency.db.FieldHospitalDAO;
import it.polito.tdp.emergency.simulation.Evento;
import it.polito.tdp.emergency.simulation.Paziente;
import it.polito.tdp.emergency.simulation.Evento.TipoEvento;


public class Modello {
	
	
	
	Map<Integer,Dottore> dottori = new HashMap<Integer,Dottore>();
	Map<Integer,Paziente> pazienti = new HashMap<Integer,Paziente>();
	Queue<Evento> listaEventi = new PriorityQueue<Evento>();
	TipoEvento tipoEvento;
	
	

	public boolean controlloInputNome(String text) {
		
			 Pattern p = Pattern.compile("^[a-z,A-Z]+$");
			 Matcher m = p.matcher(text);
			 boolean b = m.matches();
			 return b;
		}

	public boolean controlloInputOre(String text) {
		Pattern p = Pattern.compile("^\\d+$");
		 Matcher m = p.matcher(text);
		 boolean b = m.matches();
		 return b;
	}

	public String inserisciDottore(String text, String text2) {
		String result="";
		int ore=Integer.parseInt(text2);
		Dottore doc = new Dottore(text,ore);
		int contatore=doc.getContaDottori();
		contatore++;
		doc.setContaDottori(contatore);
		doc.setIdDottore(contatore);
		dottori.put(contatore, doc);
		
		Simula s = new Simula();
		s.setDottori(dottori);
		result=result+"inserito il dottore :  " + text + "\n";
		return result;
	}

	public void creaTabellaPazienti(int ore) {
		FieldHospitalDAO dao = new FieldHospitalDAO();
		pazienti=dao.creaTabellaPazienti();
		
		Simula s = new Simula();
		s.setPazienti(pazienti);
		this.listaEventi=dao.creaPrimaListaEventi();
		//aggiungo i dottori nella coda
		dottori=s.getDottori();
		//System.out.println(dottori.values().toString());
		long primo=listaEventi.peek().getTempo()+ore*3600*1000;
				;
		
		for(Dottore doc : dottori.values()){
			
			this.listaEventi.add(new Evento(primo,TipoEvento.DOCTOR_INIZIA_TURNO,doc.getIdDottore()));
			}
	
		s.setListaEventi(listaEventi);
		/*while(!listaEventi.isEmpty()){
			Evento bb=listaEventi.remove();
		System.out.println(bb.toString()+"\n");}*/
	
	}

	public boolean controlloPresenzaDottore() {
		if(this.dottori.size()>0)return true;
		return false;
	}
	}
	
	


