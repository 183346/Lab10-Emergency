package it.polito.tdp.emergency.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import it.polito.tdp.emergency.simulation.Evento;
import it.polito.tdp.emergency.simulation.Paziente;

public class Simula {
	
	static Map<Integer, Paziente> pazienti = new HashMap<Integer, Paziente>();
	static Queue<Evento> listaEventi = new PriorityQueue<Evento>();
	static Map<Integer,Dottore> dottori = new HashMap<Integer,Dottore>();
	Queue<Paziente> pazientiInAttesa = new PriorityQueue<Paziente>();
	Queue<Dottore> listaDottori = new PriorityQueue<Dottore>();
	String result="";
	int pazientiGuariti=0;
	int pazientiMorti=0;

	

	public Map<Integer, Paziente> getPazienti() {
		return pazienti;
	}

	public void setPazienti(Map<Integer, Paziente> pazienti) {
		this.pazienti = pazienti;
	}

	public Queue<Evento> getListaEventi() {
		return listaEventi;
	}

	public void setListaEventi(Queue<Evento> listaEventi) {
		this.listaEventi = listaEventi;
	}

	public Map<Integer, Dottore> getDottori() {
		return dottori;
	}

	public void setDottori(Map<Integer, Dottore> dottori) {
		this.dottori = dottori;
	}

	public String simula() {
		 		
			while (!listaEventi.isEmpty()) {
			passo();
		}
			result=result +"pazienti guariti:   "+this.pazientiGuariti+"\n";
			result=result +"pazienti morti:   "+this.pazientiMorti+"\n";
		return this.result;}

	private void passo() {
		
		
		Evento e = listaEventi.remove();
		switch (e.getTipo()) {
		case DOCTOR_FINE_TURNO:
			this.listaDottori.remove(dottori.get(pazienti.get(e.getDato()).getCodiceDottore()));
			if(this.pazientiInAttesa.size()!=0){
			listaEventi.add(new Evento(e.getTempo() + 16 *3600*1000, Evento.TipoEvento.DOCTOR_INIZIA_TURNO, e.getDato()));}
			dottori.get(e.getDato()).setDisponibile(false);
			
			break;
		case DOCTOR_INIZIA_TURNO:
			
			this.listaDottori.add(dottori.get(e.getDato()));
			dottori.get(e.getDato()).setDisponibile(true);
			result=result+"dottore inizia turno:" + dottori.get(e.getDato()).getNomeDottore()+"\n";
			listaEventi.add(new Evento(e.getTempo() + 8 *3600*1000, Evento.TipoEvento.DOCTOR_FINE_TURNO, e.getDato()));
			break;
		case PAZIENTE_ARRIVA:
		    
			pazientiInAttesa.add(pazienti.get(e.getDato()));
			switch (pazienti.get(e.getDato()).getStato()) {
			case Green:
				//muore dopo 12 ore
				listaEventi.add(new Evento(e.getTempo() + 12 *3600*1000, Evento.TipoEvento.PAZIENTE_MUORE, e.getDato()));
			case Red:
				//muore dopo 1 ora
				listaEventi.add(new Evento(e.getTempo() + 1 *3600*1000, Evento.TipoEvento.PAZIENTE_MUORE, e.getDato()));
				break;
			case White:
				//dimetto il paziente lo imposto come salvo
				result=result+"Paziente bianco dimesso è salvo:" + pazienti.get(e.getDato()).getNome()+"\n";
				pazienti.get(e.getDato()).setStato(Paziente.StatoPaziente.SALVO);
				
				break;
			case Yellow:
				//muore dopo 6 ore
				listaEventi.add(new Evento(e.getTempo() + 6 *3600*1000, Evento.TipoEvento.PAZIENTE_MUORE, e.getDato()));
				break;
			case IN_CURA:
				
				break;
			case NERO:
				this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
				break;
			case SALVO:
				this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
				break;
				
			default:
				System.err.println("Panik!");
				break;
			
			}
			break;
		case PAZIENTE_GUARISCE:
			
			switch (pazienti.get(e.getDato()).getStato()) {
			case Green:
				break;
			case IN_CURA:
				pazienti.get(e.getDato()).setStato(Paziente.StatoPaziente.SALVO);
				this.pazientiGuariti++;
				this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
				this.listaDottori.add(dottori.get(pazienti.get(e.getDato()).getCodiceDottore()));
				result=result+"Paziente guarito" + pazienti.get(e.getDato()).getNome()+"\n";
				break;
			case NERO:
				this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
				break;
			case Red:
				break;
			case SALVO:
				this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
				break;
			case White:
				this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
				break;
			case Yellow:
				break;
			default:
				break;
			
			}
	case PAZIENTE_MUORE:
	
		switch (pazienti.get(e.getDato()).getStato()) {
		case Green:
			result=result+"Paziente morto" + pazienti.get(e.getDato()).getNome()+"\n";
			pazienti.get(e.getDato()).setStato(Paziente.StatoPaziente.NERO);
			this.pazientiMorti++;
			this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
			break;
		case IN_CURA:
			result=result+"Paziente morto" + pazienti.get(e.getDato()).getNome()+"\n";
			pazienti.get(e.getDato()).setStato(Paziente.StatoPaziente.NERO);
			this.pazientiMorti++;
			this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
			this.listaDottori.add(dottori.get(pazienti.get(e.getDato()).getCodiceDottore()));
			break;
		case NERO:
			this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
			break;
		case Red:
			result=result+"Paziente morto" + pazienti.get(e.getDato()).getNome()+"\n";
			pazienti.get(e.getDato()).setStato(Paziente.StatoPaziente.NERO);
			this.pazientiMorti++;
			this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
			break;
		case SALVO:
			this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
			break;
		case White:
			this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
			break;
		case Yellow:
			result=result+"Paziente morto" + pazienti.get(e.getDato()).getNome()+"\n";
			pazienti.get(e.getDato()).setStato(Paziente.StatoPaziente.NERO);
			this.pazientiMorti++;
			this.pazientiInAttesa.remove(pazienti.get(e.getDato()));
			break;
		default:
			break;
		
		
		}
	
		}
		while (cura(e.getTempo()))
			;
		}

	private boolean cura(long tempo) {
    if (pazientiInAttesa.isEmpty())
			return false;
    
    if (dottori.size() == 0)
    	return false;
    if (listaDottori.isEmpty())
    	return false;
    
    //c'è un paziente e un dottore
    Paziente p = pazientiInAttesa.remove();
    if (p.getStato() != Paziente.StatoPaziente.NERO || p.getStato() != Paziente.StatoPaziente.IN_CURA 
    		|| p.getStato() != Paziente.StatoPaziente.White||p.getStato() != Paziente.StatoPaziente.SALVO) {
    			
    	Dottore doc = listaDottori.remove();
    	
    	if(doc==null){System.out.println("passoooooo");return false;}
    	pazienti.get(p.getId()).setStato(Paziente.StatoPaziente.IN_CURA);
    	//aggiungiEvento(new Evento(adesso + 30, Evento.TipoEvento.PAZIENTE_GUARISCE, p.getId()));
    	//IL PAZIENTE GUARISCE DOPO MEZZORA
    	listaEventi.add(new Evento(tempo+1800*1000, Evento.TipoEvento.PAZIENTE_GUARISCE, p.getId()));
    	p.setCodiceDottore(doc.getIdDottore());
    	
    	return true;
    }
    
		return true;
	}
	

	
	
}
