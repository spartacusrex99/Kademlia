package org.minima.kademlia;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.List;

import kademlia.JKademliaNode;
import kademlia.dht.GetParameter;
import kademlia.dht.JKademliaStorageEntry;
import kademlia.dht.KadContent;
import kademlia.node.KademliaId;
import kademlia.node.Node;
import kademlia.simulations.DHTContentImpl;

public class StartKad {

	public static void main(String[] zArgs) {
		System.out.println("Kademilia Test Net");
	
	
		try {
			JKademliaNode kad1 = new JKademliaNode("OwnerName1", new KademliaId("ASF45678947584567463"), 12049);
		
			JKademliaNode kad2 = new JKademliaNode("OwnerName2", new KademliaId(), 12057);
			
			kad2.bootstrap(kad1.getNode());
		
			System.out.println("Network UP");
			
			DHTContentImpl c = new DHTContentImpl(kad2.getOwnerId(), "some data");
			kad2.put(c);
		
			KademliaId objkey = c.getKey();
			System.out.println("KAD2 KEY  : "+objkey);
			
			
//			GetParameter gp = new GetParameter(objkey, DHTContentImpl.TYPE);
//			JKademliaStorageEntry entry = kad1.get(gp);
//			String data = new String(entry.getContent());
//			System.out.println("entry : "+data);
			

			//Thread.sleep(1000);
			
//			System.out.println("kad1 : "+kad1);
//			System.out.println("kad2 : "+kad1);
			
			
			//now create a nide and connect
			JKademliaNode kad3 = new JKademliaNode("OwnerName3", new KademliaId(), 12063);
			
//			Node boots = new Node(new KademliaId("ASF45678947584567463"), InetAddress.getByName("127.0.0.1"), 12049);
			Node boots = new Node(new KademliaId(), InetAddress.getByName("127.0.0.1"), 12049);
			
			kad3.bootstrap(boots);
			
			GetParameter gp = new GetParameter(objkey, DHTContentImpl.TYPE);
			JKademliaStorageEntry entry = kad3.get(gp);
			String data = new String(entry.getContent());
			System.out.println("entry : "+data);
			
			
			kad1.shutdown(false);
			kad2.shutdown(false);
			kad3.shutdown(false);

			System.out.println("Shutdown complete");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Finished");
		
	}
	
}
