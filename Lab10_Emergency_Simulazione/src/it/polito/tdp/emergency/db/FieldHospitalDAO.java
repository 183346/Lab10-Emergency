////////////////////////////////////////////////////////////////////////////////
//             //                                                             //
//   #####     // Field hospital simulator                                    //
//  ######     // (!) 2013 Giovanni Squillero <giovanni.squillero@polito.it>  //
//  ###   \    //                                                             //
//   ##G  c\   // Field Hospital DAO                                          //
//   #     _\  // Test with MariaDB 10 on win                                 //
//   |   _/    //                                                             //
//   |  _/     //                                                             //
//             // 03FYZ - Tecniche di programmazione 2012-13                  //
////////////////////////////////////////////////////////////////////////////////

package it.polito.tdp.emergency.db;

//import java.time.Instant;
import java.sql.*;
//import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import it.polito.tdp.emergency.simulation.Evento;
import it.polito.tdp.emergency.simulation.Evento.TipoEvento;
import it.polito.tdp.emergency.simulation.Paziente;
import it.polito.tdp.emergency.simulation.Paziente.StatoPaziente;
//import it.polito.tdp.emergency.simulation.*;


public class FieldHospitalDAO{



	StatoPaziente stato;
	TipoEvento tipoEvento=TipoEvento.PAZIENTE_ARRIVA;
	

	public Map<Integer, Paziente> creaTabellaPazienti() {
	
			final String sql = "SELECT distinct p.id, p.name, a.triage from patients p, arrivals a where p.id=a.patient ORDER BY p.name ASC" ;
			
			Map<Integer,Paziente> pazienti = new HashMap<Integer,Paziente>() ;
			
			Connection conn = DBConnect.getInstance().getConnection();
			try {
			
				PreparedStatement st = conn.prepareStatement(sql) ;
				ResultSet rs = st.executeQuery() ;
				int contatore=0;
				while( rs.next() ) {
					contatore++;
					stato= StatoPaziente.valueOf(rs.getString("a.triage"));
					Paziente paziente = new Paziente(
							rs.getInt("p.id"),
							rs.getString("p.name"),
							stato) ;
					pazienti.put(rs.getInt("p.id"),paziente) ;
				}
							
				st.close() ;
				conn.close();
			
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e) ;
			}
			
			return pazienti ;
		}


	public Queue<Evento> creaPrimaListaEventi() {
		final String sql = "SELECT * from arrivals " ;
		
		Queue<Evento> eventi = new PriorityQueue<Evento>() ;
		
		Connection conn = DBConnect.getInstance().getConnection();
		try {
		
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet rs = st.executeQuery() ;
	
			while( rs.next() ) {
				//Timestamp f = rs.getTimestamp("timestamp");
				//Instant i = f.toInstant();
				//long time =i.getEpochSecond();
				long time=(rs.getTimestamp("timestamp").getTime());
				Evento evento = new Evento(
						time,
						this.tipoEvento,
						rs.getInt("patient")) ;
				eventi.add(evento) ;
			}
						
			st.close() ;
			conn.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e) ;
		}
		
		
		return eventi ;
	}

	// TODO
}
