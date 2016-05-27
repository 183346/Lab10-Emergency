//////////////////////////////////////////////////////////////////-*-java-*-//
//             // Classroom code for "Tecniche di Programmazione"           //
//   #####     // (!) Giovanni Squillero <giovanni.squillero@polito.it>     //
//  ######     //                                                           //
//  ###   \    // Copying and distribution of this file, with or without    //
//   ##G  c\   // modification, are permitted in any medium without royalty //
//   #     _\  // provided this notice is preserved.                        //
//   |   _/    // This file is offered as-is, without any warranty.         //
//   |  _/     //                                                           //
//             // See: http://bit.ly/tecn-progr                             //
//////////////////////////////////////////////////////////////////////////////

package it.polito.tdp.emergency.simulation;

public class Paziente implements Comparable<Paziente> {
	public enum StatoPaziente {
		Red, Yellow, Green, White, IN_CURA, SALVO, NERO
	};

	private int id;
	private String nome;
	private StatoPaziente stato;
	private int codiceDottore;

	@Override
	public int hashCode() {
		return id;
	}
	

	public int getCodiceDottore() {
		return codiceDottore;
	}


	public void setCodiceDottore(int codiceDottore) {
		this.codiceDottore = codiceDottore;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paziente other = (Paziente) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public StatoPaziente getStato() {
		return stato;
	}

	public void setStato(StatoPaziente stato) {
		this.stato = stato;
	}

	public int getId() {
		return id;
	}

	

	@Override
	public String toString() {
		return "Paziente [id=" + id + ", nome=" + nome + ", stato=" + stato + ", codiceDottore=" + codiceDottore + "]";
	}


	public Paziente(int id, String nome, StatoPaziente stato) {
		super();
		this.id = id;
		this.nome=nome;
		this.stato = stato;
	}
	

	public Paziente() {
		super();
	}

	@Override
	public int compareTo(Paziente arg0) {
		return Integer.compare(this.getStato().ordinal(), arg0.getStato().ordinal());
	}

	public String getNome() {
		return nome;
	}

}
