package it.polito.tdp.emergency.model;

public class Dottore implements Comparable<Dottore>{
	
	private int idDottore;
	private String nomeDottore;
	private long oraInizio=0;
	private boolean disponibile=false;
	
		public Dottore(String nomeDottore, long oraInizio) {
		super();
		this.idDottore = idDottore;
		this.nomeDottore = nomeDottore;
		this.oraInizio = oraInizio;
		this.disponibile=true;
	}
		
		
		

		public static int contaDottori=0;
		
		
	public static int getContaDottori() {
			return contaDottori;
		}
		public static void setContaDottori(int contaDottori) {
			Dottore.contaDottori = contaDottori;
		}
	public int getIdDottore() {
		return idDottore;
	}
	public void setIdDottore(int idDottore) {
		this.idDottore = idDottore;
	}
	public String getNomeDottore() {
		return nomeDottore;
	}
	public void setNomeDottore(String nomeDottore) {
		this.nomeDottore = nomeDottore;
	}
	public long getOraInizio() {
		return oraInizio;
	}
	public void setOraInizio(long oraInizio) {
		this.oraInizio = oraInizio;
	}
	public boolean isDisponibile() {
		return disponibile;
	}
	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idDottore;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dottore other = (Dottore) obj;
		if (idDottore != other.idDottore)
			return false;
		return true;
	}
	@Override
	public int compareTo(Dottore arg0) {
		return Long.compare(this.oraInizio,arg0.oraInizio);
		}
	@Override
	public String toString() {
		return "Dottore [idDottore=" + idDottore + ", nomeDottore=" + nomeDottore + ", oraInizio=" + oraInizio
				+ ", disponibile=" + disponibile + "]";
	}
	
	

}
